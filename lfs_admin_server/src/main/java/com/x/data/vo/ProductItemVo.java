package com.x.data.vo;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.commons.fastjson.OssSerializer;
import com.x.data.pojo.enums.UpperOrLowerState;
import com.x.data.pojo.enums.ProductType;

import lombok.Data;

@Data
public class ProductItemVo {

	private String id;

	private String title;
	
	private List<String> departPlace;
	
	private List<String> destination;
	
	@JSONField(serializeUsing=MyEnumSerializer.class)
	private ProductType type;
	
	private Integer days;
	
	private Integer nights;
	
	@JSONField(serializeUsing=OssSerializer.class)
	private List<String> imageUrls;
	
	@JSONField(serializeUsing=MyEnumSerializer.class)
	private UpperOrLowerState state;
}
