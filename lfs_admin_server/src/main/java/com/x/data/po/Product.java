package com.x.data.po;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.x.data.dto.ScheduleDTO;
import com.x.data.pojo.enums.UpperOrLowerState;
import com.x.data.pojo.enums.ProductType;

import lombok.Data;

@Data
@Document("product")
public class Product {

	@Id
	private String id;
	
	private String title;
	
	private List<String> departPlace;
	
	private List<String> destination;
	
	private ProductType type;
	
	private Integer days;
	
	private Integer nights;
	
	private List<String> imageUrls;
	
	private List<String> tags;
	
	private String introduction;//图文介绍html
	
	private List<ScheduleDTO> schedules;
	
	private String includeFee; //includeFee不需要了解细节
	
	private String excludeFee; //excludeFee不需要了解细节
	
	private String policy;//政策
	
	private String instructions;
	
	private UpperOrLowerState state;
	
}
