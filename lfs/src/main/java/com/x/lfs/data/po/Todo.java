package com.x.lfs.data.po;

import java.util.List;

import lombok.Data;

@Data
public class Todo {

	public static final int FREE = 1;//自由活动
	public static final int MEAL = 2;//餐饮
	public static final int HOTEL = 3; //住宿
	public static final int TRAFFIC = 4; //交通
	public static final int SCENIC_SPOT = 5; //景点
	
	private int type;
	
	private String time;
	
	private String title;
	
	private String info;
	
	private List<String> imageUrlList;
	
}
