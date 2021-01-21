package com.x.lfs.entity;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="groupTour")
public class GroupTour {

	@NotBlank
	@Id
	private String id;

	private String title;//title名称

	private int days;//几天
	
	private int nights;//几晚
	
	private List<String> imageUrls;
	
	private List<String> tags;//标签，重点
	
	//国家
	//省
	//景区
	//供应商产品名称
	//供应商产品编号
	
	//预定限制
	//提前预定限制
	//儿童可预订否？
	//儿童年龄？
	//儿童身高
	//预定人数限制？
	
	private String departPlace;//出发地
	
	private List<Sku> skus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getNights() {
		return nights;
	}

	public void setNights(int nights) {
		this.nights = nights;
	}
	
	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public List<Sku> getSkus() {
		return skus;
	}

	public void setSkus(List<Sku> skus) {
		this.skus = skus;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getDepartPlace() {
		return departPlace;
	}

	public void setDepartPlace(String departPlace) {
		this.departPlace = departPlace;
	}
	
}
