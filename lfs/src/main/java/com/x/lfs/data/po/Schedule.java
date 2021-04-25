package com.x.lfs.data.po;

import java.util.List;

import lombok.Data;

@Data
public class Schedule {

	private int num;//第几天
	
	private String places; //'地点|交通类型|地点'
	
	private List<Todo> todoList;
	
}
