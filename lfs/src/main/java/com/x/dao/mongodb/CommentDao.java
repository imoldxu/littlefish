package com.x.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.CommentQuery;

public class CommentDao {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;

	public <T> MongoPageResult<T> pageQuery(CommentQuery commentQuery, Class<T> T) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("targetId").is(commentQuery.getTargetId()).and("type").is(commentQuery.getType()));
		MongoPageResult<T> result = mongoPageHelper.pageQuery(query, "comment", T,
					commentQuery.getPageSize(), commentQuery.getCurrent(), commentQuery.getLastId());	
		return result;

	}
}
