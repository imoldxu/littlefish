package com.x.data.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.commons.fastjson.OssSerializer;
import com.x.commons.fastjson.XJSONStringSerializer;
import com.x.data.pojo.enums.UpperOrLowerState;
import com.x.data.pojo.enums.ProductType;

import lombok.Data;

@Data
public class ProductVo {

	private String id;
	
	private String title;
	
	private List<String> departPlace;
	
	private List<String> destination;
	
	@JSONField(serializeUsing=MyEnumSerializer.class)
	private ProductType type;
	
	private Integer days;
	
	private Integer nights;
	
	@JSONField(serializeUsing=OssSerializer.class)
	private List<String> imageUrls;
	
	private List<String> tags;
	
	private String introduction;//图文介绍html
	
	private List<ScheduleVo> schedules;
	
	@JSONField(serializeUsing = XJSONStringSerializer.class)
	private String includeFee; //includeFee不需要了解细节
	
	@JSONField(serializeUsing = XJSONStringSerializer.class)
	private String excludeFee; //excludeFee不需要了解细节
	
	@JSONField(serializeUsing = XJSONStringSerializer.class)
	private String policy;//政策
	
	private String instructions;
	
	@JSONField(serializeUsing=MyEnumSerializer.class)
	private UpperOrLowerState state;
	
}
