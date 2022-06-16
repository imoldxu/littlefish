package com.x.data.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MybatisPlusEnumSerializer;
import com.x.commons.fastjson.OssSerializer;
import com.x.data.pojo.enums.ArticleType;

import lombok.Data;

@Data
public class ArticleItemVo {

	private Integer id;
	
	private String title;
	
	@JSONField(serializeUsing=OssSerializer.class)
	private List<String> covers;
	
	@JSONField(serializeUsing=MybatisPlusEnumSerializer.class)
	private ArticleType type;
	
	@JSONField(format="yyyy-MM-dd")
	private Date createTime;
}
