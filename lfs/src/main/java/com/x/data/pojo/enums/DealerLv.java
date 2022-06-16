package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

public enum DealerLv implements IEnum<Integer>{
	LV1(1),LV2(2);
	
	private int value;
	
	private DealerLv(int i) {
		this.value = i;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}

}
