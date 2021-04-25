package com.x.lfs.data.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class UserVo {
	
	@JSONField(serialize=false)
	private String id;
	
	private String nickName;
	
	private String avatarUrl;
	
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
	private String wxOpenId;
	
	private Date createtime;
	
	private Date lastlogintime;

	private String province;
	private String city;
	private String country;	
	
	private int age;//年龄，年龄是实时计算的
    
    private int subscribe;//是否关注微信公众号
    
    private String sessionId;//本地session
    
    @JSONField(serialize=false)
    private String wxSessionKey;
	
}
