package com.x.data.bo;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.data.pojo.enums.UpperOrLowerState;

import lombok.Data;

@Data
public class ModifySkuBo {
	
	@NotBlank(message="id不能为空")
	private String id;
		
	@NotBlank(message="sku名称不能为空")
	private String name;
	
	@JSONField(deserializeUsing=MyEnumSerializer.class)
	private UpperOrLowerState state;
	
	private String properties;//住宿、车、出团人数标准
	
}
