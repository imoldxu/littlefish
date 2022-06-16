package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum CouponState  implements IEnum<Integer>{
	
	UNUSED(1, "未使用"),
	USEING(2, "使用中"),
	USED(3, "已使用"),
	INVALID(4, "已失效");

	private int value;
	
	private String des;
	
	private CouponState(int value, String des) {
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