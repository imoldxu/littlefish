package com.x.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.x.commons.util.WxUtil;
import com.x.constant.ErrorCode;
import com.x.dao.mongodb.UserDao;
import com.x.data.bo.WxUserInfoBo;
import com.x.data.dto.UserDto;
import com.x.data.po.User;
import com.x.data.vo.UserVo;
import com.x.exception.HandleException;
import com.x.service.UserService;
import com.x.service.WxMiniProgramService;

import ma.glasnost.orika.MapperFacade;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	WxMiniProgramService wxMiniService;
	@Autowired
	UserDao userDao;
	@Autowired
	MapperFacade orikaMapper;
	
	public UserDto loginByWxMiniprogram(String wxCode) {
		
		try {
			JSONObject wx_session = wxMiniService.jsCode2Session(wxCode);
			String sessionKey = wx_session.getString("session_key");
			String openid = wx_session.getString("openid");
			String unionid = wx_session.getString("unionid");
			User user = getUser(unionid, openid, null);
			UserDto userDto = orikaMapper.map(user, UserDto.class);
			userDto.setWxSessionKey(sessionKey);
			return userDto;
		} catch (Exception e) {
			throw new HandleException(ErrorCode.WX_NET_ERROR, e.getMessage());
		}
	}
	
	
//	public UserBo loginByWx(String wxCode) throws IOException{
//
//		JsonNode wxOauthInfo = WxUtil.getOauthInfo(wxCode);
//		String accessToken = null;
//		accessToken = (String)redissonUtil.get("wechat_access_token");
//		
//		JsonNode wxUserInfo = WxUtil.getUserInfo2(accessToken, wxOauthInfo);
//		// 获得微信的数据
//		String unionID = wxUserInfo.get("unionid").asText();
//		String headerImgURL = wxUserInfo.get("headimgurl").asText();
//		headerImgURL = WxUtil.convertAvatar(headerImgURL);
//		String wxnick = wxUserInfo.get("nickname").asText();
//		String nick = WxUtil.converWxNick(wxnick);
//		// 获取微信账号对应的账号
//		UserBo user = getUserByWxUnionID(unionID, "", nick ,headerImgURL);
//		
//		if(user.getIdcardtype() == UserBo.TYPE_IDCARD){
//			String idcard = user.getIdcardnum();
//			if(idcard!=null && !idcard.isEmpty()){
//				try{
//					int age = IdCardUtil.getAge(idcard);
//					user.setAge(age);
//				}catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		JsonNode subscribeNode = wxUserInfo.get("subscribe");
//		if(subscribeNode!=null){
//			int subscribe = subscribeNode.intValue();
//			user.setSubscribe(subscribe);
//		}else{
//			user.setSubscribe(0);//视为没有关注
//		}
//		
//		return user;
//	}

	private User getUser(String wxUnionId, String wxMiniOpenId, String wxOfficalAccountOpenId) {
		User user = userDao.getOne(wxUnionId, wxMiniOpenId, wxOfficalAccountOpenId);
		Date now = new Date();
		if (user == null) {
			// 微信用户未注册
			user = new User();
			if(StringUtils.isNoneBlank(wxUnionId)) {
				user.setWxUnionId(wxUnionId);
			}
			if(StringUtils.isNoneBlank(wxMiniOpenId)) {
				user.setWxMiniOpenId(wxMiniOpenId);
			}
			if(StringUtils.isNoneBlank(wxOfficalAccountOpenId)) {
				user.setWxOfficalAccountOpenId(wxOfficalAccountOpenId);
			}
			user.setCreateTime(now);
			user.setLastLoginTime(now);
			userDao.insert(user);
		} else {
			user.setLastLoginTime(now);
			userDao.save(user);
		}
		return user;
	}

	public UserDto updateUserInfo(UserDto userDto, WxUserInfoBo wxUserInfoBo) {
		
		JSONObject userJson = wxMiniService.getUserInfo(wxUserInfoBo.getEncryptedData(), userDto.getWxSessionKey(), wxUserInfoBo.getIv());
		String nick = userJson.getString("nickName");
		nick = WxUtil.converWxNick(nick);
		String avatar = userJson.getString("avatarUrl");
		avatar = WxUtil.convertAvatar(avatar);
		String unionId = userJson.getString("unionId");
		Integer gender = userJson.getInteger("gender");
		String city = userJson.getString("city");
		String country = userJson.getString("country");
		String province = userJson.getString("province");
		
		userDto.setNickName(nick);
		userDto.setAvatarUrl(avatar);
		userDto.setCity(city);
		userDto.setCountry(country);
		userDto.setProvince(province);
		userDto.setGender(gender);
		userDto.setWxUnionId(unionId);
		
		User user = orikaMapper.map(userDto, User.class);
		userDao.save(user);
		return userDto;
	}
	
}
