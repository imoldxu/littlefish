package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

public enum IDType implements IEnum<Integer>{
	SFZ(1, "身份证"),
	JGZ(2, "军官证"),
	HZ(3, "护照");
	
	private int value;
	private String des;
	
	private IDType(int value, String des) {
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
