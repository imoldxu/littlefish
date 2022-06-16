package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

public enum ProductType implements IEnum<Integer>{
	
	GROUP(1, "跟团游"),
	FREE(2, "自由行");

	private int value;
	
	private String des;
	
	private ProductType(int value, String des) {
		this.value = value;
		this.des = des;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
	
	public String getDes() {
		return this.des;
	}
	
}
