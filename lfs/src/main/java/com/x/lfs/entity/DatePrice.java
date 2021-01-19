package com.x.lfs.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.tools.util.MoneyUtil;

@Document(collection="datePrice")
public class DatePrice {

	@Id
	private String id;
	
	private String skuId; //套餐id

	//金额转换交由前台自己控制展示形式
	//@JSONField(serializeUsing=MoneyUtil.class)
	private Integer adultPrice;
	
	//金额转换交由前台自己控制展示形式
	//@JSONField(serializeUsing=MoneyUtil.class)
	private Integer childPrice;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@JsonFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
