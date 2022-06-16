package com.x.data.bo;

import java.util.List;

import javax.validation.Valid;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.data.dto.ScheduleDTO;
import com.x.data.pojo.enums.ProductType;

import lombok.Data;

@Data
@Valid
public class ModifyProductBo {

	private String id;
	
	private String title;
	
	private List<String> departPlace;
	
	private List<String> destination;
	
	@JSONField(deserializeUsing=MyEnumSerializer.class)
	private ProductType type;
	
	private Integer days;
	
	private Integer nights;
	
	private List<String> imageUrls;
	
	private List<String> tags;
	
	private String introduction;//图文介绍html
	
	private List<ScheduleDTO> schedules;
	
	private String includeFee; //includeFee不需要了解细节
	
	private String excludeFee; //excludeFee不需要了解细节
	
	private String policy;//政策 限制、服务、退款政策
	
	private String instructions;
}
