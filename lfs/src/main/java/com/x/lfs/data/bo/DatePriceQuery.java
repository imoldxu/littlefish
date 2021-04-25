package com.x.lfs.data.bo;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class DatePriceQuery {
	
	@NotBlank
	private String skuId;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startDate;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endDate;

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
}
