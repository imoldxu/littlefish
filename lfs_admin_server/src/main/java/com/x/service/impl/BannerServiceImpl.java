package com.x.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.x.commons.mongo.pageHelper.MongoPageHelper;
import com.x.commons.mongo.pageHelper.PageResult;
import com.x.data.bo.AddBannerBo;
import com.x.data.bo.BannerQuery;
import com.x.data.po.Banner;
import com.x.data.vo.BannerVo;
import com.x.service.BannerService;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Service
@Slf4j
public class BannerServiceImpl implements BannerService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MongoPageHelper mongoPageHelper;
	
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public Banner addBanner(AddBannerBo addBannerBo) {
		Banner banner = orikaMapper.map(addBannerBo, Banner.class);
		banner = mongoTemplate.insert(banner);
		return banner;
	}

	@Override
	public PageResult<BannerVo> queryBanner(BannerQuery query) {
		Query mQuery = new Query();
		PageResult<BannerVo> banners = mongoPageHelper.pageQuery(mQuery, "banner", BannerVo.class, query.getPageSize(), query.getCurrent());
		return banners;
	}
	
	
}
