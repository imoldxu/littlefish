package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum GoodsCategory implements IEnum<Integer>{
	
	BAOJIANPIN(1, "保健品"),
	YINPIN(2, "饮品");

	private int value;
	
	private String des;
	
	private GoodsCategory(int value, String des) {
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