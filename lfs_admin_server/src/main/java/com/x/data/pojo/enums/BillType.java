package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum BillType implements IEnum<Integer>{
	TYPE_PAY(1), //支付
	TYPE_REFUND(2); //退款
	
	private int value;
	
	private BillType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}