package com.x.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.x.data.bo.BatchSetPriceCalendarBo;
import com.x.data.bo.PriceCalendarQuery;
import com.x.data.po.PriceCalendar;
import com.x.data.po.PriceRange;
import com.x.service.DatePriceService;

import ma.glasnost.orika.MapperFacade;

@Service
public class DatePriceServiceImpl implements DatePriceService {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MapperFacade orikaMapper;

	/**
	 * 产生DatePrice的自定义Id，用skuid-2021-10-1，目的是为了在批量修改日期的时候，可以覆盖对应的ID
	 * @param skuid
	 * @param date
	 * @return
	 */
	private String generateId(String skuid, Date date) {
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = formater.format(date);
		return skuid+"_"+dateStr;
	}
	
	public List<PriceCalendar> updateDatePrice(BatchSetPriceCalendarBo batchAddDatePriceBo) {
		List<PriceCalendar> ret = new ArrayList<PriceCalendar>();
		
		Date current = batchAddDatePriceBo.getStartDate();
		Date endDate = batchAddDatePriceBo.getEndDate();
		
		while(endDate.after(current)) {
			PriceCalendar datePrice = new PriceCalendar();
			datePrice.setSkuId(batchAddDatePriceBo.getSkuId());
			datePrice.setAdultPrice(batchAddDatePriceBo.getAdultPrice());
			datePrice.setChildPrice(batchAddDatePriceBo.getChildPrice());
			datePrice.setDate(current);//MongoDbUtil.getChinaMongoDate(current));
			datePrice.setId(generateId(datePrice.getSkuId(), current));
			mongoTemplate.save(datePrice);
			
			ret.add(datePrice);
			//取下一天的时间
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
			calendar.setTime(current);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
			current = calendar.getTime();
		}
		
		return ret;
	}

	public List<PriceCalendar> query(PriceCalendarQuery datePriceQuery){
		String skuId = datePriceQuery.getSkuId();
		Date startDate = datePriceQuery.getStartDate();
		Date endDate = datePriceQuery.getEndDate();
		Query query = new Query();
		query.addCriteria(Criteria.where("skuId").is(skuId));
		query.addCriteria(Criteria.where("date").gte(startDate).lte(endDate));
		List<PriceCalendar> list = mongoTemplate.find(query, PriceCalendar.class);
		return list;
	}
		
	public PriceRange getPriceRange(List<String> skuIds) {
//		// 聚合操作
//        List<AggregationOperation> operations = new ArrayList<>();
//        // 筛选条件
//        operations.add(Aggregation.match(Criteria.where("skuId").in(skuId).and("date").gte(new Date())));
//        // 分组字段
//        GroupOperation groupOperation = Aggregation.group("skuId");
//        // 聚合查询字段
//        groupOperation = groupOperation.min("price");
//        // 添加选项  (聚合查询字段和添加筛选是有区别的注意)
//        operations.add(groupOperation);
//        groupOperation = groupOperation.max("price");
//        // 添加选项  (聚合查询字段和添加筛选是有区别的注意)
//        operations.add(groupOperation);
//
//        // 最终聚合查询所有信息
//        Aggregation aggregation = Aggregation.newAggregation(operations);
//        // 查询结果
//        AggregationResults<HashMap> results = mongoTemplate.aggregate(aggregation, collectionName, HashMap.class);
//        //获取结果
//        List<HashMap> result = results.getMappedResults();
//        return result;
		Aggregation aggregation = Aggregation.newAggregation(
            Aggregation.match(Criteria.where("skuId").in(skuIds).and("date").gte(new Date())),
            //Aggregation.unwind("items"),
            //Aggregation.addFields().addFieldWithValue("xid", "1"),
            Aggregation.group().min("adultPrice").as
                    ("minPrice").max("adultPrice").as("maxPrice"));
		
		AggregationResults<PriceRange> outputTypeCount =
		        mongoTemplate.aggregate(aggregation, "datePrice", PriceRange.class);
		
		List<PriceRange> list = outputTypeCount.getMappedResults();
		if(!list.isEmpty()) {
			return list.get(0);
		}else {
			PriceRange ret = new PriceRange();
			ret.setMaxPrice(0);
			ret.setMinPrice(0);
			return ret; 
		}
	}
}
