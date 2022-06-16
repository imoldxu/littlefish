package com.x.service;

import java.util.List;

import com.x.data.bo.BatchSetPriceCalendarBo;
import com.x.data.bo.PriceCalendarQuery;
import com.x.data.po.PriceCalendar;
import com.x.data.po.PriceRange;

public interface DatePriceService {

	public List<PriceCalendar> updateDatePrice(BatchSetPriceCalendarBo batchAddDatePriceBo);

	public List<PriceCalendar> query(PriceCalendarQuery datePriceQuery);
	
	public PriceRange getPriceRange(List<String> skuIds);
}
