package com.x.lfs.context.bo;

import javax.validation.constraints.NotBlank;

public class WxMiniProgramLoginBo {

	@NotBlank
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
