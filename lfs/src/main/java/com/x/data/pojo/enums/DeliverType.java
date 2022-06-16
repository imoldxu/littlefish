package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

/**
 * 提货方式
 * @author 老徐
 *
 */
public enum DeliverType implements IEnum<Integer> {

	EXPRESS(1), //快递
	
	SELF(2); //自提
	
	private int value;
	
	private DeliverType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}
