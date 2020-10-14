package com.x.lfs.context.bo;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Valid
public class BatchAddDatePriceBo {

	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date starteDate;
	
	@NotNull
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	private Date endDate;
	
	@NotNull
	private String packageId; //套餐id

	private Integer adultPrice;
	
	private Integer childPrice;

	public Date getStarteDate() {
		return starteDate;
	}

	public void setStarteDate(Date starteDate) {
		this.starteDate = starteDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public Integer getAdultPrice() {
		return adultPrice;
	}

	public void setAdultPrice(Integer adultPrice) {
		this.adultPrice = adultPrice;
	}

	public Integer getChildPrice() {
		return childPrice;
	}

	public void setChildPrice(Integer childPrice) {
		this.childPrice = childPrice;
	}
	
}
