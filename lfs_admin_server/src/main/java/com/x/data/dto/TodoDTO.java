package com.x.data.dto;

import java.util.List;

import lombok.Data;

@Data
public class TodoDTO {

	private Integer type;
	
	private String time;
	
	private String duration;
	
	private String durationUnit;
	
	private String key;
	
	private String desc;//描述
	
	private List<String> images;
	
	private String trafficType;//交通类型
	
	private String mealType;//类型
	
	private Boolean mealAdult;//成人是否含餐
	
	private Boolean mealChild;//儿童是否含餐
	
	private String hotelName;//酒店名称
	
	private String scenicSpotName;//景点名称
}
