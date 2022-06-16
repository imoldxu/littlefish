package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum UserLevel implements IEnum<Integer>{
	NORMAL(1, "普通用户"),
	COPPER(2,"铜牌用户"),
	SILVER(3,"银牌用户"),
	GOLD(4,"金牌用户"),
	EXPERIENCE(5,"体验用户"),
	PARTNER(6,"合伙用户");
	
	
	private int value;
	private String name;
	
	private UserLevel(int value, String name) {
		this.value = value;
		this.name = name;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
}
