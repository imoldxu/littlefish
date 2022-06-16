package com.x.data.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.data.pojo.enums.CommentTargetType;

import lombok.Data;

@Data
public class CommitCommentBo {

	@JSONField(deserializeUsing=MyEnumSerializer.class)
	private CommentTargetType type;
	
	@Positive
	private Integer targetId;
	
	@NotBlank
	@Length(max=512)
	private String content;
	
}
