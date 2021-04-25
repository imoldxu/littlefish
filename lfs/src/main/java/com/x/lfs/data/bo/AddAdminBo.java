package com.x.lfs.data.bo;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.x.lfs.config.validator.PhoneFormat;
import com.x.lfs.data.po.Role;

import lombok.Data;

@Data
public class AddAdminBo {

	@NotBlank
	private String name;
	
	@NotBlank
	@PhoneFormat
	private String phone;
		
	@NotEmpty
	private Set<Role> roles;
	
}
