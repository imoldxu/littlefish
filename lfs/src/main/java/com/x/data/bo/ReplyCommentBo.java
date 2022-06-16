package com.x.data.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ReplyCommentBo {

	//回复的主评论的Id
	@Positive
	private Long commentId;
	
	//被回复的那条评论Id
    private Long commentReplyId;
	
	@NotBlank
	@Length(max=512)
	private String content;
}
