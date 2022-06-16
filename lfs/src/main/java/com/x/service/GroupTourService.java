package com.x.service;

import javax.validation.Valid;

import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.AddGroupTourBo;
import com.x.data.bo.GroupTourQuery;
import com.x.data.bo.ModifyGroupTourBo;
import com.x.data.po.GroupTour;
import com.x.data.vo.GroupTourItemVo;

public interface GroupTourService {

	public MongoPageResult<GroupTourItemVo> query(@Valid GroupTourQuery groupTourQuery);

//	public GroupTour add(@Valid AddGroupTourBo groupTourBo);

//	public GroupTour modify(@Valid ModifyGroupTourBo groupTour);

	public GroupTour getById(String id);
	
}
