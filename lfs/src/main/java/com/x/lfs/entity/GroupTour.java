package com.x.lfs.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="groupTour")
public class GroupTour {

	@Id
	private String id;

	private String title;

	private int days;//几天
	
	private int nights;//几晚
	
	private List<String> imageUrl;
	
	private List<String> points;
	
	private List<Package> packages;
	
	private List<String> introduceImageUrl;

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
