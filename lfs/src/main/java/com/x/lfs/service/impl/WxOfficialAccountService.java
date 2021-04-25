package com.x.lfs.service.impl;

import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Formatter;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class WxOfficialAccountService {

	@Value("${wx.officalAccount.appid}")
	public String appid;  //乐游
	
	@Value("${wx.officalAccount.secret}")
	public String secret;
	
	@Autowired
	@Qualifier("wxClient")
	private CloseableHttpClient httpClient;
	
	public Map<String, String> sign(String url) throws URISyntaxException, IOException {
		String token = getToken();
		String ticket = getTicket(token);
		return sign(ticket, url);
	}

	private String getToken() throws URISyntaxException, IOException {
		
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/token")
				.setParameter("grant_type", "client_credential").setParameter("appid", appid).setParameter("secret", secret)
				.build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpMethod);
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if(isError(jsonData)) {
					throw new IOException("微信响应状态错误:"+jsonData.getString("errmsg")); 
				}else {
					return jsonData.getString("access_token");
				}
			} else {
				throw new IOException("微信响应状态错误");
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
	
	public String getTicket(String token) throws URISyntaxException, ClientProtocolException, IOException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/ticket/getticket")
				.setParameter("access_token", token).setParameter("type", "jsapi")
				.build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpMethod);
		
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if(isError(jsonData)) {
					throw new IOException("微信响应状态错误:"+jsonData.getString("errmsg")); 
				}else {
					return jsonData.getString("ticket");
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}

	public Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp + "&url=" + url;
		//System.out.println(string1);

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	public JSONObject getOauthInfo(String wxCode) throws IOException, URISyntaxException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/sns/oauth2/access_token")
				.setParameter("appid", appid).setParameter("secret", secret).setParameter("code", wxCode)
				.setParameter("grant_type", "authorization_code").build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpMethod);
		
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if(isError(jsonData)) {
					throw new IOException("微信响应状态错误:"+jsonData.getString("errmsg")); 
				}else {
					return jsonData;
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}

//	public JsonNode getOauthInfobyAPP(String wxCode) throws IOException {
//		JsonNode ret = null;
//		try {
//			h.open("https://api.weixin.qq.com/sns/oauth2/access_token", "get");
//			h.addParameter("appid", app_appid);
//			h.addParameter("secret", app_secret);
//			h.addParameter("code", wxCode);
//			h.addParameter("grant_type", "authorization_code");
//
//			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
//			int status = h.send();
//			if (200 == status) {
//				String response = h.getResponseBodyAsString("utf-8");
//				ret = JSONUtils.getJsonObject(response);
//				JsonNode errorcode = ret.get("errcode");
//				if (null != errorcode) {
//					System.out.println("weChat errorCode is:"+errorcode);
//					String errMsg = ret.get("errmsg").asText();
//					throw new IOException("微信授权请求错误:" + errMsg);
//				} else {
//					return ret;
//				}
//			} else {
//				throw new IOException("微信服务器请求失败");
//			}
//		} finally {
//			h.close();
//		}
//	}
	
	public JSONObject getUserInfo(JSONObject wxOauthInfo) throws IOException, URISyntaxException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/sns/userinfo")
				.setParameter("access_token", wxOauthInfo.getString("access_token")).setParameter("openid", wxOauthInfo.getString("openid"))
				.setParameter("lang", "zh_CN").build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpMethod);
		
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if(isError(jsonData)) {
					throw new IOException("微信响应状态错误:"+jsonData.getString("errmsg")); 
				}else {
					return jsonData;
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}
	
	//使用关注了公众号的用户来获取用户信息
	public JSONObject getUserInfo2(String accessToken, JSONObject wxOauthInfo) throws IOException, URISyntaxException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/user/info")
				.setParameter("access_token", accessToken).setParameter("openid", wxOauthInfo.getString("openid"))
				.setParameter("lang", "zh_CN").build();

		HttpGet httpMethod = new HttpGet(uri);
		CloseableHttpResponse response = httpClient.execute(httpMethod);
		
		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if(isError(jsonData)) {
					throw new IOException("微信响应状态错误:"+jsonData.getString("errmsg")); 
				}else {
					return jsonData;
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}
	
	/**
	 * 发送模板消息
	 * @param openid
	 * @param access_token
	 * @param userNick
	 * @param orderid
	 * @param giftName
	 * @param money
	 * @param time
	 * @return
	 * @throws IOException
	 */
//	public boolean pushTemplateMsg(String openid, String access_token, String userNick, String orderid,String giftName, String money, String time ) throws IOException{
//		JsonNode ret = null;
//		try {       https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
//			h.open("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token, "post");
//			
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			
//			String msg = "{\"first\": {\"value\":\"携玩会员:"+ userNick +"完成了兑换\",\"color\":\"#0000FF\"},"
//					+ "\"keyword1\":{\"value\":\""+orderid+"\",\"color\":\"#0000FF\"},"
//					+ "\"keyword2\":{\"value\":\""+giftName+"\",\"color\":\"#0000FF\"},"
//					+ "\"keyword3\":{\"value\":\""+money+"\",\"color\":\"#0000FF\"},"
//					+ "\"keyword4\":{\"value\":\""+time+"\",\"color\":\"#0000FF\"},"
//					+"\"remark\":{\"value\":\"请提供礼品\",\"color\":\"#0000FF\"}}";
//			
//			JsonNode msgObj = JSONUtils.getJsonObject(msg);
//			
//			//h.addParameter("touser", openid);
//			//h.addParameter("template_id", "-QqtuXZZlcYVTQm3_sRSb_tgGymougL7onz1EbjB0rY");
//			//h.addParameter("url", "http://weixin.qq.com/download");
//			//h.addParameter("data", xxx);
//			map.put("touser", openid);
//			map.put("template_id", "-QqtuXZZlcYVTQm3_sRSb_tgGymougL7onz1EbjB0rY");
//			map.put("url", "http://weixin.qq.com/download");
//			map.put("data", msgObj);
//			
//			JsonNode postData = JSONUtils.getJsonObject(map);
//			
//			h.setRequestHeader("Cookie", "Language=zh_CN;UserAgent=PC");
//			
//			int status = h.postJson(postData.toString());
//			if (200 == status) {
//				String response = h.getResponseBodyAsString("utf-8");
//				ret = JSONUtils.getJsonObject(response);
//				JsonNode errorcode = ret.get("errcode");
//				if (errorcode != null && 0 != errorcode.asInt()) {
//					String errmsg = ret.get("errmsg").asText();
//					System.out.println("微信推送模板消息失败:" + errmsg);
//					return false;
//				} else {
//					return true;
//				}
//			} else {
//				throw new IOException("微信服务器请求失败");
//			}
//		} finally {
//			h.close();
//		}
//	}

}
