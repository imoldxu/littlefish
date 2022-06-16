package com.x.dao.mongodb;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.x.constant.ErrorCode;
import com.x.data.po.User;
import com.x.exception.HandleException;

@Repository
public class UserDao {

	@Autowired
	MongoTemplate mongoTemplate;
	
	public User getOne(String wxUnionId, String wxMiniOpenId, String wxOfficalAccountOpenId) {
		Query query = new Query();
		if(StringUtils.isNoneBlank(wxUnionId)) {
			query.addCriteria(Criteria.where("wxUnionId").is(wxUnionId));
		} else if(StringUtils.isNoneBlank(wxMiniOpenId)) {
			query.addCriteria(Criteria.where("wxMiniOpenId").is(wxMiniOpenId));
		} else if(StringUtils.isNoneBlank(wxOfficalAccountOpenId)) {
			query.addCriteria(Criteria.where("wxOfficalAccountOpenId").is(wxOfficalAccountOpenId));
		} else {
			throw new HandleException(ErrorCode.DATA_ERROR, "不能唯一确定一个用户");
		}
		User user = mongoTemplate.findOne(query, User.class);
		return user;
	}
	
	public User insert(User user) {
		mongoTemplate.insert(user);
		return user;
	}
	
	public User save(User user) {
		mongoTemplate.save(user);
		return user;
	}
}
