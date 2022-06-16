package com.x.data.po;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 产品下每个套餐的每日价格
 * @author 老徐
 *
 */
@Document(collection="priceCalendar")
@Data
public class PriceCalendar {

	@Id
	private String id;
	
	private String skuId; //套餐id

	private Integer adultPrice;
	
	private Integer childPrice;
	
	private Integer singleRoomPrice;
	
	private Date date;
	
}
