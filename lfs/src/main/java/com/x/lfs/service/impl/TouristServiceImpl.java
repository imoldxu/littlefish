package com.x.lfs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.x.lfs.context.bo.AddTouristBo;
import com.x.lfs.context.bo.ModifyTouristBo;
import com.x.lfs.entity.Tourist;
import com.x.lfs.service.TouristService;

import ma.glasnost.orika.MapperFacade;

@Service
public class TouristServiceImpl implements TouristService{

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public List<Tourist> getMyTourists(String uid) {
		Query query = new Query(Criteria.where("uid").is(uid));
		query.with(Sort.by(Sort.Order.desc("_id")));
		List<Tourist> ret = mongoTemplate.find(query, Tourist.class);
		return ret;
	}

	@Override
	public Tourist addMyTourist(String uid, AddTouristBo addTouristBo) {
		Tourist tourist = orikaMapper.map(addTouristBo, Tourist.class);
		tourist.setUid(uid);
		tourist = mongoTemplate.insert(tourist);		
		return tourist;
	}

	@Override
	public Tourist modifyMyTourist(String uid, ModifyTouristBo modifyTouristBo) {
		Query query = new Query(Criteria.where("id").is(modifyTouristBo.getId()).and("uid").is(uid));
		//Tourist tourist = orikaMapper.map(modifyTouristBo, Tourist.class);
		//tourist.setUid(uid);
		Update update = new Update();
		update.set("idNo", modifyTouristBo.getIdNo());
		update.set("idType", modifyTouristBo.getIdType());
		update.set("name", modifyTouristBo.getName());
		update.set("phone", modifyTouristBo.getPhone());
		Tourist tourist = mongoTemplate.findAndModify(query, update, Tourist.class);
		
		return tourist;
	}

	@Override
	public Tourist delMyTourist(String uid, String touristId) {
		Query query = new Query(Criteria.where("id").is(touristId).and("uid").is(uid));
		Tourist tourist = mongoTemplate.findAndRemove(query, Tourist.class);
		return tourist;
	}

	
}
