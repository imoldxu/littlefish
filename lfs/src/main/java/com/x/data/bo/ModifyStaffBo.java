package com.x.data.bo;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.x.config.validator.PhoneFormat;

import lombok.Data;

@Data
public class ModifyStaffBo {

	@Positive
	private Integer id;
	
	@NotBlank
	private String name;
	
	@PhoneFormat
	private String phone;
	
	@NotBlank
	private Set<String> roles;
}
