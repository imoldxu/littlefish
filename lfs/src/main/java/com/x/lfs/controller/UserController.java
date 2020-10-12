package com.x.lfs.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.context.bo.WxUserInfoBo;
import com.x.lfs.context.vo.UserVo;
import com.x.lfs.service.UserService;
import com.x.tools.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("用户接口")
public class UserController{

	@Autowired
	UserService userService;
	
	public static final String SESSION_KEY = "user";
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/mini/session", method = RequestMethod.POST)
	@ApiOperation(value = "微信小程序登录", notes = "微信小程序登录")
	public UserVo loginByMiniProgram(
			@ApiParam(name = "wxCode", value = "微信授权码") @RequestParam(name = "wxCode") @NotBlank String wxCode,
			HttpServletRequest request, HttpServletResponse response) {
	
		UserVo user = userService.loginByWxMiniprogram(wxCode);
		String sessionID = request.getSession().getId();
		sessionID = Base64.getEncoder().encodeToString(sessionID.getBytes());
		user.setSessionID(sessionID);
		SessionUtil.set(request, SESSION_KEY, user);
		
		return user;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(value = "/mini/info", method = RequestMethod.PUT)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public UserVo updateUserInfo(
			@ApiParam(name = "wxUserInfoBo", value = "微信用户信息") @RequestBody @Valid WxUserInfoBo wxUserInfoBo,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserVo user = SessionUtil.get(request, SESSION_KEY, UserVo.class);
			
		user = userService.updateUserInfo(user, wxUserInfoBo);
		SessionUtil.set(request, SESSION_KEY, user);
		
		return user;
	}	

}
