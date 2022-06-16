package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum InvoiceType implements IEnum<Integer>{
	NORMAL(1), //普通发票
	VAT(2); //增值税专用发票
	
	private int value;
	
	private InvoiceType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}