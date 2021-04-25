package com.x.lfs.service;

import javax.validation.Valid;

import com.x.commons.mongo.pageHelper.PageResult;
import com.x.lfs.data.bo.AddGroupTourBo;
import com.x.lfs.data.bo.GroupTourQuery;
import com.x.lfs.data.bo.ModifyGroupTourBo;
import com.x.lfs.data.po.GroupTour;
import com.x.lfs.data.vo.GroupTourItemVo;

public interface GroupTourService {

	public PageResult<GroupTourItemVo> query(@Valid GroupTourQuery groupTourQuery);

	public GroupTour add(@Valid AddGroupTourBo groupTourBo);

	public GroupTour modify(@Valid ModifyGroupTourBo groupTour);

	public GroupTour getById(String id);
	
}
