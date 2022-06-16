package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.x.config.validator.PhoneFormat;

import lombok.Data;

/**
 * 登录
 * @author 老徐
 *
 */
@Data
public class LoginBo {

	@PhoneFormat
	private String phone;
	
	@NotBlank
	@Length(max=32)
	private String password;
	
}
