package com.x.lfs.service;

import java.util.List;

import com.x.lfs.context.bo.BatchAddDatePriceBo;
import com.x.lfs.context.bo.DatePriceQuery;
import com.x.lfs.entity.DatePrice;

public interface DatePriceService {

	public void updateDatePrice(BatchAddDatePriceBo batchAddDatePriceBo);

	public List<DatePrice> query(DatePriceQuery datePriceQuery);
	
}
