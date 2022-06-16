package com.x.service;

import com.x.data.bo.WxUserInfoBo;
import com.x.data.dto.UserDto;

public interface UserService {

	public UserDto loginByWxMiniprogram(String wxCode);	

	public UserDto updateUserInfo(UserDto userDto, WxUserInfoBo wxUserInfoBo);
	
}
