package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum TradeType implements IEnum<Integer>{
	GOODS(1), //商品销售
	CONTRIBUTION(2); //捐赠
	
	private int value;
	
	private TradeType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}