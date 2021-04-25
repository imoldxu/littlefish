package com.x.lfs.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.x.commons.util.WxUtil;
import com.x.lfs.constant.ErrorCode;
import com.x.lfs.data.bo.WxUserInfoBo;
import com.x.lfs.data.po.User;
import com.x.lfs.data.vo.UserVo;
import com.x.lfs.exception.HandleException;
import com.x.lfs.service.UserService;

import ma.glasnost.orika.MapperFacade;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	WxMiniProgramService wxMiniService;
	
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MapperFacade orikaMapper;
	
	public UserVo loginByWxMiniprogram(String wxCode) {
		
		try {
			JSONObject wx_session = wxMiniService.jsCode2Session(wxCode);
			String sessionKey = wx_session.getString("session_key");
			String openid = wx_session.getString("openid");
			String unionid = wx_session.getString("unionid");
			User user = null;
			if(StringUtils.isBlank(unionid)) {
				user = getUserByMiniOpenId(openid);
			}else {
				user = getUserByUnionId(unionid);
			}
			UserVo userVo = orikaMapper.map(user, UserVo.class);
			userVo.setWxSessionKey(sessionKey);
			return userVo;
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

	private User getUserByUnionId(String unionId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("wxUnionId").is(unionId));
		User user = mongoTemplate.findOne(query, User.class);
		Date now = new Date();
		if (user == null) {
			// 微信用户未注册
			user = new User();
			user.setWxUnionId(unionId);
			user.setCreatetime(now);
			user.setLastlogintime(now);
			mongoTemplate.insert(user);
		} else {
			user.setLastlogintime(now);
			mongoTemplate.save(user);
		}
		return user;
	}
	
	private User getUserByMiniOpenId(String openId) {
		Query query = new Query();
		query.addCriteria(Criteria.where("wxMiniOpenId").is(openId));
		User user = mongoTemplate.findOne(query, User.class);
		Date now = new Date();
		if (user == null) {
			// 微信用户未注册
			user = new User();
			user.setWxMiniOpenId(openId);
			user.setCreatetime(now);
			user.setLastlogintime(now);
			mongoTemplate.insert(user);
		} else {
			user.setLastlogintime(now);
			mongoTemplate.save(user);
		}
		return user;
	}


	public UserVo updateUserInfo(UserVo userVo, WxUserInfoBo wxUserInfoBo) {
		
		JSONObject userJson = wxMiniService.getUserInfo(wxUserInfoBo.getEncryptedData(), userVo.getWxSessionKey(), wxUserInfoBo.getIv());
		String nick = userJson.getString("nickName");
		nick = WxUtil.converWxNick(nick);
		String avatar = userJson.getString("avatarUrl");
		avatar = WxUtil.convertAvatar(avatar);
		String unionId = userJson.getString("unionId");
		Integer gender = userJson.getInteger("gender");
		String city = userJson.getString("city");
		String country = userJson.getString("country");
		String province = userJson.getString("province");
		
		userVo.setNickName(nick);
		userVo.setAvatarUrl(avatar);
		userVo.setCity(city);
		userVo.setCountry(country);
		userVo.setProvince(province);
		userVo.setGender(gender);
		userVo.setWxUnionId(unionId);
		
		User user = orikaMapper.map(userVo, User.class);
		mongoTemplate.save(user);
		return userVo;
	}

//	public void updateInfo(int uid, String name, String phone, int idcardtype, String idcardnum) {
//		
//		User user = userMapper.selectByPrimaryKey(uid);
//		if(user == null){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "系统异常,用户不存在");
//		}
//		if(idcardtype != User.TYPE_IDCARD && idcardtype != User.TYPE_JG){
//			throw new HandleException(ErrorCode.ARG_ERROR, "证件类型异常,请检查客户端");
//		}
//		if(idcardtype==User.TYPE_IDCARD){
//			if(!IdCardUtil.isIDCard(idcardnum)){
//				throw new HandleException(ErrorCode.NORMAL_ERROR, "身份证号有误,请检查");
//			}
//		}
//		if(!ValidDataUtil.isPhone(phone)){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "手机号有误,请检查"); 
//		}
//		
//		user.setName(name);
//		user.setPhone(phone);
//		user.setIdcardtype(idcardtype);
//		user.setIdcardnum(idcardnum);
//		
//		userMapper.updateByPrimaryKey(user);
//	}

//	public UserBo login(String phone, String password) {
//		Example ex = new Example(UserBo.class);
//		ex.createCriteria().andEqualTo("phone", phone);
//		UserBo user = userMapper.selectOneByExample(ex);
//		if(user == null){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户不存在");
//		}else{
//			if(user.getPassword().equals(password)){
//				return user;
//			}else{
//				throw new HandleException(ErrorCode.NORMAL_ERROR, "密码错误");
//			}
//		}
//	}
//
//	public void register(String phone, String password) {
//		if(!ValidDataUtil.isPhone(phone)){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "手机号有误,请检查"); 
//		}
//		
//		Example ex = new Example(UserBo.class);
//		ex.createCriteria().andEqualTo("phone", phone);
//		UserBo user = userMapper.selectOneByExample(ex);
//		if(user != null){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户已存在");
//		}else{
//			user = new UserBo();
//			user.setPhone(phone);
//			user.setPassword(password);
//			userMapper.insertUseGeneratedKeys(user);
//		}
//	}
//
//	public void getVerificationCode(String phone) {
//		Random r = new Random();
//		int number = r.nextInt(999999);
//		String code = String.format("%06d", number);
//		
//		redissonUtil.set("VERCODE_"+phone, code, 300000L);
//		System.out.println("vercode:"+code);
//		//TODO: 发送短信
//	}
//
//	public void verifyIDCard(int uid, String code, String name, String phone, int idcardtype, String idcardnum) {
////		String vercode = (String) redissonUtil.get("VERCODE_"+phone);
////		if(vercode==null){
////			throw new HandleException(ErrorCode.NORMAL_ERROR, "验证码已过期");
////		}
////		if(!vercode.equals(code)){
////			throw new HandleException(ErrorCode.NORMAL_ERROR, "验证码错误");
////		}
//		Example ex = new Example(UserBo.class);
//		ex.createCriteria().andEqualTo("idcardnum", idcardnum);
//		UserBo user = userMapper.selectOneByExample(ex);
//		if(user!=null) {
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "该身份证号已被实名，不能绑定第二个账号");
//		}		
//		user = userMapper.selectByPrimaryKey(uid);
//		if(user.getIdcardnum()!=null){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "用户已实名认证，不可重复认证");
//		}
//		if(!IdCardUtil.isIDCard(idcardnum)){
//			throw new HandleException(ErrorCode.NORMAL_ERROR, "身份证号格式有误");
//		}
//		
//		//TODO 调用实名认证接口
//		
//		//FIXME:验证成功
//		user.setIdcardnum(idcardnum);
//		user.setName(name);
//		user.setPhone(phone);
//		String birthday = IdCardUtil.getBirthday(idcardnum);
//		user.setBirthday(birthday);
//		//String sex = IdCardUtil.getSex(idcardnum);
//		//user.setSex(sex);
//		userMapper.updateByPrimaryKey(user);
//	}

	
}
