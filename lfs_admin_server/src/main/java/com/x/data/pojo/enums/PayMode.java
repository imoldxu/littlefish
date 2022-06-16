package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum PayMode implements IEnum<Integer>{
	
	WXPAY(1, "微信支付"),
	ALIPAY(2, "支付宝");

	private int value;
	
	private String des;
	
	private PayMode(int value, String des) {
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