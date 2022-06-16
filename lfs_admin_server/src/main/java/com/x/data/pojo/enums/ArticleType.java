package com.x.data.pojo.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum ArticleType implements IEnum<Integer>{
	YANSHENG(1,"养生讲堂"),
	ACTIVITY(2,"公益活动");
	
	private int value;
	private String des;
	
	private ArticleType(int value, String des) {
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