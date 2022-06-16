package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum IncomeType implements IEnum<Integer>{
	IN(1),
	OUT(0);
	
	private int value;
	
	private IncomeType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}