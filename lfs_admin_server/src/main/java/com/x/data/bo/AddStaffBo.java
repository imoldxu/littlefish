package com.x.data.bo;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.x.config.validator.PhoneFormat;

import lombok.Data;

@Data
public class AddStaffBo {

	@NotBlank
	private String name;
	
	@NotBlank
	@PhoneFormat
	private String phone;
	
//	@NotBlank
//	private String role;
	
	@NotEmpty
	private Set<String> roles;
	
}
