package com.x.data.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.OssSerializer;

import lombok.Data;

@Data
public class BannerVo {

	private String id;
	
	private String name;//广告名称
	
	@JSONField(serializeUsing=OssSerializer.class)
	private String resUrl; //资源路径
	
	private String redirectTo; //跳转路径
	
	private Date startDate; //开始时间
	
	private Date endDate; //结束时间
	
}
