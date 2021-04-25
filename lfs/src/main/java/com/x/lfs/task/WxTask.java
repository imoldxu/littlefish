package com.x.lfs.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.x.lfs.service.impl.WxMiniProgramService;


@Component
public class WxTask {
	
	@Autowired
	WxMiniProgramService wxMiniService;
	
	//每隔一个小时刷新一次wx小程序的token
	@Scheduled(fixedRate=3600000)
	@CachePut(value="wechat_mini_access_token", unless="#result == null")
	public String refreshAccessToken() {
		try {
			String token = wxMiniService.getToken();
			return token;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
