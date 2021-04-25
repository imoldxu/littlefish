//package com.x.lfs.config.wx;
//
//import java.io.UnsupportedEncodingException;
//import java.security.PrivateKey;
//
//import org.apache.http.client.HttpClient;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
//import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
//import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
//import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
//import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
//
//
//不在此处进行构建client端，移动到service的创建之初构建
//@Configuration
//public class WxPayConfig {
//
//	@Value("${wx.pay.merchantId}")
//	private String merchantId;
//	
//	@Value("${wx.pay.merchantSerialNumber}")
//	private String merchantSerialNumber;
//	
//	@Value("${wx.pay.merchantPrivateKey}")
//	private PrivateKey merchantPrivateKey;
//	
//	//private List<X509Certificate> wechatpayCertificates;
//	
//	
//	@Bean(name="wxPayClient")
//	public HttpClient wxPayClient() throws UnsupportedEncodingException {
//		
//		//不需要传入微信支付证书，将会自动更新
//		AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
//		        new WechatPay2Credentials(merchantId, new PrivateKeySigner(merchantSerialNumber, merchantPrivateKey)),
//		        "apiV3Key".getBytes("utf-8"));
//		
//		WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
//		        .withMerchant(merchantId, merchantSerialNumber, merchantPrivateKey)
//		        .withValidator(new WechatPay2Validator(verifier));
//		        //.withWechatpay(wechatpayCertificates);
//		// ... 接下来，你仍然可以通过builder设置各种参数，来配置你的HttpClient
//
//		// 通过WechatPayHttpClientBuilder构造的HttpClient，会自动的处理签名和验签
//		HttpClient httpClient = builder.build();
//		return httpClient;
//	}
//	
//	/**
//	 * 初始化下载证书时，或不需要校验时使用此client
//	 * @return
//	 * @throws UnsupportedEncodingException
//	 */
//	@Bean(name="wxPayNoneValidClient")
//	public HttpClient wxPayNoneValidClient() throws UnsupportedEncodingException {
//		
//		CloseableHttpClient httpClient = WechatPayHttpClientBuilder.create()
//				  .withMerchant(merchantId, merchantSerialNumber, merchantPrivateKey)
//				  .withValidator(response -> true) // NOTE: 设置一个空的应答签名验证器，**不要**用在业务请求
//				  .build();
//		return httpClient;
//	}
//	
//}
