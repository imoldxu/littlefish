package com.x.lfs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.commons.mongo.pageHelper.PageResult;
import com.x.lfs.data.bo.AddGroupTourBo;
import com.x.lfs.data.bo.GroupTourQuery;
import com.x.lfs.data.bo.ModifyGroupTourBo;
import com.x.lfs.data.po.GroupTour;
import com.x.lfs.data.vo.GroupTourItemVo;
import com.x.lfs.data.vo.GroupTourVo;
import com.x.lfs.service.GroupTourService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/grouptour")
@Api("跟团游接口")
public class GroupTourController {

	@Autowired
	GroupTourService groupTourService;
	@Autowired
	MapperFacade orikaMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询跟团游列表", notes = "查询跟团游列表")
	public PageResult<GroupTourItemVo> query(
			@ApiParam(name = "groupTourQuery", value = "日期查询条件") @Valid GroupTourQuery groupTourQuery,
			HttpServletRequest request, HttpServletResponse response) {
	
		PageResult<GroupTourItemVo> result = groupTourService.query(groupTourQuery);
		return result;
	}
	
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "查询跟团游详情", notes = "查询跟团游详情")
	public GroupTourVo getById(
			@ApiParam(name = "id", value = "跟团游id") @PathVariable(name="id") @NotBlank String id,
			HttpServletRequest request, HttpServletResponse response) {
		
		GroupTour result = groupTourService.getById(id);
		GroupTourVo ret = orikaMapper.map(result, GroupTourVo.class);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加跟团游", notes = "添加跟团游")
	public GroupTourVo add(
			@ApiParam(name = "groupTourBo", value = "日期查询条件")  @RequestBody @Valid AddGroupTourBo groupTourBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		GroupTour gt = groupTourService.add(groupTourBo);
		GroupTourVo ret = orikaMapper.map(gt, GroupTourVo.class);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "修改跟团游信息", notes = "修改跟团游信息")
	public GroupTourVo modify(
			@ApiParam(name = "groupTour", value = "日期查询条件") @RequestBody @Valid ModifyGroupTourBo groupTour,
			HttpServletRequest request, HttpServletResponse response) {
	
		GroupTour gt = groupTourService.modify(groupTour);
		GroupTourVo ret = orikaMapper.map(gt, GroupTourVo.class);
		return ret;
	}
	
}
