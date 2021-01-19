package com.x.lfs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.context.bo.AddGroupTourBo;
import com.x.lfs.context.bo.GroupTourQuery;
import com.x.lfs.context.vo.GroupTourItemVo;
import com.x.lfs.entity.GroupTour;
import com.x.lfs.service.GroupTourService;
import com.x.tools.mongo.pageHelper.PageResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/grouptour")
@Api("跟团游接口")
public class GroupTourController {

	@Autowired
	GroupTourService groupTourService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询跟团游列表", notes = "查询跟团游列表")
	public PageResult<GroupTourItemVo> query(
			@ApiParam(name = "groupTourQuery", value = "日期查询条件") @Valid GroupTourQuery groupTourQuery,
			HttpServletRequest request, HttpServletResponse response) {
	
		PageResult<GroupTourItemVo> result = groupTourService.query(groupTourQuery);
		return result;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "查询跟团游详情", notes = "查询跟团游详情")
	public GroupTour getById(
			@ApiParam(name = "id", value = "跟团游id") @PathVariable(name="id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		
		GroupTour result = groupTourService.getById(id);
		return result;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加跟团游", notes = "添加跟团游")
	public GroupTour add(
			@ApiParam(name = "groupTourBo", value = "日期查询条件")  @RequestBody @Valid AddGroupTourBo groupTourBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		GroupTour gt = groupTourService.add(groupTourBo);
		
		return gt;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "修改跟团游信息", notes = "修改跟团游信息")
	public GroupTour modify(
			@ApiParam(name = "groupTour", value = "日期查询条件") @RequestBody @Valid GroupTour groupTour,
			HttpServletRequest request, HttpServletResponse response) {
	
		GroupTour gt = groupTourService.modify(groupTour);
		
		return gt;
	}
	
}
