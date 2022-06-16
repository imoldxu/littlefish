package com.x.data.dto;

import java.util.List;

import lombok.Data;

@Data
public class ScheduleDTO {

	private String plan;
	
	private List<TodoDTO> todoList;
	
}
