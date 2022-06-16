package com.x.dao.mongodb;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.PageResult;
import com.x.data.bo.ProductQuery;
import com.x.data.po.Product;
import com.x.data.pojo.enums.UpperOrLowerState;
import com.x.data.vo.ProductItemVo;

@Repository
public class ProductDao{

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	public Product insert(Product product) {
		mongoTemplate.insert(product);
		return product;
	}
	
	public PageResult<ProductItemVo> query(ProductQuery rawQuery){
		
		PageResult<ProductItemVo> ret = null;
		
		int pageSize = rawQuery.getPageSize();
		int pageIndex = rawQuery.getCurrent();
		String lastId = rawQuery.getLastId();
		
		Query query = new Query();
		//按id倒叙排列
		if(StringUtils.isNoneBlank(rawQuery.getId())) {
			query.addCriteria(Criteria.where("_id").is(rawQuery.getId()));
		}else {
			if(StringUtils.isNoneBlank(rawQuery.getTitle())) {
				query.addCriteria(Criteria.where("title").regex(rawQuery.getTitle()));
			}
			if(rawQuery.getType()!=null) {
				query.addCriteria(Criteria.where("tpye").is(rawQuery.getType()));
			}
			if(rawQuery.getState()!=null) {
				query.addCriteria(Criteria.where("state").is(UpperOrLowerState.valueOf(rawQuery.getState())));
			}
			if(rawQuery.getDays()!=null) {
				query.addCriteria(Criteria.where("days").is(rawQuery.getDays()));
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
			ret = mongoPageHelper.pageQuery(query, "product", ProductItemVo.class, Function.identity(), pageSize, pageIndex);
		}else {
			ret = mongoPageHelper.pageQuery(query, "product", ProductItemVo.class, Function.identity(), pageSize, pageIndex, lastId);
		}
		
		return ret;
	}

	public Product selectById(String id) {
		Product ret = mongoTemplate.findById(id, Product.class);
		return ret;
	}

	public long update(Product product) {
		Query query = new Query(Criteria.where("id").is(product.getId()));
		Update update = new Update();
		update.set("title", product.getTitle());
		update.set("departPlace", product.getDepartPlace());
		update.set("destination", product.getDestination());
		update.set("type", product.getType());
		update.set("days", product.getDays());
		update.set("nights", product.getNights());
		update.set("imageUrls", product.getImageUrls());
		update.set("tags", product.getTags());
		update.set("introduction", product.getIntroduction());
		update.set("schedules", product.getSchedules());
		update.set("includeFee", product.getIncludeFee());
		update.set("excludeFee", product.getExcludeFee());
		update.set("policy", product.getPolicy());
		update.set("instructions", product.getInstructions());
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, Product.class);
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
		
		UpdateResult result = mongoTemplate.updateFirst(query, update, Product.class);
		return result.getModifiedCount();
	}
	
}
