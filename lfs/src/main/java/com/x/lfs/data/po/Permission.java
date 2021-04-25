package com.x.lfs.data.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="permission")
public class Permission {
	
	@Id
	private String id;
	
	private String name;//权限名称
	
	private String url;//权限对应的url
}