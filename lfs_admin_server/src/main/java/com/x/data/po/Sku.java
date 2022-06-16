package com.x.data.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.x.data.pojo.enums.UpperOrLowerState;

import lombok.Data;

/**
 * 套餐
 * @author 老徐
 *
 */
@Document("sku")
@Data
public class Sku {

	@Id
	private String id;//objectId
	
	private String productId;
	
	private String name;
	
	private String properties;
	
	private UpperOrLowerState state;
}
