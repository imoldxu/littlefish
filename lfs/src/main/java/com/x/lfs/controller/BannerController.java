package com.x.lfs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/banner")
@Api("广告位")
public class BannerController {

	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "获取广告位信息", notes = "获取广告位信息")
	public List<String> getBanner(){
		List<String> ret = new ArrayList<String>();
		ret.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2021432396,1125990300&fm=26&gp=0.jpg");
		ret.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2942349449,2891846967&fm=26&gp=0.jpg");
		ret.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3560123941,1782341381&fm=26&gp=0.jpg");
		return ret;
	}
	
}
