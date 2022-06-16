package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum CouponValidType  implements IEnum<Integer>{
	
	FIXDATE(1, "固定有效期"),
	DURATION(2, "持续时间"),
	ONLY_TODAY(3, "当日有效");

	private int value;
	
	private String des;
	
	private CouponValidType(int value, String des) {
		this.value = value;
		this.des = des;
	}
	
	public String getDes() {
		return this.des;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}