package com.x.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.x.dao.mongodb.TouristDao;
import com.x.data.bo.AddTouristBo;
import com.x.data.bo.ModifyTouristBo;
import com.x.data.po.Tourist;
import com.x.service.TouristService;

import ma.glasnost.orika.MapperFacade;

@Service
public class TouristServiceImpl implements TouristService{

	@Autowired
	TouristDao touristDao;
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public List<Tourist> getMyTourists(String uid) {
		List<Tourist> ret = touristDao.query(uid);
		return ret;
	}

	@Override
	public Tourist addMyTourist(String uid, AddTouristBo addTouristBo) {
		Tourist tourist = orikaMapper.map(addTouristBo, Tourist.class);
		tourist.setUid(uid);
		tourist = touristDao.insert(tourist);		
		return tourist;
	}

	@Override
	public Tourist modifyMyTourist(String uid, ModifyTouristBo modifyTouristBo) {
		Tourist tourist = orikaMapper.map(modifyTouristBo, Tourist.class);

		tourist.setUid(uid);

		tourist = touristDao.updateById(tourist);
		
//		Query query = new Query(Criteria.where("id").is(modifyTouristBo.getId()).and("uid").is(uid));
//		
//		Update update = new Update();
//		update.set("idNo", modifyTouristBo.getIdNo());
//		update.set("idType", modifyTouristBo.getIdType());
//		update.set("name", modifyTouristBo.getName());
//		update.set("phone", modifyTouristBo.getPhone());
//		Tourist tourist = mongoTemplate.findAndModify(query, update, Tourist.class);
		
		return tourist;
	}

	@Override
	public void delMyTourist(String uid, String touristId) {
		touristDao.del(touristId, uid);
	}

	
}
