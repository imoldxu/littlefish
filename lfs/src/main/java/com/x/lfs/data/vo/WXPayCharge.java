package com.x.lfs.data.vo;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class WXPayCharge{

	private String appId;
	
	private String timeStamp;
	
	private String nonceStr;
	
	@JSONField(name="package")
	private String prePayStr;
	
	private String signType;
	
	private String paySign;
	
}
