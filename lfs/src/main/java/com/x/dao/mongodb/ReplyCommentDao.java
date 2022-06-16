package com.x.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.ReplyCommentQuery;
import com.x.data.po.CommentReply;

public class ReplyCommentDao {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;

	public MongoPageResult<CommentReply> query(ReplyCommentQuery commentQuery) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("commentId").is(commentQuery.getCommentId()));
		MongoPageResult<CommentReply> result = mongoPageHelper.pageQuery(query, "comment", CommentReply.class,
					commentQuery.getPageSize(), commentQuery.getCurrent(), commentQuery.getLastId());	
		return result;
	}
	
	public CommentReply insert(CommentReply entity) {
		entity = mongoTemplate.insert(entity);
		return entity;
	}
	
	public long getReplyNum(String commentId) {
		Query query = new Query();
		query.addCriteria(
				Criteria.where("commentId").is(commentId));
		long count = mongoTemplate.count(query, CommentReply.class);
		return count;
	}
}
