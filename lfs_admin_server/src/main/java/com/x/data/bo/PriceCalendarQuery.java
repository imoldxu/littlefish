package com.x.data.bo;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PriceCalendarQuery {
	
	@NotBlank
	private String skuId;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;
	
}
