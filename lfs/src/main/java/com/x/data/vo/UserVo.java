package com.x.data.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class UserVo {
	
	@JSONField(serialize=false)
	private String id;
	
	private String nickName; //是否统一为nick
	 
	private String avatarUrl; //是否统一为headImgUrl
	
	private String name;
	
	private Integer gender;

	private Integer idType;
	
	private String idNum;
	
	private String birthday;
	
	private String phone;
	
	@JSONField(serialize=false)
	private String wxUnionId;
	
	@JSONField(serialize=false)
	private String wxMiniOpenId;
	
	@JSONField(serialize=false)
	private String wxOfficialAccountOpenId;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	private String province;
	private String city;
	private String country;	
	
	private int age;//年龄，年龄是实时计算的
    
    private int subscribe;//是否关注微信公众号
    
    private String sessionId;//小程序本地session
    
    @JSONField(serialize=false)
    private String wxSessionKey;
	
}
