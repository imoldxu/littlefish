package com.x.service.impl;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x.constant.ErrorCode;
import com.x.data.vo.UserVo;
import com.x.data.vo.WxPayCharge;
import com.x.exception.HandleException;
import com.x.service.WxMiniProgramService;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Arrays;
import java.util.Base64;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class WxMiniProgramServiceImpl implements WxMiniProgramService {

	@Autowired
	@Qualifier("wxClient")
	private CloseableHttpClient httpClient;

	@Value("${wx.mini.appid}")
	private String appid = "wx9a452688cd3858eb";
	
	@Value("${wx.mini.secret}")
	private String secret = "b09661d0f88271431db87950116c7c09";
	
	@Autowired
	private WxPayServiceImpl wxPayService;

	public JSONObject jsCode2Session(String wxCode) throws IOException, URISyntaxException {

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

	private boolean isError(JSONObject data) {
		Integer errorcode = data.getInteger("errcode");
		if (errorcode != null && 0 != errorcode) {
			return true;
		}
		return false;
	}

	public String getAccessToken() throws URISyntaxException, IOException {

		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/token")
				.setParameter("grant_type", "client_credential").setParameter("appid", appid)
				.setParameter("secret", secret).build();

		HttpGet httpMethod = new HttpGet(uri);

		CloseableHttpResponse response = httpClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if (isError(jsonData)) {
					throw new IOException("微信响应状态错误:" + jsonData.getString("errmsg"));
				} else {
					return jsonData.getString("access_token");
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}

//	/**
//	 * 发送模板消息，接收微信的url验证
//	 * 
//	 * @param signature
//	 * @param timestamp
//	 * @param nonce
//	 * @param echostr
//	 * @return
//	 * @throws AesException
//	 */
//	public static String checkSignature(String signature, String timestamp, String nonce, String echostr)
//			throws AesException {
//
//		WXBizMsgCrypt pc = new WXBizMsgCrypt(TOKEN, encodingAesKey, appid);
//		String decodeEcho = pc.verifyUrl(signature, timestamp, nonce, "");
//		
//		log.info("checkSignature: %s", decodeEcho);
//		
//		return echostr;
//	}

	/**
	 * 发送模板消息
	 * 
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public boolean pushTemplateMsg(String openid, String access_token, String template_id, String page, String form_id,
			JSONObject msg) throws IOException, URISyntaxException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com")
				.setPath("/cgi-bin/message/wxopen/template/uniform_send").setParameter("access_token", access_token)
				.build();

		HttpPost httpMethod = new HttpPost(uri);

		HashMap<String, Object> map = new HashMap<>();

		map.put("access_token", access_token);
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

	public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
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
			e.printStackTrace();
			throw new HandleException(ErrorCode.WX_CRYPTO_ERROR, "微信解密异常");
		}
		return null;
	}

	public WxPayCharge getPayCharge(UserVo user, String orderNo, Integer total, String description)
			throws URISyntaxException, IOException {
		return wxPayService.createTransaction(appid, user.getWxMiniOpenId(), orderNo, total, description);
	}
}
