package com.x.lfs.context.bo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.x.lfs.entity.Package;

@Valid
public class AddGroupTourBo {

	@NotBlank
	private String title;
	
	private Integer days;//几天
	
	private Integer nights;//几晚
	
	private List<String> imageUrl;
	
	private List<String> points;
	
	private List<Package> packages;
	
	private List<String> introduceImageUrl;

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

	public List<String> getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(List<String> imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<String> getPoints() {
		return points;
	}

	public void setPoints(List<String> points) {
		this.points = points;
	}

	public List<Package> getPackages() {
		return packages;
	}

	public void setPackages(List<Package> packages) {
		this.packages = packages;
	}

	public List<String> getIntroduceImageUrl() {
		return introduceImageUrl;
	}

	public void setIntroduceImageUrl(List<String> introduceImageUrl) {
		this.introduceImageUrl = introduceImageUrl;
	}
	
}
