package com.x.service.impl;

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
import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.dao.mongodb.GroupTourDao;
import com.x.data.bo.AddGroupTourBo;
import com.x.data.bo.GroupTourQuery;
import com.x.data.bo.ModifyGroupTourBo;
import com.x.data.po.GroupTour;
import com.x.data.po.PriceRange;
import com.x.data.po.Sku;
import com.x.data.vo.GroupTourItemVo;
import com.x.service.DatePriceService;
import com.x.service.GroupTourService;

import ma.glasnost.orika.MapperFacade;

@Service
public class GroupTourServiceImpl implements GroupTourService{

//	@Autowired
//	MongoTemplate mongoTemplate;
//	@Autowired
//	MongoPageHelper mongoPageHelper;
	@Autowired
	GroupTourDao groupTourDao;
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
		
		groupTourDao.insert(gt);
		return gt;
	}
	
//	public GroupTour modify(ModifyGroupTourBo newGroupTour) {
//		Query query = new Query(Criteria.where("id").is(newGroupTour.getId()));
//		Update update = new Update();
//		update.set("title", newGroupTour.getTitle());
//		update.set("days", newGroupTour.getDays());
//		update.set("nights", newGroupTour.getNights());
//		update.set("imageUrls", newGroupTour.getImageUrls());
//		update.set("tags", newGroupTour.getTags());
//		
//		List<Sku> skus = newGroupTour.getSkus();
//		skus.forEach( p->{
//			if(null == p.getId()) {
//				ObjectId skuId = ObjectId.get();
//				p.setId(skuId.toHexString());
//			}
//		});
//		update.set("skus", skus);
//		
//		mongoTemplate.updateFirst(query, update, GroupTour.class);
//		GroupTour groupTour = orikaMapper.map(newGroupTour, GroupTour.class);
//		return groupTour;
//	}
	
//	public void addPackage(String tourId, Sku p) {
//		Query query = new Query(Criteria.where("id").is(tourId));
//		Update update = new Update();
//		ObjectId packageId = ObjectId.get();
//		p.setId(packageId.toHexString());
//		//在数组中加入子文档
//		update.push("packages", p);
//		mongoTemplate.updateFirst(query, update, GroupTour.class);
//	}
//	
//	public void deletePackage(String tourId, String packageId) {
//		Query query = new Query(Criteria.where("id").is(tourId));
//		query.addCriteria(Criteria.where("$packages.id").is(packageId));
//		Update update = new Update();
//		//删除数组中的子文档
//		
//		mongoTemplate.updateFirst(query, update, GroupTour.class);
//	}
	
//	public void modifyPakcage(String tourId, Sku p) {
//		Query query = new Query(Criteria.where("id").is(tourId));
//		query.addCriteria(Criteria.where("$packages.id").is(p.getId()));
//		Update update = new Update();
//		//更新数组中的子文档
//		mongoTemplate.updateFirst(query, update, GroupTour.class);
//	}
	
	public MongoPageResult<GroupTourItemVo> query(GroupTourQuery gtQuery){
		
		MongoPageResult<GroupTourItemVo> ret = groupTourDao.query(gtQuery);
				
		ret.getData().forEach(item->{
			List<String> skuIds = item.getSkus().stream().map(sku->sku.getId()).collect(Collectors.toList());
			PriceRange priceRange = dataPriceService.getPriceRange(skuIds);
			item.setMaxPrice(priceRange.getMaxPrice());
			item.setMinPrice(priceRange.getMinPrice());
		});
		
		return ret;
	}

	@Override
	public GroupTour getById(String id) {
		GroupTour ret = groupTourDao.getById(id);
		return ret;
	}
	
}
