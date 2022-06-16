package com.x.data.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductQuery extends BasePageQuery{

	private String id;
	
	private Integer type;
	
	private String title;
	
	private Integer state;
	
	private Integer days;
	
}
