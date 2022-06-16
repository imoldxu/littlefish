package com.x.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.x.service.WxMiniProgramService;
import com.x.service.WxOfficialAccountService;


@Component
public class WxTask {
	
	@Autowired
	WxMiniProgramService wxMiniService;
	@Autowired
	WxOfficialAccountService wxOfficialAccountService;
	
	//每隔一个小时刷新一次wx小程序的token
	@Scheduled(fixedRate=3600000)
	@CachePut(value="wechat_mini_access_token", unless="#result == null")
	public String refreshMiniAccessToken() {
		try {
			String token = wxMiniService.getAccessToken();
			return token;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//每隔一个小时刷新一次wx公众号的token
	@Scheduled(fixedRate=3600000)
	@CachePut(value="wechat_oa_access_token", unless="#result == null")
	public String refreshAccessToken() {
		try {
			String token = wxOfficialAccountService.getToken();
			return token;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
