package com.x.service;

import java.util.List;

import com.x.commons.mongo.pageHelper.PageResult;
import com.x.data.bo.AddBannerBo;
import com.x.data.bo.BannerQuery;
import com.x.data.po.Banner;
import com.x.data.vo.BannerVo;

public interface BannerService {

	public Banner addBanner(AddBannerBo addBannerBo);

	public PageResult<BannerVo> queryBanner(BannerQuery query);
}
