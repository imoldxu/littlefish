package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import com.x.config.validator.PhoneFormat;

import lombok.Data;

@Data
public class AdminLoginBo {

	@NotBlank
	@PhoneFormat
	private String phone;
	
	@NotBlank
	private String password;
	
}
