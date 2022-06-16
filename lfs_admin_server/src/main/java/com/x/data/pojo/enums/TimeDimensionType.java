package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum TimeDimensionType  implements IEnum<Integer>{

	YEAR(1),
	MONTH(2),
	DAY(3), 
	HOUR(4); 
	
	private int value;
	
	private TimeDimensionType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}
