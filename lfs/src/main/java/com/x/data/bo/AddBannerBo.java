package com.x.data.bo;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.OssSerializer;

import lombok.Data;

@Data
public class AddBannerBo {

	@NotBlank
	private String name;//活动名称
	
	@NotBlank
	@JSONField(deserializeUsing=OssSerializer.class)
	private String resUrl; //资源路径
	
	private String redirectTo; //跳转路径
	
	private Date startDate; //开始时间
	
	private Date endDate; //结束时间
}
