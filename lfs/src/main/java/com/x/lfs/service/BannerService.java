package com.x.lfs.service;

import java.util.List;

import com.x.lfs.data.bo.banner.AddBannerBo;
import com.x.lfs.data.po.Banner;

public interface BannerService {

	public Banner addBanner(AddBannerBo addBannerBo);

	public List<Banner> queryBanner();
}
