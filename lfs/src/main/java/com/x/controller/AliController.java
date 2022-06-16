//package com.x.controller;
//
//import java.io.IOException;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSONObject;
//import com.aliyuncs.auth.sts.AssumeRoleResponse.Credentials;
//import com.x.service.AliYunOssService;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//
//@RestController
//@RequestMapping("/aliyun")
//@Api("aliyun接口")
//public class AliController {
//
//	@Autowired
//	AliYunOssService ossService;
//	
//	@RequestMapping(path="/sts", method = RequestMethod.GET)
//	@ApiOperation(value = "客户端获取临时STS授权", notes = "客户端获取临时STS授权")
//	public Credentials getSTS(){
//		Credentials ret = ossService.getAcs();
//		return ret;
//	}
//	
//	@RequestMapping(path="/oss/signature", method = RequestMethod.GET)
//	@ApiOperation(value = "客户端获取临时上传的签名", notes = "获取临时上传的签名")
//	public JSONObject getSign(){
//		JSONObject ret = ossService.sign();
//		return ret;
//	}
//	
//	@RequestMapping(path="/oss/callback", method = RequestMethod.POST)
//	@ApiOperation(value = "上传对象的回调", notes = "上传对象的回调")
//	public void callBack(@RequestBody String ossCallbackBody, HttpServletRequest request, HttpServletResponse response) throws NumberFormatException, IOException{
//		String autorizationInput = request.getHeader("Authorization");
//		String pubKeyInput = request.getHeader("x-oss-pub-key-url");
//		String queryString = request.getQueryString();
//		String uri = request.getRequestURI();
//		boolean isVerify = ossService.VerifyOSSCallbackRequest(ossCallbackBody, autorizationInput, pubKeyInput, queryString, uri);
//		if (isVerify) {
//			response(request, response, "{\"Status\":\"OK\"}", HttpServletResponse.SC_OK);
//		} else {
//			response(request, response, "{\"Status\":\"verdify not ok\"}", HttpServletResponse.SC_BAD_REQUEST);
//		}
//		return;
//	}
//	
//	private void response(HttpServletRequest request, HttpServletResponse response, String results, int status)
//			throws IOException {
//		String callbackFunName = request.getParameter("callback");
//		response.addHeader("Content-Length", String.valueOf(results.length()));
//		if (callbackFunName == null || callbackFunName.equalsIgnoreCase(""))
//			response.getWriter().println(results);
//		else
//			response.getWriter().println(callbackFunName + "( " + results + " )");
//		response.setStatus(status);
//		response.flushBuffer();
//	}
//	
//}
