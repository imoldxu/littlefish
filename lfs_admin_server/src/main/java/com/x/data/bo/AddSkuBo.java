package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class AddSkuBo {
	
	@NotBlank(message="产品编号不能为空")
	private String productId;
	
	@NotBlank(message="sku名称不能为空")
	private String name;
	
	private String properties;//住宿、车、出团人数标准
	
}
