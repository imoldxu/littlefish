package com.x.lfs.context.bo;

import javax.validation.constraints.NotBlank;

import com.x.lfs.config.validator.IDCardFormat;
import com.x.lfs.config.validator.PhoneFormat;

public class AddTouristBo {

	@NotBlank
	private String name;
	@PhoneFormat
	private String phone;
	@NotBlank
	private String idType;
	@IDCardFormat
	private String idNo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	
}
