package com.x.lfs.service.impl;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.x.lfs.context.bo.AddGroupTourBo;
import com.x.lfs.context.bo.GroupTourQuery;
import com.x.lfs.context.vo.GroupTourItemVo;
import com.x.lfs.entity.GroupTour;
import com.x.lfs.entity.Package;
import com.x.lfs.service.GroupTourService;
import com.x.tools.mongo.pageHelper.MongoPageHelper;
import com.x.tools.mongo.pageHelper.PageResult;

import ma.glasnost.orika.MapperFacade;

@Service
public class GroupTourServiceImpl implements GroupTourService{

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	@Autowired
	MapperFacade orikaMapper;
	
	public GroupTour add(AddGroupTourBo addGroupTourBo) {
		GroupTour gt = orikaMapper.map(addGroupTourBo, GroupTour.class);
		
		List<Package> packageList = gt.getPackages();
		packageList.forEach( p->{
			ObjectId packageId = ObjectId.get();
			p.setId(packageId.toHexString());
		});
		
		mongoTemplate.insert(gt);
		return gt;
	}
	
	public GroupTour modify(GroupTour gt) {
		Query query = new Query(Criteria.where("id").is(gt.getId()));
		Update update = new Update();
		update.set("days", gt.getDays());
		update.set("nights", gt.getNights());
		List<String> imageUrls = gt.getImageUrl();
		update.set("imageUrl", imageUrls);
		update.set("introduceImageUrl", gt.getIntroduceImageUrl());
		update.set("points", gt.getPoints());
		update.set("title", gt.getTitle());
		mongoTemplate.updateFirst(query, update, GroupTour.class);
		
//		List<Package> packageList = gt.getPackages();
//		packageList.forEach(p ->{
//			modifyPackage(p);
//		});
		return gt;
	}
	
	public void addPackage(String tourId, Package p) {
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
	
	public void modifyPakcage(String tourId, Package p) {
		Query query = new Query(Criteria.where("id").is(tourId));
		query.addCriteria(Criteria.where("$packages.id").is(p.getId()));
		Update update = new Update();
		//更新数组中的子文档
		mongoTemplate.updateFirst(query, update, GroupTour.class);
	}
	
	public PageResult<GroupTourItemVo> query(GroupTourQuery gtQuery){
		
		PageResult<GroupTourItemVo> ret = null;
		
		int pageSize = gtQuery.getPageSize();
		int pageIndex = gtQuery.getPageIndex();
		String lastId = gtQuery.getLastId();
		
		Query query = new Query();
		//按id倒叙排列
		//包含哪些字段
		query.fields().include("");
		query.with(Sort.by(Sort.Order.desc("_id")));
		//
		Function<GroupTour, GroupTourItemVo> mapper = new Function<GroupTour, GroupTourItemVo>() {
	        @Override
	        public GroupTourItemVo apply(GroupTour in) {
	            return orikaMapper.map(in, GroupTourItemVo.class);
	        }
	    };
	    
	    //使用非entity对象来接受，可能是起到project的效果
	    //mongoTemplate.find(query, GroupTourItemVo.class, "grouptour");
		
		if(StringUtils.isBlank(lastId)) {
			ret = mongoPageHelper.pageQuery(query, GroupTour.class, mapper, pageSize, pageIndex);
		}else {
			ret = mongoPageHelper.pageQuery(query, GroupTour.class, mapper, pageSize, pageIndex, lastId);
		}
		return ret;
	}

	@Override
	public GroupTour getById(String id) {
		GroupTour ret = mongoTemplate.findById(id, GroupTour.class);
		return ret;
	}
	
}
