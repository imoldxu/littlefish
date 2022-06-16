package com.x.data.bo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class BatchSetPriceCalendarBo {

	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
	private Integer[] dayOfWeeks; //星期几
	
	@NotNull
	private String skuId; //套餐id

	@PositiveOrZero
	private Integer adultPrice;
	
	@PositiveOrZero
	private Integer childPrice;

	@PositiveOrZero
	private Integer singleRoomPrice;
	
}
