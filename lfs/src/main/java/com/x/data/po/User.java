package com.x.data.po;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection="user")
@Data
public class User {

	@Id
	private String id;
	
	private String nickName;
	
	private String avatarUrl;
	
	private String name;
	
	private Integer gender;
	
	public static final int TYPE_IDCARD = 1;
	public static final int TYPE_JG = 2;
	
	private Integer idType;
	
	private String idNum;
	
	private String birthday;
	
	@Indexed(unique=true)
	private String phone;
	
	private String password;
	
	@Indexed(unique=true)
	private String wxUnionId;
	
	@Indexed(unique=true)
	private String wxMiniOpenId;
	
	@Indexed(unique=true)
	private String wxOfficalAccountOpenId;
	
	private Date createTime;
	
	private Date lastLoginTime;

	private String province;
	
	private String city;
	
	private String country;
		
}
