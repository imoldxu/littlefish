package com.x.lfs.context.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.tools.util.MoneyUtil;

public class GroupTourItemVo {

	private String id;

	private String title;
	
	private List<String> imageUrls;
	
	private List<String> tags;
	
//	@JSONField(serializeUsing=MoneyUtil.class)
	private Integer minPrice;
	
//	@JSONField(serializeUsing=MoneyUtil.class)
	private Integer maxPrice;
	
	public class SkuItemVo {
		private String id;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
		
	}

	@JSONField(serialize=false)
	private List<SkuItemVo> skus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Integer getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}

	public Integer getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<SkuItemVo> getSkus() {
		return skus;
	}

	public void setSkus(List<SkuItemVo> skus) {
		this.skus = skus;
	}
	
}
