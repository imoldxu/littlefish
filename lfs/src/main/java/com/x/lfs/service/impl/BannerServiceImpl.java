package com.x.lfs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.x.lfs.data.bo.banner.AddBannerBo;
import com.x.lfs.data.po.Banner;
import com.x.lfs.service.BannerService;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Service
@Slf4j
public class BannerServiceImpl implements BannerService {

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public Banner addBanner(AddBannerBo addBannerBo) {
		Banner banner = orikaMapper.map(addBannerBo, Banner.class);
		banner = mongoTemplate.insert(banner);
		return banner;
	}

	@Override
	public List<Banner> queryBanner() {
//		Query query = new Query();
//		query.addCriteria(Criteria.where(key))
		List<Banner> banner = mongoTemplate.findAll(Banner.class);
		return banner;
	}
	
	
}
