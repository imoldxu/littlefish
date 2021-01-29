package com.x.lfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.context.bo.AddTouristBo;
import com.x.lfs.context.bo.ModifyTouristBo;
import com.x.lfs.context.vo.UserVo;
import com.x.lfs.entity.Tourist;
import com.x.lfs.service.TouristService;
import com.x.tools.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/tourist")
@Api("游客接口")
public class TouristController {

	@Autowired
	TouristService touristService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询游客", notes = "查询我的游客")
	public List<Tourist> query(
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, "user", UserVo.class);
		
		List<Tourist> ret = touristService.getMyTourists(user.getId());
		
		return ret;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加游客", notes = "添加我的游客")
	public Tourist add(
			@ApiParam(name = "addTouristBo", value = "添加我的游客信息") @RequestBody @Valid AddTouristBo addTouristBo,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		Tourist ret = touristService.addMyTourist(user.getId(), addTouristBo);
		
		return ret;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "修改游客", notes = "修改我的游客")
	public Tourist modify(
			@ApiParam(name = "modifyTouristBo", value = "修改我的游客信息") @RequestBody @Valid ModifyTouristBo modifyTouristBo,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		Tourist ret = touristService.modifyMyTourist(user.getId(), modifyTouristBo);
		
		return ret;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/{id}", method = RequestMethod.DELETE)
	@ApiOperation(value = "删除游客", notes = "删除我的游客")
	public Tourist del(
			@ApiParam(name = "id", value = "游客编号") @PathVariable(name="id") @NotBlank String id,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		Tourist ret = touristService.delMyTourist(user.getId(), id);
		
		return ret;
	}
}
