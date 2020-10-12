package com.x.lfs.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.x.tools.util.RedissonCache;
import com.x.tools.util.WxMiniProgramUtil;


@Component
public class WxTask {

	@Autowired
	RedissonCache redissonCache;
	
	//每隔一个小时刷新一次wx小程序的token
	@Scheduled(fixedRate=3600000)
	public void refreshAccessToken() {
		try {
			String token = WxMiniProgramUtil.getToken();
			redissonCache.set("wechat_mini_access_token", token, 7200000L);	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
