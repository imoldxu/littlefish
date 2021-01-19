package com.x.lfs.context.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

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
	
	//private String password;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWxUnionId() {
		return wxUnionId;
	}

	public void setWxUnionId(String wxUnionId) {
		this.wxUnionId = wxUnionId;
	}

	public String getWxMiniOpenId() {
		return wxMiniOpenId;
	}

	public void setWxMiniOpenId(String wxMiniOpenId) {
		this.wxMiniOpenId = wxMiniOpenId;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getLastlogintime() {
		return lastlogintime;
	}

	public void setLastlogintime(Date lastlogintime) {
		this.lastlogintime = lastlogintime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getWxSessionKey() {
		return wxSessionKey;
	}

	public void setWxSessionKey(String wxSessionKey) {
		this.wxSessionKey = wxSessionKey;
	}
	
}
