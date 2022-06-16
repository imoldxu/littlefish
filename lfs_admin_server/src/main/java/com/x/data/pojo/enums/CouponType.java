package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum CouponType  implements IEnum<Integer>{
	
	FREE(1, "free"),
	EnoughReduce(2, "enoughReduce"),
	DISCOUNT(3, "discount");

	private int value;
	
	private String des;
	
	private CouponType(int value, String des) {
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