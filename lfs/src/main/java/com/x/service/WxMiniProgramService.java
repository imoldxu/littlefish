package com.x.service;

import com.alibaba.fastjson.JSONObject;
import com.x.data.vo.UserVo;
import com.x.data.vo.WxPayCharge;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 微信小程序
 * @author 老徐
 *
 */
public interface WxMiniProgramService {

	public JSONObject jsCode2Session(String wxCode) throws IOException, URISyntaxException;
	
	public String getAccessToken() throws URISyntaxException, IOException;

	/**
	 * 发送模板消息
	 * 
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public boolean pushTemplateMsg(String openid, String access_token, String template_id, String page,
			String form_id, JSONObject msg) throws IOException, URISyntaxException;

	/**
	 * 获取用户信息
	 * @param encryptedData
	 * @param sessionKey
	 * @param iv
	 * @return
	 */
	public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv);

	/**
	 * 获取支付凭证
	 * @param user
	 * @param orderNo
	 * @param total
	 * @param description
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public WxPayCharge getPayCharge(UserVo user, String orderNo, Integer total, String description) throws URISyntaxException, IOException;
}
