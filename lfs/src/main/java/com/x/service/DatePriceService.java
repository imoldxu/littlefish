package com.x.service;

import java.util.List;

import com.x.data.bo.BatchAddDatePriceBo;
import com.x.data.bo.DatePriceQuery;
import com.x.data.po.DatePrice;
import com.x.data.po.PriceRange;

public interface DatePriceService {

	public List<DatePrice> updateDatePrice(BatchAddDatePriceBo batchAddDatePriceBo);

	public List<DatePrice> query(DatePriceQuery datePriceQuery);
	
	public PriceRange getPriceRange(List<String> skuIds);
}
