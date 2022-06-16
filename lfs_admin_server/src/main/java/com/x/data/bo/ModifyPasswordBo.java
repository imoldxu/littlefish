package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ModifyPasswordBo {

	@NotBlank
	private String oldPassword;
	
	@NotBlank
	private String newPassword;
	
}
