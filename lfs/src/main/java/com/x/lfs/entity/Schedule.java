package com.x.lfs.entity;

import java.util.List;

public class Schedule {

	private int num;//第几天
	
	private String places; //'地点|交通类型|地点'
	
	private List<Todo> todoList;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPlaces() {
		return places;
	}

	public void setPlaces(String places) {
		this.places = places;
	}

	public List<Todo> getTodoList() {
		return todoList;
	}

	public void setTodoList(List<Todo> todoList) {
		this.todoList = todoList;
	}
	
}
