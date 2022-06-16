package com.x.data.bo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentQuery extends BasePageQuery{
	
	@Positive
	private Integer targetId;
	
	@Min(1)
	@Max(2)
	private Integer type;
}
