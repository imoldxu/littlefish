package com.x.lfs.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("广告位")
public class BannerController {

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "获取广告位信息", notes = "获取广告位信息")
	public List<String> getBanner(){
		
		
		return null;
	}
	
}
