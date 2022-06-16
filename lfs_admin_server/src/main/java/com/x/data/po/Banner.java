package com.x.data.po;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="banner")
public class Banner {

	@Id
	private String id;
	
	private String name;//广告名称
	
	private String resUrl; //资源路径
	
	private String redirectTo; //跳转路径
	
	private Date startDate; //开始时间
	
	private Date endDate; //结束时间
	
	private Date createTime;//创建时间
}
