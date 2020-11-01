package com.x.lfs.service;

import javax.validation.Valid;

import com.x.lfs.context.bo.AddGroupTourBo;
import com.x.lfs.context.bo.GroupTourQuery;
import com.x.lfs.context.vo.GroupTourItemVo;
import com.x.lfs.entity.GroupTour;
import com.x.tools.mongo.pageHelper.PageResult;

public interface GroupTourService {

	public PageResult<GroupTourItemVo> query(@Valid GroupTourQuery groupTourQuery);

	public GroupTour add(@Valid AddGroupTourBo groupTourBo);

	public GroupTour modify(@Valid GroupTour groupTour);

	public GroupTour getById(String id);
	
}
