package com.x.dao.mongodb;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.client.result.UpdateResult;
import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.PageResult;
import com.x.data.bo.BannerQuery;
import com.x.data.po.Banner;

public class BannerDao {

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	public Banner insert(Banner entity) {
		mongoTemplate.insert(entity);
		return entity;
	}
	
	public PageResult<Banner> query(BannerQuery rawQuery){
		
		PageResult<Banner> ret = null;
		
		int pageSize = rawQuery.getPageSize();
		int pageIndex = rawQuery.getCurrent();
		String lastId = rawQuery.getLastId();
		
		Query query = new Query();
		//按id倒叙排列
		if(StringUtils.isNoneBlank(rawQuery.getId())) {
			query.addCriteria(Criteria.where("_id").is(rawQuery.getId()));
		}else {
			if(StringUtils.isNoneBlank(rawQuery.getName())) {
				query.addCriteria(Criteria.where("name").regex(rawQuery.getName()));
			}
		}
		//包含哪些字段
		//query.fields().include("");
		query.with(Sort.by(Sort.Order.desc("_id")));
		//
//		Function<GroupTour, GroupTourItemVo> mapper = new Function<GroupTour, GroupTourItemVo>() {
//	        @Override
//	        public GroupTourItemVo apply(GroupTour in) {
//	            return orikaMapper.map(in, GroupTourItemVo.class);
//	        }
//	    };
	    
	    //使用非entity对象来接受，可能是起到project的效果
	    //mongoTemplate.find(query, GroupTourItemVo.class, "grouptour");
		
		if(StringUtils.isBlank(lastId)) {
			ret = mongoPageHelper.pageQuery(query, "banner", Banner.class, Function.identity(), pageSize, pageIndex);
		}else {
			ret = mongoPageHelper.pageQuery(query, "banner", Banner.class, Function.identity(), pageSize, pageIndex, lastId);
		}
		
		return ret;
	}

	public Banner selectById(String id) {
		Banner ret = mongoTemplate.findById(id, Banner.class);
		return ret;
	}

	public long update(Banner entity) {
		Query query = new Query(Criteria.where("id").is(entity.getId()));
		Update update = new Update();
//		update.set("title", product.getTitle());
//		update.set("departPlace", product.getDepartPlace());
//		update.set("destination", product.getDestination());
//		update.set("type", product.getType());
//		update.set("days", product.getDays());
//		update.set("nights", product.getNights());
//		update.set("imageUrls", product.getImageUrls());
//		update.set("tags", product.getTags());
//		update.set("introduction", product.getIntroduction());
//		update.set("schedules", product.getSchedules());
//		update.set("includeFee", product.getIncludeFee());
//		update.set("excludeFee", product.getExcludeFee());
//		update.set("policy", product.getPolicy());
//		update.set("instructions", product.getInstructions());
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, Banner.class);
		return result.getModifiedCount();
	}

	/**
	   *  更新指定的字段
	 * @param id
	 * @param key
	 * @param value
	 * @return
	 */
	public long updateProperty(String id, String key, Object value) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set(key, value);
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, Banner.class);
		return result.getModifiedCount();
	}
	
	public long logicDelete(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		update.set("deleted", true);
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, Banner.class);
		return result.getModifiedCount();
	}
}
