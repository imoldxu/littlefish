package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ExpressCompany implements IEnum<Integer>{
	
	SF(1, "顺丰");

	private int value;
	
	private String des;
	
	private ExpressCompany(int value, String des) {
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