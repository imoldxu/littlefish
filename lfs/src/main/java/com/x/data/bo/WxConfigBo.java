package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class WxConfigBo {

	@NotBlank(message="url不能为空")
	private String url;
	
}
