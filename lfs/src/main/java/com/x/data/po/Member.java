package com.x.data.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.x.data.pojo.enums.IDType;

import lombok.Data;

@Document("userMember")
@Data
public class Member {

	@Id
	private String id;
	
	private Long uid;
	
	private String name;
	
	private IDType idType;
	
	private String idNumber;
	
	private String phone;
	
	private Integer deleted;
}
