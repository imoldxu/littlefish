package com.x.tools.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.x.lfs.context.ErrorCode;
import com.x.lfs.context.HandleException;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Base64;
import java.util.Formatter;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class WxMiniProgramUtil {

	private static Logger log = LoggerFactory.getLogger(WxMiniProgramUtil.class);

	/**
	 * 发送消息相关
	 */
	private static final String TOKEN = "zkhz";
	private static final String encodingAesKey = "WADge6EHVX3zxQ3OculChEommAdP5aV3ajk3Hww3RMm";

	public static final String grant_type = "client_credential";

	public static final String appid = "wx9a452688cd3858eb";
	public static final String secret = "d0e80bf423848e0ebb647998e2228d63";

	private static CloseableHttpClient httpClient = httpClient();

	public static JSONObject jsCode2Session(String wxCode) throws IOException, URISyntaxException {

		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/sns/jscode2session")
				.setParameter("grant_type", "authorization_code").setParameter("appid", appid)
				.setParameter("secret", secret).setParameter("js_code", wxCode).build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if (isError(jsonData)) {
					throw new HandleException(ErrorCode.WX_ERROR, jsonData.getString("errmsg"));
				} else {
					return jsonData;
				}
			} else {
				throw new HandleException(ErrorCode.WX_NET_ERROR, "微信响应状态失败:" + status);
			}
		} finally {
			response.close();
		}
	}

	private static boolean isError(JSONObject data) {
		Integer errorcode = data.getInteger("errcode");
		if (errorcode != null && 0 != errorcode) {
			return true;
		}
		return false;
	}

	public static String convertAvatar(String headerImgURL) {
		if (null != headerImgURL && !headerImgURL.isEmpty()) {
			// 去掉微信图片为0的标号
			headerImgURL = headerImgURL.substring(0, headerImgURL.length() - 3);
			// 设置微信图片为64的标号
			headerImgURL = headerImgURL + "64";
		}
		return headerImgURL;
	}

	public static String converWxNick(String wxnick) {
		String regEx = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(wxnick);
		return m.replaceAll("").trim();
	}

	public static String getToken() throws URISyntaxException, IOException {

		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/token")
				.setParameter("grant_type", grant_type).setParameter("appid", appid).setParameter("secret", secret)
				.build();

		HttpGet httpMethod = new HttpGet(uri);

		CloseableHttpResponse response = httpClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				return jsonData.getString("access_token");
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}

	/**
	 * 发送模板消息，接收微信的url验证
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 * @throws AesException
	 */
	public static String checkSignature(String signature, String timestamp, String nonce, String echostr)
			throws AesException {

		WXBizMsgCrypt pc = new WXBizMsgCrypt(TOKEN, encodingAesKey, appid);
		String decodeEcho = pc.verifyUrl(signature, timestamp, nonce, "");

		return echostr;
	}

	/**
	 * 发送模板消息
	 * 
	 * @param openid
	 * @param access_token
	 * @param userNick
	 * @param orderid
	 * @param giftName
	 * @param money
	 * @param time
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static boolean pushTemplateMsg(String openid, String access_token, String template_id, String page,
			String form_id, JSONObject msg) throws IOException, URISyntaxException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com")
				.setPath("/cgi-bin/message/wxopen/template/send").setParameter("access_token", access_token).build();

		HttpPost httpMethod = new HttpPost(uri);

		HashMap<String, Object> map = new HashMap<>();

		map.put("touser", openid);
		map.put("template_id", template_id);
		if (page != null && !page.isEmpty()) {
			map.put("page", page);
		}
		map.put("form_id", form_id);
		log.info(msg.toJSONString());
		map.put("data", msg);

		String postData = JSON.toJSONString(map);

		httpMethod.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

		log.info(postData.toString());
		CloseableHttpResponse response = httpClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if (isError(jsonData)) {
					// throw new HandleException(ErrorCode.WX_ERROR, jsonData.getString("errmsg"));
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} finally {
			response.close();
		}

	}

	public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
		// 被加密的数据
		byte[] dataByte = Base64.getDecoder().decode(encryptedData.getBytes());
		// 加密秘钥
		byte[] keyByte = Base64.getDecoder().decode(sessionKey.getBytes());
		// 偏移量
		byte[] ivByte = Base64.getDecoder().decode(iv.getBytes());
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return JSON.parseObject(result);
			}
		} catch (Exception e) {
			throw new HandleException(ErrorCode.WX_CRYPTO_ERROR, "微信解密异常");
		}
		return null;
	}

	private static CloseableHttpClient httpClient() {
		try {
			
			// 解决keepAlive设置的问题
			ConnectionKeepAliveStrategy keepAliveStrategy = new DefaultConnectionKeepAliveStrategy() {

				@Override
				public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
					long keepAlive = super.getKeepAliveDuration(response, context);
					if (keepAlive == -1) {
						keepAlive = 3600 * 1000;
					}
					return keepAlive;
				}
			};

			// 解决https的问题，信任所有的证书
			SSLContextBuilder sslbuilder = new SSLContextBuilder();
			sslbuilder.loadTrustMaterial(null, new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
					return true;
				}
			});
			SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(sslbuilder.build(),
					new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2" }, null, NoopHostnameVerifier.INSTANCE);
			
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(
					RegistryBuilder.<ConnectionSocketFactory>create()
	                .register("http", PlainConnectionSocketFactory.getSocketFactory())
	                .register("https", sslFactory)
	                .build());
	        cm.setMaxTotal(128);
	        
			CloseableHttpClient client = HttpClients.custom()
					.setConnectionManager(cm)
					.setSSLSocketFactory(sslFactory).setKeepAliveStrategy(keepAliveStrategy).build();
			return client;
		} catch (Exception e) {
			return null;
		}
	}
}
