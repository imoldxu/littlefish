package com.x.lfs.data.pojo.wxpay;

import lombok.Data;

@Data
public class WxNotice {

	private String id;
	
	private String create_time;
	
	private String event_type;
	
	private String resource_type;
	
	private NoticeResource resource;
	
	private String 	summary;
}
