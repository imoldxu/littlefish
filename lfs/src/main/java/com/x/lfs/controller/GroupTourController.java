package com.x.lfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.context.bo.DatePriceQuery;
import com.x.lfs.context.bo.GroupTourQuery;
import com.x.lfs.context.vo.GroupTourItemVo;
import com.x.lfs.entity.DatePrice;
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
			@ApiParam(name = "datePriceQuery", value = "日期查询条件") @Valid GroupTourQuery groupTourQuery,
			HttpServletRequest request, HttpServletResponse response) {
	
		PageResult<GroupTourItemVo> result = groupTourService.query(groupTourQuery);
		return result;
	}
	
	
	
}
