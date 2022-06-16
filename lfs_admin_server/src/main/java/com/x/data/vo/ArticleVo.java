package com.x.data.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MybatisPlusEnumSerializer;
import com.x.commons.fastjson.OssSerializer;
import com.x.data.pojo.enums.ArticleType;

import lombok.Data;

@Data
public class ArticleVo {

	private Integer id;
	
	private String title;
	
	@JSONField(serializeUsing=OssSerializer.class)
	private List<String> covers;
	
	private String content;
	
	@JSONField(serializeUsing=MybatisPlusEnumSerializer.class)
	private ArticleType type;
	
}
