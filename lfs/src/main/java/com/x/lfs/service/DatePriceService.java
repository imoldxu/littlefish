package com.x.lfs.service;

import java.util.List;

import com.x.lfs.data.bo.BatchAddDatePriceBo;
import com.x.lfs.data.bo.DatePriceQuery;
import com.x.lfs.data.po.DatePrice;
import com.x.lfs.data.po.PriceRange;

public interface DatePriceService {

	public List<DatePrice> updateDatePrice(BatchAddDatePriceBo batchAddDatePriceBo);

	public List<DatePrice> query(DatePriceQuery datePriceQuery);
	
	public PriceRange getPriceRange(List<String> skuIds);
}
