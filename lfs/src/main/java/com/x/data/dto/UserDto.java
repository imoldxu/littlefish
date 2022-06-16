package com.x.data.dto;

import java.util.Date;

import lombok.Data;

/**
    * 缓存在服务端的用户对象
 * @author 老徐
 *
 */
@Data
public class UserDto {

	private String id;
	
	private String nickName; //是否统一为nick
	 
	private String avatarUrl; //是否统一为headImgUrl
	
	private String name;
	
	private Integer gender;

	private Integer idType;
	
	private String idNum;
	
	private String birthday;
	
	private String phone;
	
	private String wxUnionId;
	
	private String wxMiniOpenId;
	
	private String wxOfficialAccountOpenId;
	
	private Date createTime;
	
	private Date lastLoginTime;

	private String province;
	
	private String city;
	
	private String country;	
	
	private int age;//年龄，年龄是实时计算的
    
    private int subscribe;//是否关注微信公众号
    
    private String sessionId;//小程序本地session
    
    private String wxSessionKey;
}
