package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

public enum AccountType implements IEnum<Integer>{
	POINTS(1),
	BONUS(2);
	
	private int value;
	
	private AccountType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}