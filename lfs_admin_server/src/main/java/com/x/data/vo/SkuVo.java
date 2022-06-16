package com.x.data.vo;

import org.springframework.data.annotation.Id;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.commons.fastjson.XJSONStringSerializer;
import com.x.data.pojo.enums.UpperOrLowerState;

import lombok.Data;

/**
 * 套餐
 * @author 老徐
 *
 */
@Data
public class SkuVo {

	@Id
	private String id;//objectId
	
	private String productId;
	
	private String name;
	
	@JSONField(serializeUsing=XJSONStringSerializer.class)
	private String properties;
	
	@JSONField(serializeUsing=MyEnumSerializer.class)
	private UpperOrLowerState state;
	
}
