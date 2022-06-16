package com.x.data.bo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MybatisPlusEnumSerializer;
import com.x.data.pojo.enums.ArticleType;

import lombok.Data;

@Data
public class ModifyArticleBo {

	@Positive
	private Integer id;
	
	@NotBlank
	@Length(max=100)
	private String title;

	private List<String> covers; 
	
	@NotBlank
	private String content;
	
	@JSONField(deserializeUsing=MybatisPlusEnumSerializer.class)
	private ArticleType type;
}
