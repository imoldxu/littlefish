package com.x.lfs.context.bo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Valid
public class AddGroupTourBo {

	@NotBlank
	private String title;
	
	private Integer days;//几天
	
	private Integer nights;//几晚
	
	private List<String> imageUrls;
	
	private List<String> tags;
	
	@NotEmpty
	private List<AddSkuBo> skus;
	
	@NotBlank
	private String departPlace;//出发地

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getNights() {
		return nights;
	}

	public void setNights(Integer nights) {
		this.nights = nights;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<AddSkuBo> getSkus() {
		return skus;
	}

	public void setSkus(List<AddSkuBo> skus) {
		this.skus = skus;
	}

	public String getDepartPlace() {
		return departPlace;
	}

	public void setDepartPlace(String departPlace) {
		this.departPlace = departPlace;
	}

}
