package com.x.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.commons.mongo.pageHelper.PageResult;
import com.x.data.bo.AddBannerBo;
import com.x.data.bo.BannerQuery;
import com.x.data.po.Banner;
import com.x.data.vo.BannerVo;
import com.x.service.BannerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/banner")
@Api("广告位")
public class BannerController {

	@Autowired
	BannerService bannerService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取广告位信息", notes = "获取广告位信息")
	public PageResult<BannerVo> getBanner(@ApiParam @Valid BannerQuery query){
		PageResult<BannerVo> ret = bannerService.queryBanner(query);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加广告位信息", notes = "添加广告位信息")
	public Banner addBanner(@ApiParam @RequestBody @Valid AddBannerBo addBannerBo){
		Banner ret = bannerService.addBanner(addBannerBo);
		return ret;
	}
	
}
