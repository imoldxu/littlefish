package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import com.x.config.validator.IDCardFormat;
import com.x.config.validator.PhoneFormat;

import lombok.Data;

@Data
public class AddTouristBo {

	@NotBlank
	private String name;
	
	private String surname;
	
	private String givenName;
	
	@PhoneFormat
	private String phone;
	@NotBlank
	private String idType;
	@IDCardFormat
	private String idNo;
	
}
