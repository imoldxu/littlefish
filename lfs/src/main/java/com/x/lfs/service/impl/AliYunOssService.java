package com.x.lfs.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.STSAssumeRoleSessionCredentialsProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.auth.sts.AssumeRoleResponse.Credentials;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.x.lfs.constant.ErrorCode;
import com.x.lfs.exception.HandleException;

import lombok.SneakyThrows;

@Service
public class AliYunOssService {

	@Value("${aliyun.oss.accessKeyId}")
	private String accessKeyId;
	
	@Value("${aliyun.oss.accessKeySecret}")
	private String accessKeySecret;
	
	@Value("${aliyun.oss.roleArn}")
	private String roleArn;
	
	@Value("${aliyun.oss.region}")
	private String region;
	
	@Value("${aliyun.oss.endpoint}")
	private String endpoint;
	
	@Value("${aliyun.oss.callbackUrl}")
	private String callbackUrl;
	
	private OSS ossClient;
	
	@PostConstruct
	public void init() throws ClientException {
		// 授权STSAssumeRole访问的Region。以杭州为例，其它Region请按实际情况填写。
		String region = "cn-hangzhou";
		// 填写RAM用户的访问密钥（AccessKeyId和AccessKeySecret）。
		//String accessKeyId = "<yourAccessKeyId>";
		//String accessKeySecret = "<yourAccessKeySecret>";
		// 角色的ARN，即需要扮演的角色ID。
		//String roleArn = "<yourRoleArn>";   
		// 创建STSAssumeRoleSessionCredentialsProvider实例。
		STSAssumeRoleSessionCredentialsProvider provider = CredentialsProviderFactory.newSTSAssumeRoleSessionCredentialsProvider(
		region, accessKeyId, accessKeySecret, roleArn);

		// 通过STSAssumeRole访问OSS的Endpoint。以杭州为例，其它endpoint请按实际情况填写。
		//String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";  
		// 创建ClientConfiguration实例。
		ClientBuilderConfiguration conf = new ClientBuilderConfiguration();

		// 创建OSSClient实例。
		ossClient = new OSSClientBuilder().build(endpoint, provider, conf);
	}
	
	@PreDestroy
	public void destroy() {
		ossClient.shutdown();
	}
	
	/**
	 * oss签名URI资源
	 * @param objectName
	 * @return
	 */
	public String signAccessObject(String objectName) {
		String bucket = "lfs-image";
		// 设置URL过期时间为1小时。
		Date expiration = new Date(new Date().getTime() + 3600 * 1000);
		// 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
		URL url = ossClient.generatePresignedUrl(bucket, objectName, expiration);
		return url.toString();
	}
	
	/**
	 *   获取后台签名，前端PostObject直传
	 * @return
	 */
	@SneakyThrows
	public JSONObject sign() {
		String bucket = "lfs-image";
		String host = "https://" + bucket + "." + endpoint; 
		String dir = "/"; 
		
		long expireTime = 30;
		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);
		PolicyConditions policyConds = new PolicyConditions();
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
		
		String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes("utf-8");
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = ossClient.calculatePostSignature(postPolicy);

		Map<String, String> respMap = new LinkedHashMap<String, String>();
		respMap.put("accessId", accessKeyId);
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", dir);
		respMap.put("host", host);
		respMap.put("expire", String.valueOf(expireEndTime / 1000));

		JSONObject jasonCallback = new JSONObject();
		jasonCallback.put("callbackUrl", callbackUrl);
		jasonCallback.put("callbackBody",
				"filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
		jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
		String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
		respMap.put("callback", base64CallbackBody);

		JSONObject ja1 = JSONObject.parseObject(JSON.toJSONString(respMap));;
		return ja1;
	}
	
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
	public boolean VerifyOSSCallbackRequest(String ossCallbackBody, String autorizationInput, String pubKeyInput, String queryString, String uri)//HttpServletRequest request, String ossCallbackBody)
            throws NumberFormatException, IOException {
        boolean ret = false;
        //String autorizationInput = new String(request.getHeader("Authorization"));
        //String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            System.out.println("pub key addr must be oss addrss");
            return false;
        }
        String retString = getPublicKey(pubKeyAddr);
        retString = retString.replace("-----BEGIN PUBLIC KEY-----", "");
        retString = retString.replace("-----END PUBLIC KEY-----", "");
        //String queryString = request.getQueryString();
        //String uri = request.getRequestURI();
        String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
        String authStr = decodeUri;
        if (queryString != null && !queryString.equals("")) {
            authStr += "?" + queryString;
        }
        authStr += "\n" + ossCallbackBody;
        ret = doCheck(authStr, authorization, retString);
        return ret;
    }
	
	@SuppressWarnings({ "finally" })
	private String getPublicKey(String url) {
		BufferedReader in = null;

		String content = null;
		try {
			// 定义HttpClient
			@SuppressWarnings("resource")
			DefaultHttpClient client = new DefaultHttpClient();
			// 实例化HTTP方法
			HttpGet request = new HttpGet();
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			content = sb.toString();
		} catch (Exception e) {
		} finally {
			if (in != null) {
				try {
					in.close();// 最后要关闭BufferedReader
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return content;
		}
	}
	
	/**
	 * 验证RSA
	 * 
	 * @param content
	 * @param sign
	 * @param publicKey
	 * @return
	 */
	private boolean doCheck(String content, byte[] sign, String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
			java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
			signature.initVerify(pubKey);
			signature.update(content.getBytes());
			boolean bverify = signature.verify(sign);
			return bverify;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	  * 获取STS临时凭证
	 * @return
	 */
	public Credentials getAcs() {        
        String roleSessionName = "lfs";
        String policy = "{\r\n" + 
        		"    \"Statement\": [\r\n" + 
        		"        {\r\n" + 
        		"            \"Effect\": \"Allow\",\r\n" + 
        		"            \"Action\": [\r\n" + 
        		"                \"oss:PutObject\"\r\n" + 
        		"            ],\r\n" + 
        		"            \"Resource\": [\r\n" + 
        		"                \"acs:oss:*:*:lfs-image\",\r\n" + 
        		"                \"acs:oss:*:*:lfs-image/*\"\r\n" + 
        		"            ]\r\n" + 
        		"        }\r\n" + 
        		"    ],\r\n" + 
        		"    \"Version\": \"1\"\r\n" + 
        		"}";
     
        // 添加endpoint（直接使用STS endpoint，前两个参数留空，无需添加region ID）
        //DefaultProfile.addEndpoint("", "Sts", "");
        // 构造default profile（参数留空，无需添加region ID）
        //IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
        
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        //构造请求，设置参数。关于参数含义和设置方法，请参见API参考。
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        request.setPolicy(policy); // 若policy为空，则用户将获得该角色下所有权限，设置可以进一步控制其权限
        request.setDurationSeconds(1000L); // 设置凭证有效时间，默认3600秒
        
        //发起请求，并得到响应。
        try {
            AssumeRoleResponse response = client.getAcsResponse(request);
            Credentials credentials = response.getCredentials();
            return credentials;
        } catch (ServerException e) {
        	e.printStackTrace();
        	throw new HandleException(ErrorCode.ALI_NET_ERROR, e.getErrMsg());
        } catch (ClientException e) {
        	e.printStackTrace();
        	throw new HandleException(ErrorCode.ALI_NET_ERROR, e.getErrMsg());
        }        
    }
	
}
