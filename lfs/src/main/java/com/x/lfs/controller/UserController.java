package com.x.lfs.controller;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.commons.util.SessionUtil;
import com.x.lfs.data.bo.WxMiniProgramLoginBo;
import com.x.lfs.data.bo.WxUserInfoBo;
import com.x.lfs.data.vo.UserVo;
import com.x.lfs.service.impl.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("用户接口")
public class UserController{

	@Autowired
	UserServiceImpl userService;
	
	@RequestMapping(value = "/mini/session", method = RequestMethod.POST)
	@ApiOperation(value = "微信小程序登录", notes = "微信小程序登录")
	public UserVo loginByMiniProgram(
			@ApiParam(name = "wxCode", value = "微信授权码") @RequestBody @Valid WxMiniProgramLoginBo wxMiniProgramLoginBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		UserVo user = userService.loginByWxMiniprogram(wxMiniProgramLoginBo.getCode());
		String sessionId = request.getSession().getId();
		sessionId = Base64.getEncoder().encodeToString(sessionId.getBytes());
		user.setSessionId(sessionId);
		SessionUtil.set(request, SessionUtil.SESSION_KEY, user);
		
		return user;
	}
	
	@RequestMapping(value = "/mini/info", method = RequestMethod.PUT)
	@ApiOperation(value = "更新用户信息", notes = "更新用户信息")
	public UserVo updateUserInfo(
			@ApiParam(name = "wxUserInfoBo", value = "微信用户信息") @RequestBody @Valid WxUserInfoBo wxUserInfoBo,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
			
		user = userService.updateUserInfo(user, wxUserInfoBo);
		SessionUtil.set(request, SessionUtil.SESSION_KEY, user);
		
		return user;
	}	

	@RequestMapping(value = "/test/login", method = RequestMethod.POST)
	@ApiOperation(value = "临时用户登陆", notes = "临时用户登陆")
	public UserVo testlogin(
			HttpServletRequest request, HttpServletResponse response) {
		
		UserVo user = new UserVo();
		user.setId("5e6a5aafd12a915a60d1c79c");

		SessionUtil.set(request, SessionUtil.SESSION_KEY, user);
		
		return user;
	}	
}
