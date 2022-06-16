package com.x.service;

import java.io.IOException;

import com.alibaba.fastjson.JSONObject;

import com.aliyuncs.auth.sts.AssumeRoleResponse.Credentials;

public interface AliYunOssService {

	/**
	 * oss签名URI资源
	 * @param objectName
	 * @return
	 */
	public String signAccessObject(String objectName);
	
	/**
	 *   获取后台签名，前端PostObject直传
	 * @return
	 */
	public JSONObject sign();
	
	/**
	   * 处理客户端上传后的回调
	 * @param ossCallbackBody
	 * @param autorizationInput
	 * @param pubKeyInput
	 * @param queryString
	 * @param uri
	 * @return
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public boolean VerifyOSSCallbackRequest(String ossCallbackBody, String autorizationInput, String pubKeyInput, String queryString, String uri) throws NumberFormatException, IOException;
	
	/**
	  * 获取STS临时凭证
	 * @return
	 */
	public Credentials getAcs();
	
}
