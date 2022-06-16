package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WxUserInfoBo {

	
	private String rawData;
	private String signature;
	@NotBlank
	private String encryptedData;
	@NotBlank
	private String iv;
	
}
