package com.x.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.ArticleQuery;
import com.x.data.po.Article;

@Repository
public class ArticleDao {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	public <T> MongoPageResult<T> pageQuery(ArticleQuery articleQuery, Class<T> T) {
		Query query = new Query();

		MongoPageResult<T> ret = mongoPageHelper.pageQuery(query, "article", T,
				articleQuery.getPageSize(), articleQuery.getCurrent(), articleQuery.getLastId());

		return ret;
	}

	public Article getById(String id) {
		Article entity = mongoTemplate.findById(id, Article.class);
		return entity;
	}
	
	
}
