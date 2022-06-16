package com.x.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.dao.mongodb.BannerDao;
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
	BannerDao bannerDao;
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public Banner addBanner(AddBannerBo addBannerBo) {
		Banner banner = orikaMapper.map(addBannerBo, Banner.class);
		banner = bannerDao.insert(banner);
		return banner;
	}

	@Override
	public MongoPageResult<BannerVo> queryBanner(BannerQuery query) {
		MongoPageResult<BannerVo> banners = bannerDao.pageQuery(query, BannerVo.class);
		return banners;
	}
	
	
}
