package com.x.lfs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.data.bo.banner.AddBannerBo;
import com.x.lfs.data.bo.banner.BannerQuery;
import com.x.lfs.data.po.Banner;
import com.x.lfs.service.BannerService;

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
	public List<Banner> getBanner(@ApiParam @Valid BannerQuery query){
		List<Banner> ret = bannerService.queryBanner();
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加广告位信息", notes = "添加广告位信息")
	public Banner addBanner(@ApiParam @RequestBody @Valid AddBannerBo addBannerBo){
		Banner ret = bannerService.addBanner(addBannerBo);
		return ret;
	}
	
}
