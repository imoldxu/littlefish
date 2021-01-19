package com.x.lfs.service;

import java.util.List;

import com.x.lfs.context.bo.BatchAddDatePriceBo;
import com.x.lfs.context.bo.DatePriceQuery;
import com.x.lfs.entity.DatePrice;
import com.x.lfs.entity.PriceRange;

public interface DatePriceService {

	public List<DatePrice> updateDatePrice(BatchAddDatePriceBo batchAddDatePriceBo);

	public List<DatePrice> query(DatePriceQuery datePriceQuery);
	
	public PriceRange getPriceRange(List<String> skuIds);
}
