package com.x.dao.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.BannerQuery;
import com.x.data.po.Banner;
import com.x.data.vo.BannerVo;

@Repository
public class BannerDao {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	public Banner insert(Banner banner) {
		banner = mongoTemplate.insert(banner);
		return banner;
	}
	
	public <T> MongoPageResult<T> pageQuery(BannerQuery query, Class<T> T) {
		Query mQuery = new Query();
		
		MongoPageResult<T> entity = mongoPageHelper.pageQuery(mQuery, "banner", T, query.getPageSize(), query.getCurrent(), null);
		
		return entity;
	}
}
