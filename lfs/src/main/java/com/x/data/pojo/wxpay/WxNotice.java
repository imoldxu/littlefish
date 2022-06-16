package com.x.data.pojo.wxpay;

import lombok.Data;

/**
 * 支付通知
 * @author 老徐
 *
 */
@Data
public class WxNotice {

	private String id;
	
	private String create_time;
	
	private String event_type;
	
	private String resource_type;
	
	private NoticeResource resource;
	
	private String 	summary;
}
