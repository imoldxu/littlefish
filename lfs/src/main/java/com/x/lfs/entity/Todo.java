package com.x.lfs.entity;

import java.util.List;

public class Todo {

	public static final int FREE = 1;//自由活动
	public static final int MEAL = 2;//餐饮
	public static final int HOTEL = 3; //住宿
	public static final int SCENIC_SPOT = 4; //景点
	
	private int type;
	
	private String time;
	
	private String todo;
	
	private String info;
	
	private List<String> imageUrlList;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<String> getImageUrlList() {
		return imageUrlList;
	}

	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}
	
	
}
