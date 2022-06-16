package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ContributionType implements IEnum<Integer>{
	
	GOODS(1, "销售捐款"),
	FUND(2, "基金捐款"),
	TARGET(3, "定向捐款");

	private int value;
	
	private String des;
	
	private ContributionType(int value, String des) {
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
