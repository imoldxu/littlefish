package com.x.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import org.jeasy.rules.api.Facts;
//import org.jeasy.rules.api.Rules;
//import org.jeasy.rules.core.DefaultRulesEngine;
//import org.jeasy.rules.support.composite.UnitRuleGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.lock.annotation.Lock4j;
import com.x.commons.util.DateUtil;
import com.x.commons.util.SnowflakeIdUtil;
import com.x.constant.ErrorCode;
import com.x.constant.RedisConstant;
//import com.x.dao.mybatis.CouponTemplateMapper;
//import com.x.dao.mybatis.GoodsCouponMapper;
//import com.x.dao.mybatis.UserCouponMapper;
import com.x.data.bo.CouponQuery;
import com.x.data.bo.CouponTemplateQuery;
import com.x.data.dto.UserCouponDto;
import com.x.data.po.CouponTemplate;
import com.x.data.po.UserCoupon;
import com.x.data.pojo.easyrules.RuleDes;
import com.x.data.pojo.enums.CommonState;
import com.x.data.pojo.enums.CouponState;
import com.x.data.pojo.enums.CouponValidType;
import com.x.data.pojo.enums.MatchGoodsType;
import com.x.data.vo.UserCouponItemVo;
import com.x.data.vo.UserCouponVo;
import com.x.data.vo.UserCouponTemplateVo;
import com.x.data.vo.UserVo;
import com.x.exception.HandleException;
import com.x.rule.RuleFactory;
import com.x.rule.RuleKey;
import com.x.service.CouponService;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;

@Slf4j
@Service
public class CouponServiceImpl implements CouponService {

//	@Autowired
//	CouponTemplateMapper couponTemplateMapper;
//	@Autowired
//	UserCouponMapper userCouponMapper;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	@Autowired
	SnowflakeIdUtil snowflakeIdUtil;
	@Autowired
	GoodsCouponMapper goodsCouponMapper;
	@Autowired
	MapperFacade orikaMapper;

	@Lock4j(keys = { "#user.id", "#couponTemplateId" }, expire = 60000, acquireTimeout = 1000)
	@Transactional
	@Override
	public UserCouponVo claim(UserVo user, Integer couponTemplateId) {
		CouponTemplate template = couponTemplateMapper.selectById(couponTemplateId);
		// TODO 应该将库存和模板分开管理,根据用户标识来加锁，避免用户并发的来领取
		// 校验时间
		Date endTime = template.getClaimEndTime();
		Date startTime = template.getClaimStartTime();
		Date now = new Date();
		if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "不在领取优惠券的时段");
		}
		// 校验库存,-1为没有库存限制，则不减少优惠券数量
		if (template.getTotal() > 0) {
			int affectRow = couponTemplateMapper.reduceStock(couponTemplateId, 1);
			if (affectRow != 1) {
				throw new HandleException(ErrorCode.NORMAL_ERROR, "优惠券已领完");
			}
		}

		// 判断用户是否有领券的资格
		RuleDes userMatchRuleDes = template.getMatchUserRules();
		Boolean isMatch = false;
		if (userMatchRuleDes != null) {
			Facts facts = new Facts();
			facts.put("user", user);
			facts.put("result", Boolean.FALSE);

			Object userMatchRule = RuleFactory.build(userMatchRuleDes);
			Object resultTrueRule = RuleFactory.buildRule(RuleKey.RESULT_TRUE);
			UnitRuleGroup group = new UnitRuleGroup();
			group.addRule(userMatchRule);
			group.addRule(resultTrueRule);

			Rules rules = new Rules();
			rules.register(group);

			DefaultRulesEngine ruleEngine = new DefaultRulesEngine();
			ruleEngine.fire(rules, facts);
			isMatch = facts.get("result");
		} else {
			isMatch = true;
		}

		if (!isMatch) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "你不符合领取的条件");
		}

		// 检查是否符合领取的条件
		Integer claimLimitNum = template.getClaimLimitNum();
		if (claimLimitNum != null && claimLimitNum > 0) {
			int count = getUserClaimNum(user.getId(), couponTemplateId, null, null);
			if (count >= claimLimitNum) {
				throw new HandleException(ErrorCode.NORMAL_ERROR, "你已经领过该优惠券了");
			}
		}
		Integer dayLimit = template.getClaimDayLimitNum();
		if (dayLimit != null && dayLimit > 0) {
			Date dayBegin = DateUtil.getDayStartTime(now);
			Date dayEnd = DateUtil.getDayEndTime(now);
			int count = getUserClaimNum(user.getId(), couponTemplateId, dayBegin, dayEnd);
			if (count >= dayLimit) {
				throw new HandleException(ErrorCode.NORMAL_ERROR, "今天你已经领过该优惠券了");
			}
		}

		// 生成优惠券
		UserCoupon coupon = new UserCoupon();
		coupon.setCouponNo(snowflakeIdUtil.nextId("%d"));
		coupon.setCouponTemplateId(couponTemplateId);
		coupon.setCreateTime(now);
		// 设置优惠券的有效期
		if (template.getValidType() == CouponValidType.FIXDATE) {
			coupon.setExpireTime(template.getValidTime());
		} else if (template.getValidType() == CouponValidType.DURATION) {
			// FIXME:这里可能应该取当日的结束时间+持续时间
			coupon.setExpireTime(new Date(now.getTime() + template.getValidDuration()));
		} else if (template.getValidType() == CouponValidType.ONLY_TODAY) {
			coupon.setExpireTime(DateUtil.getDayEndTime(now));
		}
		coupon.setState(CouponState.UNUSED);
		coupon.setUid(user.getId());
		userCouponMapper.insert(coupon);

		// 设置过期处理
		long offset = coupon.getExpireTime().getTime() - now.getTime();
		redisTemplate.opsForValue().set("Coupon" + RedisConstant.DIVIDER + coupon.getId(), null, offset);

		UserCouponVo vo = orikaMapper.map(coupon, UserCouponVo.class);
		vo.setCover(template.getCover());
		vo.setDiscount(template.getDiscount());
		vo.setFaceValue(template.getFaceValue());
		vo.setIntroduction(template.getIntroduction());
		vo.setMinAmount(template.getMinAmount());
		vo.setName(template.getName());
		vo.setType(template.getType());
		return vo;
	}

	/**
	 * 获取用户领取数量
	 * 
	 * @param uid
	 * @param couponTemplateId
	 * @param beginTime        用于判断当天这种时间段
	 * @param endTime
	 * @return
	 */
	private int getUserClaimNum(Long uid, Integer couponTemplateId, Date beginTime, Date endTime) {
		QueryWrapper<UserCoupon> queryWrapper = new QueryWrapper<UserCoupon>();
		if (beginTime != null && endTime != null) {
			queryWrapper.eq("uid", uid).eq("coupon_template_id", couponTemplateId).between("create_time", beginTime,
					endTime);
		} else {
			queryWrapper.eq("uid", uid).eq("coupon_template_id", couponTemplateId);
		}
		int count = userCouponMapper.selectCount(queryWrapper);
		return count;
	}

	@Override
	public List<UserCouponTemplateVo> queryCouponTemplateVo(UserVo user, CouponTemplateQuery query) {

		List<CouponTemplate> coupons = queryTemplate(query);
		// 需要判断用户是否已经领取了
		final List<UserCouponTemplateVo> ret = new ArrayList<UserCouponTemplateVo>();
		final List<UserCouponTemplateVo> claimedCoupons = new ArrayList<UserCouponTemplateVo>();
		coupons.forEach(coupon -> {
			UserCouponTemplateVo templateVo = orikaMapper.map(coupon, UserCouponTemplateVo.class);

			// 获取用户优惠券中未使用的优惠券，若还有则认为用户已经领取过了
			// FIXME 此时未判断用户是否有资格再领取，待到领取时再判断
			QueryWrapper<UserCoupon> queryWrapper = new QueryWrapper<UserCoupon>();
			queryWrapper.eq("uid", user.getId()).eq("coupon_template_id", coupon.getId()).eq("state",
					CouponState.UNUSED.getValue());
			int count = userCouponMapper.selectCount(queryWrapper);

			if (count > 0) {
				templateVo.setIsClaimed(Boolean.TRUE);
				claimedCoupons.add(templateVo);
			} else {
				templateVo.setIsClaimed(Boolean.FALSE);
				ret.add(templateVo);
			}
		});
		ret.addAll(claimedCoupons);
		return ret;

	}

	private List<CouponTemplate> queryTemplate(CouponTemplateQuery query) {
		Date now = new Date();

		List<CouponTemplate> templates = new ArrayList<CouponTemplate>();
		// 查询指定的商品优惠券
		List<CouponTemplate> goodsCoupons = goodsCouponMapper.queryCouponTemplate(query.getGoodsId(), now);
		templates.addAll(goodsCoupons);
		// 查询分类优惠券
		List<CouponTemplate> categoryCoupons = getCategoryCouponTemplate(query.getGoodsCategory(), now);
		templates.addAll(categoryCoupons);
		// 查询通用优惠券
		List<CouponTemplate> commonCoupons = getCommonCouponTemplate(now);
		templates.addAll(commonCoupons);
		return templates;
	}

	@Override
	public List<UserCouponTemplateVo> queryCouponTemplateVo(CouponTemplateQuery query) {

		List<CouponTemplate> coupons = queryTemplate(query);

		return orikaMapper.mapAsList(coupons, UserCouponTemplateVo.class);

	}

	private List<CouponTemplate> getCategoryCouponTemplate(Integer cat, Date now) {
		QueryWrapper<CouponTemplate> queryWrapper = new QueryWrapper<CouponTemplate>();
		queryWrapper.eq("match_goods_type", MatchGoodsType.CATEGORY.getValue()).eq("match_goods_category", cat)
				.eq("state", CommonState.VALID.getValue()).le(true, "claim_start_time", now)
				.ge(true, "claim_end_time", now);
		List<CouponTemplate> categoryCoupons = couponTemplateMapper.selectList(queryWrapper);
		return categoryCoupons;
	}

	private List<CouponTemplate> getCommonCouponTemplate(Date now) {
		// 查询通用券
		QueryWrapper<CouponTemplate> query = new QueryWrapper<CouponTemplate>();
		query.eq("match_goods_type", MatchGoodsType.ALL.getValue()).eq("state", CommonState.VALID.getValue())
				.le(true, "claim_start_time", now).ge(true, "claim_end_time", now)
				.eq("state", CommonState.VALID.getValue());
		;
		List<CouponTemplate> commonCoupons = couponTemplateMapper.selectList(query);
		return commonCoupons;
	}

	@Override
	public UserCouponVo getUserCouponVo(Long uid, Long couponId) {
		UserCouponDto dto = userCouponMapper.getUserCouponDto(couponId);
		UserCouponVo vo = orikaMapper.map(dto, UserCouponVo.class);
		return vo;
	}

	@Override
	public IPage<UserCouponItemVo> queryUserCouponItemVo(CouponQuery couponQuery) {
		Page<UserCouponItemVo> page = new Page<UserCouponItemVo>(couponQuery.getCurrent(), couponQuery.getPageSize());
		IPage<UserCouponItemVo> pageData = userCouponMapper.queryUserCouponItemVo(page, couponQuery);
		return pageData;
	}

	@Override
	public List<UserCouponDto> queryUserCouponDto(Long uid) {

		List<UserCouponDto> coupons = userCouponMapper.getUserCouponList(uid);

		return coupons;
	}

	@Override
	public UserCouponDto getUserCouponDto(Long couponId) {
		UserCouponDto dto = userCouponMapper.getUserCouponDto(couponId);
		return dto;
	}

	@Override
	public void lock(Long couponId) {
		int affectRows = userCouponMapper.lock(couponId);
		if (affectRows != 1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "锁定失败");
		}
	}

	@Override
	public void unlock(Long couponId) {
		Date now = new Date();
		int affectRows = userCouponMapper.unlock(couponId, now);
		if (affectRows != 1) {
			log.info("优惠券" + couponId + "解锁失败，认为是已过期");
			// TODO: 应该将其设置为过期
		}
	}

	@Override
	public void writeOff(String couponNo) {
		int affectRows = userCouponMapper.writeOffByNo(couponNo);
		if (affectRows != 1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "核销失败");
		}
	}

	@Override
	public void writeOff(Long couponId) {
		int affectRows = userCouponMapper.writeOff(couponId);
		if (affectRows != 1) {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "核销失败");
		}
	}

	@Override
	public void expire(Long couponId) {
		int affectRows = userCouponMapper.expire(couponId);
		if (affectRows != 1) {
			log.info("优惠券" + couponId + "过期失败，可能被使用");
		}
	}

}
