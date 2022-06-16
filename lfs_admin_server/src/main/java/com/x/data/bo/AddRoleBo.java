package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddRoleBo {

	@NotBlank
	private String name;
	
	@NotBlank
	private String key;
}
