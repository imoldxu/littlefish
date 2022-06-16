package com.x.service.impl;

import java.util.UUID;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.x.data.pojo.wxoa.Menu;
import com.x.data.vo.UserVo;
import com.x.data.vo.WxConfig;
import com.x.data.vo.WxPayCharge;
import com.x.service.WxOfficialAccountService;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class WxOfficialAccountServiceImpl implements WxOfficialAccountService {

	@Value("${wx.officalAccount.appid}")
	public String appid; // 乐游

	@Value("${wx.officalAccount.secret}")
	public String secret;

	@Autowired
	@Qualifier("wxClient")
	private CloseableHttpClient httpClient;

	@Autowired
	private WxPayServiceImpl wxPayService;

	/**
	 * 签名，生成页面wx.config的配置
	 * 
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public WxConfig sign(String url) throws URISyntaxException, IOException {
		String ticket = getTicket();
		return sign(ticket, url);
	}

	@Cacheable(cacheNames = "wechat_oa_access_token", unless = "#result == null")
	public String getTokenByCache() throws URISyntaxException, IOException {
		return getToken();
	}

	public String getToken() throws URISyntaxException, IOException {

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

	private boolean isError(JSONObject data) {
		Integer errorcode = data.getInteger("errcode");
		if (errorcode != null && 0 != errorcode) {
			return true;
		}
		return false;
	}

	public String getTicket() throws URISyntaxException, ClientProtocolException, IOException {
		String token = getTokenByCache();

		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/ticket/getticket")
				.setParameter("access_token", token).setParameter("type", "jsapi").build();

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
					return jsonData.getString("ticket");
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}

	public WxConfig sign(String jsapi_ticket, String url) {
		WxConfig config = new WxConfig();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String signature = "";
		// 注意这里参数名必须全部小写，且必须有序
		String signedStr = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str + "&timestamp=" + timestamp
				+ "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(signedStr.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		config.setAppId(appid);
		config.setUrl(url);
		config.setJsapi_ticket(jsapi_ticket);
		config.setNonceStr(nonce_str);
		config.setTimestamp(timestamp);
		config.setSignature(signature);
		return config;
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

	/**
	 * 获取授权信息
	 * 
	 * @param wxCode
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public JSONObject code2AccessToken(String wxCode) throws IOException, URISyntaxException {
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
				if (isError(jsonData)) {
					throw new IOException("微信响应状态错误:" + jsonData.getString("errmsg"));
				} else {
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
	 * 获取SNS用户信息
	 * 
	 * @param wxOauthInfo
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public JSONObject getSNSUserInfo(JSONObject wxOauthInfo) throws IOException, URISyntaxException {
		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/sns/userinfo")
				.setParameter("access_token", wxOauthInfo.getString("access_token"))
				.setParameter("openid", wxOauthInfo.getString("openid")).setParameter("lang", "zh_CN").build();

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
					return jsonData;
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}

	// 使用关注了公众号的用户来获取用户信息 获取用户基本信息（包括UnionID机制）
	public JSONObject getUserInfo(String openid) throws IOException, URISyntaxException {
		String accessToken = getTokenByCache();

		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/user/info")
				.setParameter("access_token", accessToken).setParameter("openid", openid).setParameter("lang", "zh_CN")
				.build();

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
					return jsonData;
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}
	}

	public WxPayCharge getPayCharge(UserVo user, String orderNo, Integer total, String description)
			throws URISyntaxException, IOException {
		return wxPayService.createTransaction(appid, user.getWxOfficialAccountOpenId(), orderNo, total, description);
	}

	/**
	 * menu配置
	 * 
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws URISyntaxException
	 */
	public void createMenu() throws ClientProtocolException, IOException, URISyntaxException {
		String accessToken = getTokenByCache();

		URI uri = new URIBuilder().setScheme("https").setHost("api.weixin.qq.com").setPath("/cgi-bin/menu/create")
				.setParameter("access_token", accessToken).build();

		HttpPost httpMethod = new HttpPost(uri);

		Menu menu = new Menu();
		List<Menu.MenuItem> button = new ArrayList<Menu.MenuItem>();

		Menu.MenuItem item1 = new Menu.MenuItem();
		item1.setName("爱心义卖");
		List<Menu.MenuItem> item1subButton = new ArrayList<Menu.MenuItem>();
		item1subButton.add(buildViewMenuItem("配捐活动", "https://www.x.com/active_sell"));
		item1subButton.add(buildViewMenuItem("许仙饮", "https://www.x.com/goods?categroy=2"));
		item1subButton.add(buildViewMenuItem("健康商城", "https://www.x.com/goods/1"));
		item1.setSub_button(item1subButton);
		button.add(item1);

		Menu.MenuItem item2 = new Menu.MenuItem();
		item2.setName("公益在线");
		List<Menu.MenuItem> item2subButton = new ArrayList<Menu.MenuItem>();
		item2subButton.add(buildViewMenuItem("专项基金", "https://www.x.com/fund"));
		item2subButton.add(buildViewMenuItem("公益活动", "https://www.x.com/article?type=2"));
		item2subButton.add(buildViewMenuItem("健康讲堂", "https://www.x.com/article?type=1"));
		item2subButton.add(buildViewMenuItem("需要你", "https://www.x.com/article?type=3"));
		item2.setSub_button(item2subButton);
		button.add(item2);

		Menu.MenuItem item3 = buildViewMenuItem("爱心人士", "https://www.x.com/mine");
		button.add(item3);

		menu.setButton(button);

		String postData = JSON.toJSONString(menu);

		httpMethod.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

		CloseableHttpResponse response = httpClient.execute(httpMethod);

		try {
			int status = response.getStatusLine().getStatusCode();
			if (status == HttpStatus.SC_OK) {

				String data = EntityUtils.toString(response.getEntity());
				JSONObject jsonData = JSON.parseObject(data);
				if (isError(jsonData)) {
					throw new IOException("微信响应状态错误:" + jsonData.getString("errmsg"));
				} else {
					return;
				}
			} else {
				throw new IOException("微信响应状态错误");
			}
		} finally {
			response.close();
		}

	}

	private Menu.MenuItem buildViewMenuItem(String name, String url) {
		Menu.MenuItem item = new Menu.MenuItem();
		item.setType("view");
		item.setName(name);
		item.setUrl(url);
		return item;
	}

	private Menu.MenuItem buildMiniProgramMenuItem(String name, String url, String appid, String pagepath) {
		Menu.MenuItem item = new Menu.MenuItem();
		item.setType("miniprogram");
		item.setName(name);
		item.setUrl(url);
		item.setAppid(appid);
		item.setPagepath(pagepath);
		return item;
	}

	private Menu.MenuItem buildClickMenuItem(String name, String key) {
		Menu.MenuItem item = new Menu.MenuItem();
		item.setType("click");
		item.setName(name);
		item.setKey(key);
		return item;
	}

}
