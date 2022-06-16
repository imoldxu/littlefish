package com.x.service;

import org.apache.http.client.ClientProtocolException;

import com.alibaba.fastjson.JSONObject;
import com.x.data.vo.UserVo;
import com.x.data.vo.WxConfig;
import com.x.data.vo.WxPayCharge;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 微信公众号服务
 * @author 老徐
 *
 */
public interface WxOfficialAccountService {
	
	/**
	 * 签名，生成页面wx.config的配置
	 * @param url
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public WxConfig sign(String url) throws URISyntaxException, IOException;

	/**
	 * 从缓存中获取token
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public String getTokenByCache() throws URISyntaxException, IOException;
	
	public String getToken() throws URISyntaxException, IOException;
	
	public String getTicket() throws URISyntaxException, ClientProtocolException, IOException;
	
	public WxConfig sign(String jsapi_ticket, String url);
	
	/**
	 * 获取授权信息
	 * @param wxCode
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public JSONObject code2AccessToken(String wxCode) throws IOException, URISyntaxException; 
	
	/**
	 * 获取SNS用户信息
	 * @param wxOauthInfo
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public JSONObject getSNSUserInfo(JSONObject wxOauthInfo) throws IOException, URISyntaxException; 	
	//使用关注了公众号的用户来获取用户信息
	public JSONObject getUserInfo(String openid) throws IOException, URISyntaxException;
	
	public WxPayCharge getPayCharge(UserVo user, String orderNo, Integer total, String description) throws URISyntaxException, IOException;
	
}
