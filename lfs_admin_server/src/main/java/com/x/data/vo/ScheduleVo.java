package com.x.data.vo;

import java.util.List;

import lombok.Data;

@Data
public class ScheduleVo {

	private String plan;
	
	private List<TodoVo> todoList;
	
}
