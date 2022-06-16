package com.x.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.GroupTourQuery;
import com.x.data.po.GroupTour;
import com.x.data.vo.GroupTourItemVo;

@Repository
public class GroupTourDao {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	public GroupTour getById(String id) {
		GroupTour entity = mongoTemplate.findById(id, GroupTour.class);
		return entity;
	}
	
	public GroupTour insert(GroupTour entity) {
		entity = mongoTemplate.insert(entity);
		return entity;
	}
	
	public MongoPageResult<GroupTourItemVo> query(GroupTourQuery gtQuery) {
				
		int pageSize = gtQuery.getPageSize();
		int pageIndex = gtQuery.getCurrent();
		String lastId = gtQuery.getLastId();
		
		Query query = new Query();
		//按id倒叙排列
		//包含哪些字段
		//query.fields().include("");
		query.with(Sort.by(Sort.Order.desc("_id")));
	    //使用非entity对象来接受，可能是起到project的效果
	    //mongoTemplate.find(query, GroupTourItemVo.class, "grouptour");
		MongoPageResult<GroupTourItemVo> ret = mongoPageHelper.pageQuery(query, "groupTour", GroupTourItemVo.class, pageSize, pageIndex, lastId);
		
		return ret;
	}
}
