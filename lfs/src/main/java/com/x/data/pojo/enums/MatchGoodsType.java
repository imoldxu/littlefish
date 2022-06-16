package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

public enum MatchGoodsType  implements IEnum<Integer>{
	
	ALL(1, "全部"),
	CATEGORY(2, "适配类别"),
	SEPCIAL(3, "指定商品");

	private int value;
	
	private String des;
	
	private MatchGoodsType(int value, String des) {
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
