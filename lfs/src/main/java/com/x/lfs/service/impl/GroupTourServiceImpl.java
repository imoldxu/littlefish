package com.x.lfs.service.impl;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.PageResult;
import com.x.lfs.data.bo.AddGroupTourBo;
import com.x.lfs.data.bo.GroupTourQuery;
import com.x.lfs.data.bo.ModifyGroupTourBo;
import com.x.lfs.data.po.GroupTour;
import com.x.lfs.data.po.PriceRange;
import com.x.lfs.data.po.Sku;
import com.x.lfs.data.vo.GroupTourItemVo;
import com.x.lfs.service.DatePriceService;
import com.x.lfs.service.GroupTourService;

import ma.glasnost.orika.MapperFacade;

@Service
public class GroupTourServiceImpl implements GroupTourService{

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	@Autowired
	MapperFacade orikaMapper;
	@Autowired
	DatePriceService dataPriceService;
	
	public GroupTour add(AddGroupTourBo addGroupTourBo) {
		GroupTour gt = orikaMapper.map(addGroupTourBo, GroupTour.class);
		
		List<Sku> skus = gt.getSkus();
		skus.forEach( p->{
			ObjectId skuId = ObjectId.get();
			p.setId(skuId.toHexString());
		});
		
		mongoTemplate.insert(gt);
		return gt;
	}
	
	public GroupTour modify(ModifyGroupTourBo newGroupTour) {
		Query query = new Query(Criteria.where("id").is(newGroupTour.getId()));
		Update update = new Update();
		update.set("title", newGroupTour.getTitle());
		update.set("days", newGroupTour.getDays());
		update.set("nights", newGroupTour.getNights());
		update.set("imageUrls", newGroupTour.getImageUrls());
		update.set("tags", newGroupTour.getTags());
		
		List<Sku> skus = newGroupTour.getSkus();
		skus.forEach( p->{
			if(null == p.getId()) {
				ObjectId skuId = ObjectId.get();
				p.setId(skuId.toHexString());
			}
		});
		update.set("skus", skus);
		
		mongoTemplate.updateFirst(query, update, GroupTour.class);
		GroupTour groupTour = orikaMapper.map(newGroupTour, GroupTour.class);
		return groupTour;
	}
	
	public void addPackage(String tourId, Sku p) {
		Query query = new Query(Criteria.where("id").is(tourId));
		Update update = new Update();
		ObjectId packageId = ObjectId.get();
		p.setId(packageId.toHexString());
		//在数组中加入子文档
		update.push("packages", p);
		mongoTemplate.updateFirst(query, update, GroupTour.class);
	}
	
	public void deletePackage(String tourId, String packageId) {
		Query query = new Query(Criteria.where("id").is(tourId));
		query.addCriteria(Criteria.where("$packages.id").is(packageId));
		Update update = new Update();
		//删除数组中的子文档
		
		mongoTemplate.updateFirst(query, update, GroupTour.class);
	}
	
	public void modifyPakcage(String tourId, Sku p) {
		Query query = new Query(Criteria.where("id").is(tourId));
		query.addCriteria(Criteria.where("$packages.id").is(p.getId()));
		Update update = new Update();
		//更新数组中的子文档
		mongoTemplate.updateFirst(query, update, GroupTour.class);
	}
	
	public PageResult<GroupTourItemVo> query(GroupTourQuery gtQuery){
		
		PageResult<GroupTourItemVo> ret = null;
		
		int pageSize = gtQuery.getPageSize();
		int pageIndex = gtQuery.getCurrent();
		String lastId = gtQuery.getLastId();
		
		Query query = new Query();
		//按id倒叙排列
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
			ret = mongoPageHelper.pageQuery(query, "groupTour", GroupTourItemVo.class, Function.identity(), pageSize, pageIndex);
		}else {
			ret = mongoPageHelper.pageQuery(query, "groupTour", GroupTourItemVo.class, Function.identity(), pageSize, pageIndex, lastId);
		}
		
		ret.getList().forEach(item->{
			List<String> skuIds = item.getSkus().stream().map(sku->sku.getId()).collect(Collectors.toList());
			PriceRange priceRange = dataPriceService.getPriceRange(skuIds);
			item.setMaxPrice(priceRange.getMaxPrice());
			item.setMinPrice(priceRange.getMinPrice());
		});
		
		return ret;
	}

	@Override
	public GroupTour getById(String id) {
		GroupTour ret = mongoTemplate.findById(id, GroupTour.class);
		return ret;
	}
	
}
