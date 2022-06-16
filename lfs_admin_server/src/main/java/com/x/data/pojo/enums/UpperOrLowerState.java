package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;
import com.x.constant.ErrorCode;
import com.x.exception.HandleException;

/**
 * 通用的有效无效状态枚举
 * @author 老徐
 *
 */
public enum UpperOrLowerState implements IEnum<Integer> {
	INVALID(0),
	VALID(1);

	private int value;
	
	private UpperOrLowerState(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
	
	public static UpperOrLowerState valueOf(Integer v) {
		if(v == 0) {
			return INVALID;
		} else if(v==1) {
			return VALID;
		}
		throw new HandleException(ErrorCode.DATA_ERROR, "状态不支持的值");
	}
}
