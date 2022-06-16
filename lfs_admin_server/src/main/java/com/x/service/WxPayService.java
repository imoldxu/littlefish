package com.x.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.apache.http.client.ClientProtocolException;

import com.x.data.pojo.wxpay.NoticeResource;
import com.x.data.pojo.wxpay.RefundTransaction;
import com.x.data.pojo.wxpay.WxTransaction;
import com.x.data.vo.WxPayCharge;

/**
 * 微信支付服务
 * @author 老徐
 *
 */
public interface WxPayService {
	
	/**
	   * 统一下单接口
	 * @param appid   小程序、APP、公众号的appid
	 * @param openid  与appid匹配的openid
	 * @param orderNo  订单号
	 * @param total  总金额，单位分
	 * @param description  订单描述信息
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public WxPayCharge createTransaction(String appid, String openid, String orderNo, Integer total, String description) throws URISyntaxException, IOException;	
	
	/**
	   * 通过交易id获取微信交易信息
	 * @param transactionId  返回的流水号
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public WxTransaction getTransactionByWxId(String transactionId) throws URISyntaxException, ClientProtocolException, IOException; 	

	/**
	   * 通过订单号获取支付信息
	 * @param orderNo
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public WxTransaction getTransactionByOrderno(String orderNo) throws URISyntaxException, ClientProtocolException, IOException ;	
	/**
	 * 关闭交易
	 * @param orderNo
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void closeTransaction(String orderNo) throws URISyntaxException, ClientProtocolException, IOException;
	
	/**
	 * 发起退货
	 * @param transaction_id  支付时流水号
	 * @param out_refund_no   退货订单号
	 * @param refund     退款金额  
	 * @param total      原订单总金额
	 * @param reason     退款原因
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public RefundTransaction refund(String transaction_id, String out_refund_no, int refund, int total, String reason) throws URISyntaxException, ClientProtocolException, IOException;	

	/**
	   * 查询退款交易
	 * @param out_refund_no
	 * @return
	 * @throws URISyntaxException
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public RefundTransaction queryRefund(String out_refund_no) throws URISyntaxException, ClientProtocolException, IOException; 	

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
	 * @param content
	 * @param WechatpaySignature
	 * @return
	 */
	public boolean verify(String content, String WechatpaySignature) ;
	
	/**
	 * 解密微信回调的交易
	 * @param noticeResource
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public WxTransaction decrypt(NoticeResource noticeResource) throws GeneralSecurityException, IOException;
}
