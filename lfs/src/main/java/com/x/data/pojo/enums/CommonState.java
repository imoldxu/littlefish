package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

/**
 * 通用的有效无效状态枚举
 * @author 老徐
 *
 */
public enum CommonState implements IEnum<Integer> {
	INVALID(0),
	VALID(1);

	private int value;
	
	private CommonState(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}
