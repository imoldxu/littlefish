package com.x.dao.mongodb;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import com.mongodb.bulk.BulkWriteResult;
import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.data.bo.PriceCalendarQuery;
import com.x.data.po.PriceCalendar;
import com.x.data.po.PriceRange;

@Repository
public class PriceCalendarDao {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	public List<PriceCalendar> query(PriceCalendarQuery rawQuery){
		
		Query query = new Query();
		
		query.addCriteria(Criteria.where("skuId").is(rawQuery.getSkuId()));
		query.addCriteria(Criteria.where("date").gte(rawQuery.getStartDate()).lte(rawQuery.getEndDate()));
		//包含哪些字段
		//query.fields().include("");
		query.with(Sort.by(Sort.Order.asc("date")));		
		List<PriceCalendar> ret = mongoTemplate.find(query, PriceCalendar.class);
		
		return ret;
	}

	public PriceCalendar selectById(String id) {
		PriceCalendar ret = mongoTemplate.findById(id, PriceCalendar.class);
		return ret;
	}

	public long set(List<PriceCalendar> priceCalendars) {
		
		//方式一,forEach一个一个的插入，效率低
//		priceCalendars.forEach(priceCalendar->{
//			mongoTemplate.save(priceCalendar);
//		});
		//方式二,批量upsert		
		List<Pair<Query, Update>> updates = priceCalendars.parallelStream().map(dailyPrice -> {
			Query query = new Query(new
                    Criteria("_id").is(dailyPrice.getId()));
            Update update = new Update();
            update.set("_id", dailyPrice.getId());
            update.set("skuId", dailyPrice.getSkuId());
            update.set("date", dailyPrice.getDate());
            update.set("adultPrice", dailyPrice.getAdultPrice());
    		update.set("childPrice", dailyPrice.getChildPrice());
    		update.set("singleRoomPrice", dailyPrice.getSingleRoomPrice());
            Pair<Query, Update> updatePair = Pair.of(query, update);
            return updatePair;
        }).collect(Collectors.toList());
		BulkOperations bulkOps = mongoTemplate.bulkOps(BulkMode.UNORDERED, PriceCalendar.class);
		BulkWriteResult result = bulkOps.upsert(updates).execute();
		
		return result.getMatchedCount();
	}
	
	//获取指定SKU集合当天及之后时间的价格范围
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
