package com.x.dao.mongodb;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.data.bo.SkuQuery;
import com.x.data.po.Sku;

@Repository
public class SkuDao{

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	public Sku insert(Sku sku) {
		mongoTemplate.insert(sku);
		return sku;
	}
	
	public List<Sku> query(SkuQuery rawQuery){
		
		Query query = new Query();
		if(StringUtils.isNotBlank(rawQuery.getProductId())){
			query.addCriteria(Criteria.where("productId").is(rawQuery.getProductId()));
		}
		//按id倒叙排列
		//包含哪些字段
		//query.fields().include("");
		query.with(Sort.by(Sort.Order.asc("_id")));
	    
	    //使用非entity对象来接受，可能是起到project的效果
	    //mongoTemplate.find(query, GroupTourItemVo.class, "grouptour");
		
		List<Sku> ret = mongoTemplate.find(query, Sku.class);
		
		return ret;
	}

	public Sku selectById(String id) {
		Sku ret = mongoTemplate.findById(id, Sku.class);
		return ret;
	}

	public long update(Sku sku) {
		Query query = new Query(Criteria.where("id").is(sku.getId()));
		Update update = new Update();
		update.set("name", sku.getName());
		update.set("state", sku.getState());
		update.set("properties", sku.getProperties());
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, Sku.class);
		return result.getMatchedCount();
	}
	
}
