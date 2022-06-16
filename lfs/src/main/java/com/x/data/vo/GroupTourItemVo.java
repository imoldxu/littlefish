package com.x.data.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

@Data
public class GroupTourItemVo {

	private String id;

	private String title;
	
	private List<String> imageUrls;
	
	private List<String> tags;
	
	private Integer minPrice;
	
	private Integer maxPrice;
	
	@Data
	public class SkuItemVo {
		private String id;		
	}

	@JSONField(serialize=false)
	private List<SkuItemVo> skus;
	
}
