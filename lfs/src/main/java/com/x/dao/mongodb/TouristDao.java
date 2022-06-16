package com.x.dao.mongodb;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.x.constant.ErrorCode;
import com.x.data.po.Tourist;
import com.x.exception.HandleException;

@Repository
public class TouristDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public List<Tourist> query(String uid) {
		
		Query query = new Query(Criteria.where("uid").is(uid));
		
		query.with(Sort.by(Sort.Order.desc("_id")));
		List<Tourist> ret = mongoTemplate.find(query, Tourist.class);
		return ret;
	}
	
	public Tourist insert(Tourist tourist) {
		tourist = mongoTemplate.insert(tourist);
		return tourist;
	}
	
	public Tourist updateById(Tourist tourist) {
		Query query = new Query(Criteria.where("id").is(tourist.getId()).and("uid").is(tourist.getUid()));
		Update update = new Update();
		if(tourist.getName()!=null) {
			update.addToSet("name", tourist.getName());
		}
//      TODO		
//		update.set("idNo", tourist.getIdNo());
//		update.set("idType", tourist.getIdType());
//		update.set("name", tourist.getName());
//		update.set("phone", tourist.getPhone());
		tourist = mongoTemplate.findAndModify(query, update, Tourist.class);
		
		return tourist;
	}
	
	public void del(String touristId, String uid) {
		Query query = new Query(Criteria.where("id").is(touristId).and("uid").is(uid));
		Tourist tourist = mongoTemplate.findAndRemove(query, Tourist.class);
		if(tourist==null) {
			throw new HandleException(ErrorCode.DOMAIN_ERROR, "删除数据不存在或无权操作");
		}
	}
}
