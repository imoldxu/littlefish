package com.x.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.x.constant.ErrorCode;
import com.x.data.bo.AddStaffBo;
import com.x.data.bo.LoginBo;
import com.x.data.bo.ModifyPasswordBo;
import com.x.data.bo.ModifyStaffBo;
import com.x.data.bo.StaffQuery;
import com.x.data.dto.StaffDto;
import com.x.data.po.Staff;
import com.x.data.pojo.enums.CommonState;
import com.x.data.vo.PageResult;
import com.x.data.vo.StaffVo;
import com.x.exception.HandleException;
import com.x.service.StaffService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/staff")
@Api("员工接口")
public class StaffController {

	@Autowired
	StaffService staffService;
	@Autowired
	MapperFacade orikaMapper;
	
	@RequiresRoles({"admin"})
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "注册员工账户", notes = "管理员注册接口")
	public StaffVo register(@ApiParam(name="addStaffBo",value="添加员工信息") @Valid @RequestBody AddStaffBo addStaffBo) {
		StaffDto dto = staffService.register(addStaffBo);	
		StaffVo vo = orikaMapper.map(dto, StaffVo.class);
		return vo;
	}
	
	@RequiresRoles({"admin"})
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询员工", notes = "查询员工")
	public PageResult<StaffVo> query(@ApiParam(name="query",value="查询") @Valid StaffQuery query) {
		
		IPage<StaffDto> pageData = staffService.queryStaff(query);
		
		List<StaffVo> pageVo = orikaMapper.mapAsList(pageData.getRecords(), StaffVo.class);
		
		PageResult<StaffVo> pageResult = new PageResult<StaffVo>();
		pageResult.setTotal(pageData.getTotal());
		pageResult.setData(pageVo);
		pageResult.setSuccess(true);

		return pageResult;
	}
	
	@RequiresRoles({"admin"})
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "更新员工信息", notes = "更新员工信息")
	public StaffVo update(@ApiParam(name="modifyStaffBo",value="管理员") @Valid @RequestBody ModifyStaffBo modifyStaffBo) {	
		StaffDto staff = staffService.updateStaff(modifyStaffBo);
		StaffVo vo = orikaMapper.map(staff, StaffVo.class);
		return vo;
	}
	
	@RequiresRoles({"admin"})
	@RequestMapping(path="/{id}/state/enable", method = RequestMethod.PATCH)
	@ApiOperation(value = "启/停员工", notes = "启/停员工")
	public void enable(@PathVariable("id") Integer id) {	
		
		staffService.changeState(id, CommonState.VALID);
		return;	
	}
	
	@RequiresRoles({"admin"})
	@RequestMapping(path="/{id}/state/disable", method = RequestMethod.PATCH)
	@ApiOperation(value = "启/停管理员", notes = "启/停管理员")
	public void disable(@PathVariable("id") Integer id) {	
		
		staffService.changeState(id, CommonState.INVALID);
		return;	
	}
	
	@RequiresRoles({"admin"})
	@RequestMapping(path="/{id}/password", method = RequestMethod.PATCH)
	@ApiOperation(value = "重置管理员密码", notes = "重置管理员密码")
	public void resetPassword(@PathVariable("id") Integer id) {	
		staffService.resetPassword(id);
		return;	
	}
	
	@RequestMapping(value = "/session", method = RequestMethod.POST)
	@ApiOperation(value = "员工登陆", notes = "员工登陆接口")
	public StaffVo login(@ApiParam(name="loginBo",value="登陆信息") @Valid @RequestBody LoginBo loginBo) {
		Subject subject = SecurityUtils.getSubject();
		subject.getSession().setTimeout(4*3600*1000);//8个小时过期,避免收费处重复登录
		AuthenticationToken token = new UsernamePasswordToken(loginBo.getPhone(), loginBo.getPassword());
		subject.login(token);
		StaffDto staff = (StaffDto) subject.getPrincipal();
		
		StaffVo ret = orikaMapper.map(staff, StaffVo.class);
		
		return ret;
	}
	
	@RequestMapping(path="/password", method = RequestMethod.PATCH)
	@ApiOperation(value = "修改密码", notes = "修改密码")
	public void modifyPassword(@ApiParam(name="modifyPasswordBo",value="修改密码信息") @Valid @RequestBody ModifyPasswordBo modifyPasswordBo) {
		Subject subject = SecurityUtils.getSubject();
		Staff staff = (Staff) subject.getPrincipal();	
		staffService.modifyPassword(staff.getId(), modifyPasswordBo);	
		return;
	}
	
	@RequestMapping(value = "/session", method = RequestMethod.GET)
	@ApiOperation(value = "管理员信息", notes = "管理员信息")
	public StaffVo getSessionInfo() {
		Subject subject = SecurityUtils.getSubject();
		StaffDto currentStaff = (StaffDto) subject.getPrincipal();
		if(currentStaff == null) {
			throw new HandleException(ErrorCode.UNLOGIN, "还未登陆");
		}
		StaffVo ret = orikaMapper.map(currentStaff, StaffVo.class);
		return ret;
	}
	
	@RequestMapping(value = "/session", method = RequestMethod.DELETE)
	@ApiOperation(value = "管理员登出", notes = "管理员登出接口")
	public void logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}
}
