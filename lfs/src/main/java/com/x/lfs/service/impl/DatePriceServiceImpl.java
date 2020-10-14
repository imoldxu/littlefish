package com.x.lfs.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.x.lfs.context.bo.BatchAddDatePriceBo;
import com.x.lfs.context.bo.DatePriceQuery;
import com.x.lfs.entity.DatePrice;
import com.x.lfs.service.DatePriceService;

import ma.glasnost.orika.MapperFacade;

@Service
public class DatePriceServiceImpl implements DatePriceService {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MapperFacade orikaMapper;

	public void updateDatePrice(BatchAddDatePriceBo batchAddDatePriceBo) {
		Date current = batchAddDatePriceBo.getStarteDate();
		Date endDate = batchAddDatePriceBo.getEndDate();
		while(endDate.before(current)) {
			DatePrice datePrice = new DatePrice();
			datePrice.setPackageId(batchAddDatePriceBo.getPackageId());
			datePrice.setAdultPrice(batchAddDatePriceBo.getAdultPrice());
			datePrice.setChildPrice(batchAddDatePriceBo.getChildPrice());
			datePrice.setDate(current);
			saveDatePrice(datePrice);
		}
	}

	public List<DatePrice> query(DatePriceQuery datePriceQuery){
		String packageId = datePriceQuery.getPackageId();
		Date startDate = datePriceQuery.getStartDate();
		Date endDate = datePriceQuery.getEndDate();
		Query query = new Query();
		query.addCriteria(Criteria.where("packageId").is(packageId));
		query.addCriteria(Criteria.where("date").gte(startDate).and("date").lte(endDate));
		List<DatePrice> list = mongoTemplate.find(query, DatePrice.class);
		return list;
	}
	
	private DatePrice saveDatePrice(DatePrice datePrice) {
				
		Date day = datePrice.getDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String id = datePrice.getPackageId()+"_"+sdf.format(day);
		datePrice.setId(id);
		
		mongoTemplate.save(datePrice);
		return datePrice;
	}
		
}
