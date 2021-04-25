package com.x.lfs.controller;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.constant.ErrorCode;
import com.x.lfs.data.bo.AddAdminBo;
import com.x.lfs.data.bo.AdminLoginBo;
import com.x.lfs.data.po.Admin;
import com.x.lfs.exception.HandleException;
import com.x.lfs.service.AdminService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/admin")
@Api("管理员接口")
public class AdminController {

	@Autowired
	AdminService adminService;
	
	@RequestMapping(value = "/session", method = RequestMethod.POST)
	@ApiOperation(value = "管理员登陆", notes = "管理员登陆接口")
	public Admin login(@ApiParam(name="loginBo",value="登陆信息") @Valid @RequestBody AdminLoginBo loginBo) {
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setTimeout(4*3600*1000);//8个小时过期,避免收费处重复登录
		AuthenticationToken token = new UsernamePasswordToken(loginBo.getPhone(), loginBo.getPassword());
		subject.login(token);
		Admin user = (Admin) subject.getPrincipal();
		return user;
	}
	
	@RequestMapping(value = "/session", method = RequestMethod.DELETE)
	@ApiOperation(value = "管理员登出", notes = "管理员登出\"")
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "用户信息", notes = "用户信息")
	public Admin get() {
		Subject subject = SecurityUtils.getSubject();
		Admin admin = (Admin) subject.getPrincipal();
		if(admin == null) {
			throw new HandleException(ErrorCode.UNLOGIN, "还未登陆");
		}
		return admin;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "管理员注册账户", notes = "管理员注册接口")
	public void register(@ApiParam(name="addAdminBo",value="添加用户信息") @Valid @RequestBody AddAdminBo addAdminBo) {
		adminService.register(addAdminBo);	
		return;
	}
}
