package com.x.data.vo;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * 微信公众号配置信息
 * @author 老徐
 *
 */
@Data
public class WxConfig {

	private String appId;
	
	private String url;
	
	@JSONField(serialize=false)
	private String jsapi_ticket;
	
	private String nonceStr;
	
	private String timestamp;
	
	private String signature;

}
