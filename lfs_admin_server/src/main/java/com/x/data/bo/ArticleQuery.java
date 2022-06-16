package com.x.data.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArticleQuery extends BasePageQuery{

	private String title;
	
	private Integer type;
	
	private String startTime;
	
	private String endTime;
	
}
