package com.x.lfs.data.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class DatePriceVo {

	private String id;
	
	private String skuId; //套餐id

	private Integer adultPrice;
	
	private Integer childPrice;
	
	@JSONField(format="yyyy-MM-dd")
	private Date date;
	
}
