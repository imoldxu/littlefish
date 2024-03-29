package com.x.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.apache.http.Consts;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wechat.pay.contrib.apache.httpclient.util.RsaCryptoUtil;
import com.x.commons.util.WxUtil;
import com.x.constant.ErrorCode;
import com.x.data.pojo.wxpay.Amount;
import com.x.data.pojo.wxpay.NoticeResource;
import com.x.data.pojo.wxpay.Payer;
import com.x.data.pojo.wxpay.RefundAmount;
import com.x.data.pojo.wxpay.RefundReq;
import com.x.data.pojo.wxpay.RefundTransaction;
import com.x.data.pojo.wxpay.WxTransaction;
import com.x.data.pojo.wxpay.WxTransactionReq;
import com.x.data.vo.WxPayCharge;
import com.x.exception.HandleException;
import com.x.service.WxPayService;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WxPayServiceImpl implements WxPayService {

	@Value("${wx.pay.merchantId}")
	private String mchid;

	@Value("${wx.pay.certSerialNumber}")
	private String certSerialNumber;

	@Value("${wx.pay.merchantPrivateKeyFile}")
	private String privateKeyFile;

	@Value("${wx.pay.notifyUrl}")
	private String notify_url;

	@Value("${wx.pay.refundNotifyUrl}")
	private String refund_notify_url;

	@Value("${wx.pay.apiv3Key}")
	private String apiv3Key;

	CloseableHttpClient wxPayClient;
	AutoUpdateCertificatesVerifier verifier;
	PrivateKey merchantPrivateKey;

	@SneakyThrows
	@PostConstruct
	public void init() {
		ClassPathResource classPathResource = new ClassPathResource(privateKeyFile);
		// 临时屏蔽以下代码，微信支付还没申请前
		merchantPrivateKey = PemUtil.loadPrivateKey(classPathResource.getInputStream());
		// 不需要传入微信支付证书，将会自动更新
		verifier = new AutoUpdateCertificatesVerifier(
				new WechatPay2Credentials(mchid, new PrivateKeySigner(certSerialNumber, merchantPrivateKey)),
				apiv3Key.getBytes("utf-8"));

		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
				.withMerchant(mchid, certSerialNumber, merchantPrivateKey)
				.withValidator(new WechatPay2Validator(verifier));
		// .withWechatpay(wechatpayCertificates);
		// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient

		// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
		wxPayClient = builder.build();

		log.info("wxPayService init success");
	}

	@SneakyThrows
	@PreDestroy
	public void destroy() {
		wxPayClient.close();
	}

	/**
	 * 统一下单接口
	 * 
	 * @param appid       小程序、APP、公众号的appid
	 * @param openid      与appid匹配的openid
	 * @param orderNo     订单号
	 * @param total       总金额，单位分
	 * @param description 订单描述信息
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public WxPayCharge createTransaction(String appid, String openid, String orderNo, Integer total, String description)
			throws URISyntaxException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.mch.weixin.qq.com")
				.setPath("/v3/pay/transactions/jsapi").build();

		HttpPost httpMethod = new HttpPost(uri);

		WxTransactionReq req = new WxTransactionReq();
		req.setAppid(appid);
		req.setMchid(mchid);
		req.setDescription(description);
		req.setOut_trade_no(orderNo);
		// req.setTime_expire(time_expire);
		// req.setAttach(attach);
		req.setNotify_url(notify_url);
		// req.setGoods_tag(goods_tag);
		Amount amount = new Amount();
		amount.setTotal(total);
		req.setAmount(amount);
		Payer payer = new Payer();
		payer.setOpenid(openid);
		req.setPayer(payer);

		String postData = JSON.toJSONString(req);

		httpMethod.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));
		httpMethod.setHeader("accept", ContentType.APPLICATION_JSON.toString());
		log.info(postData.toString());
		CloseableHttpResponse response = wxPayClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				String prepay_id = jsonData.getString("prepay_id");

				return generatePayChage(appid, prepay_id);

			} else {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				log.error(jsonData.toJSONString());
				// TODO: 从错误信息中提取错误信息
				throw new HandleException(ErrorCode.WX_PAY_ERROR, "交易错误");
			}
		} finally {
			response.close();
		}
	}

	private WxPayCharge generatePayChage(String appid, String prepay_id) {
		WxPayCharge charge = new WxPayCharge();
		charge.setAppId(appid);
		String nonceStr = WxUtil.create_nonce_str();
		charge.setNonceStr(nonceStr);
		charge.setPrePayStr("prepay_id=" + prepay_id);
		charge.setSignType("RSA");
		String timeStamp = String.valueOf(new Date().getTime() / 1000);// 取到秒级，10位数字
		charge.setTimeStamp(timeStamp);

		String paySign = sign(appid, timeStamp, nonceStr, "prepay_id=" + prepay_id, merchantPrivateKey);

		charge.setPaySign(paySign);

		return charge;
	}

	@SneakyThrows
	private String sign(String appid, String timeStamp, String nonceStr, String body, PrivateKey privateKey) {
		String signatureStr = Stream.of(appid, timeStamp, nonceStr, body).collect(Collectors.joining("\n", "", "\n"));
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initSign(privateKey);
		sign.update(signatureStr.getBytes("utf-8"));
		return Base64Utils.encodeToString(sign.sign());
	}

	/**
	 * 通过交易id获取微信交易信息
	 * 
	 * @param transactionId 返回的流水号
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public WxTransaction getTransactionByWxId(String transactionId)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.mch.weixin.qq.com")
				.setPath("/v3/pay/transactions/id/" + transactionId).setParameter("mchid", mchid).build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = wxPayClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				WxTransaction trans = JSON.parseObject(data, WxTransaction.class);
				return trans;
			} else {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				log.error(jsonData.toJSONString());
				// TODO: 从错误信息中提取错误信息
				throw new HandleException(ErrorCode.WX_PAY_ERROR, "交易错误");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 通过订单号获取支付信息
	 * 
	 * @param orderNo
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public WxTransaction getTransactionByOrderno(String orderNo)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.mch.weixin.qq.com")
				.setPath("/v3/pay/transactions/out-trade-no/" + orderNo).setParameter("mchid", mchid).build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = wxPayClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				WxTransaction trans = JSON.parseObject(data, WxTransaction.class);
				return trans;
			} else {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				log.error(jsonData.toJSONString());
				// TODO: 从错误信息中提取错误信息
				throw new HandleException(ErrorCode.WX_PAY_ERROR, "交易错误");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 关闭交易
	 * 
	 * @param orderNo
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void closeTransaction(String orderNo) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.mch.weixin.qq.com")
				.setPath("/v3/pay/transactions/out-trade-no/" + orderNo + "/close").build();

		HttpPost httpMethod = new HttpPost(uri);

		Map<String, String> req = new HashMap<String, String>();
		req.put("mchid", mchid);
		String postData = JSON.toJSONString(req);

		httpMethod.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

		log.info(postData.toString());
		CloseableHttpResponse response = wxPayClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				return;
			} else {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				log.error(jsonData.toJSONString());
				// TODO: 从错误信息中提取错误信息
				throw new HandleException(ErrorCode.WX_PAY_ERROR, "交易错误");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 发起退货
	 * 
	 * @param transaction_id 支付时流水号
	 * @param out_refund_no  退货订单号
	 * @param refund         退款金额
	 * @param total          原订单总金额
	 * @param reason         退款原因
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public RefundTransaction refund(String transaction_id, String out_refund_no, int refund, int total, String reason)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.mch.weixin.qq.com")
				.setPath("/v3/refund/domestic/refunds").build();

		HttpPost httpMethod = new HttpPost(uri);

		RefundReq req = new RefundReq();
		req.setTransaction_id(transaction_id);
		// req.setOut_trade_no(out_trade_no); 和第三方流水号二选一
		req.setOut_refund_no(out_refund_no);
		RefundAmount amount = new RefundAmount();
		amount.setRefund(refund);
		amount.setTotal(total);
		req.setAmount(amount);
		req.setReason(reason);
		req.setNotify_url(refund_notify_url);
		String postData = JSON.toJSONString(req);

		httpMethod.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

		log.info(postData.toString());
		CloseableHttpResponse response = wxPayClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {
				String data = EntityUtils.toString(response.getEntity());
				RefundTransaction trans = JSON.parseObject(data, RefundTransaction.class);
				return trans;
			} else {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				log.error(jsonData.toJSONString());
				// TODO: 从错误信息中提取错误信息
				throw new HandleException(ErrorCode.WX_PAY_ERROR, "交易错误");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 查询退款交易
	 * 
	 * @param out_refund_no
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public RefundTransaction queryRefund(String out_refund_no)
			throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.mch.weixin.qq.com")
				.setPath("/v3/refund/domestic/refunds/" + out_refund_no).build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = wxPayClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				RefundTransaction trans = JSON.parseObject(data, RefundTransaction.class);
				return trans;
			} else {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				log.error(jsonData.toJSONString());
				// TODO: 从错误信息中提取错误信息
				throw new HandleException(ErrorCode.WX_PAY_ERROR, "交易错误");
			}
		} finally {
			response.close();
		}
	}

//    /**
//     * 获取平台证书
//     * @return
//     * @throws ParseException
//     * @throws IllegalAccessException
//     * @throws NoSuchAlgorithmException
//     * @throws IOException
//     * @throws InstantiationException
//     * @throws SignatureException
//     * @throws InvalidKeyException
//     * @throws CertificateException
//     */
//    public Map<String, X509Certificate> refreshCertificate() throws ParseException, CertificateException, RequestWechatException {
//        //获取平台证书json
//    	URI uri = new URIBuilder().setScheme("https").setHost("api.mch.weixin.qq.com")
//				.setPath("/v3/certificates")
//				.build();
//
//		HttpGet request = new HttpGet(uri);
//    	
//		CloseableHttpResponse response = wxPayClient.execute(request);
//		try {
//			int status = response.getStatusLine().getStatusCode();
//			if (status == HttpStatus.SC_OK) {
//
//				String data = EntityUtils.toString(response.getEntity());
//				RefundTransaction trans = JSON.parseObject(data, RefundTransaction.class);
//				return trans;
//			} else {
//				String data = EntityUtils.toString(response.getEntity());
//				JSONObject jsonData = JSON.parseObject(data);
//				log.error(jsonData.toJSONString());
//				//TODO: 从错误信息中提取错误信息
//				throw new HandleException(ErrorCode.WX_PAY_ERROR, "交易错误");
//			}
//		} finally {
//			response.close();
//		}
//        
//        
//    }

	/**
	 * 验证微信回调的签名
	 * 
	 * @param content
	 * @param WechatpaySignature
	 * @return
	 */
	@SneakyThrows
	public boolean verify(String content, String WechatpaySignature) {
		X509Certificate wechatpayCertificate = verifier.getValidCertificate();
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initVerify(wechatpayCertificate);
		signature.update(content.getBytes("UTF-8"));
		return signature.verify(Base64.getDecoder().decode(WechatpaySignature));
	}

	/**
	 * 解密微信回调的交易
	 * 
	 * @param noticeResource
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public WxTransaction decrypt(NoticeResource noticeResource) throws GeneralSecurityException, IOException {
		AesUtil aesUtil = new AesUtil(Base64.getDecoder().decode(apiv3Key));
		String transStr = aesUtil.decryptToString(Base64.getDecoder().decode(noticeResource.getAssociated_data()),
				Base64.getDecoder().decode(noticeResource.getNonce()), noticeResource.getCiphertext());
		WxTransaction transaction = JSON.parseObject(transStr, WxTransaction.class);
		return transaction;
	}
}
