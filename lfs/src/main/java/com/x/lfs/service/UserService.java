package com.x.lfs.service;

import com.x.lfs.context.bo.WxUserInfoBo;
import com.x.lfs.context.vo.UserVo;

public interface UserService {

	public UserVo loginByWxMiniprogram(String wxCode);	

	public UserVo updateUserInfo(UserVo userVo, WxUserInfoBo wxUserInfoBo);
	
}
