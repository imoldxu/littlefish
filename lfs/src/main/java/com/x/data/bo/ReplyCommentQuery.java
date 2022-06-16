package com.x.data.bo;

import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyCommentQuery extends BasePageQuery{

	@Positive
	private Long commentId;
	
}
