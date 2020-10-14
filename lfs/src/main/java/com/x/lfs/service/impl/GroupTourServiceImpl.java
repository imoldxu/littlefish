package com.x.lfs.service.impl;

import java.util.List;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.x.lfs.context.bo.AddGroupTourBo;
import com.x.lfs.context.bo.GroupTourQuery;
import com.x.lfs.context.vo.GroupTourItemVo;
import com.x.lfs.entity.GroupTour;
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
		mongoTemplate.insert(gt);
		return gt;
	}
	
	public GroupTour modify(GroupTour gt) {
		mongoTemplate.save(gt);
		return gt;
	}
	
	public PageResult<GroupTourItemVo> query(GroupTourQuery gtQuery){
		
		PageResult<GroupTourItemVo> ret = null;
		
		int pageSize = gtQuery.getPageSize();
		int pageIndex = gtQuery.getPageIndex();
		String lastId = gtQuery.getLastId();
		
		Query query = new Query();
		//
		Function<GroupTour, GroupTourItemVo> mapper = new Function<GroupTour, GroupTourItemVo>() {
	        @Override
	        public GroupTourItemVo apply(GroupTour in) {
	            return orikaMapper.map(in, GroupTourItemVo.class);
	        }
	    };
		
		if(StringUtils.isBlank(lastId)) {
			ret = mongoPageHelper.pageQuery(query, GroupTour.class, mapper, pageSize, pageIndex);
		}else {
			ret = mongoPageHelper.pageQuery(query, GroupTour.class, mapper, pageSize, pageIndex, lastId);
		}
		return ret;
	}
	
}
