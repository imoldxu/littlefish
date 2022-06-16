package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum OrderState implements IEnum<Integer>{
	NEW(1, "新建"),  //新建
	CONFIRM(2, "待支付"),  //用户已经确认了订单，提供了快递信息
	PAYED(4, "已支付"), //已支付，待发货
	DELIVERED(5,"已发货"), //已发货，发货之后不允许修改配送地址，对该订单直接退款
	REFUNDIND(6, "待退款"),
	INVALID(7, "已失效"), //
	COMPLETE(8, "已完成"), //
	REFUNDED(9, "已退款");
	
	private int value;
	
	private String des;
	
	private OrderState(int value, String des) {
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