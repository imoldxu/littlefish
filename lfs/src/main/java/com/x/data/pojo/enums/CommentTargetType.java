package com.x.data.pojo.enums;

import com.x.commons.fastjson.IEnum;

public enum CommentTargetType implements IEnum<Integer>{
	ARTICLE(1), //文章
	GOODS(2); //商品
	
	private int value;
	
	private CommentTargetType(int value) {
		this.value = value;
	}

	@Override
	public Integer getValue() {
		return this.value;
	}
}