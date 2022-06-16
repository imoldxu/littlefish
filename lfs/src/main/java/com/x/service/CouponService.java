package com.x.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.x.data.bo.CouponQuery;
import com.x.data.bo.CouponTemplateQuery;
import com.x.data.dto.UserCouponDto;
import com.x.data.vo.UserCouponItemVo;
import com.x.data.vo.UserCouponVo;
import com.x.data.vo.UserCouponTemplateVo;
import com.x.data.vo.UserVo;

/**
 * 优惠券服务
 * @author 老徐
 *
 */
public interface CouponService {

	/**
	 * 获取用户的优惠券详情
	 * @param uid
	 * @param couponId
	 * @return
	 */
	public UserCouponVo getUserCouponVo(Long uid, Long couponId);

	/**
	 * 获取用户的优惠券详情
	 * @param couponId
	 * @return
	 */
	public UserCouponDto getUserCouponDto(Long couponId);
	
	/**
	 * 查询用户的优惠券显示列表
	 * @param uid
	 * @param couponQuery
	 * @return
	 */
	public IPage<UserCouponItemVo> queryUserCouponItemVo(CouponQuery couponQuery);

	/**
	 * 获取用户所有的优惠券
	 * @param uid
	 * @return
	 */
	public List<UserCouponDto> queryUserCouponDto(Long uid);

	/**
	 * 面向商品来获取可用的优惠券模板
	 * @param user
	 * @param query
	 * @return
	 */
	public List<UserCouponTemplateVo> queryCouponTemplateVo(CouponTemplateQuery query);
	
	/**
	 * 面向用户来获取可用的优惠券模板
	 * @param user
	 * @param query
	 * @return
	 */
	public List<UserCouponTemplateVo> queryCouponTemplateVo(UserVo user, CouponTemplateQuery query);

	/**
	 * 领券
	 * @param couponNo
	 */
	public UserCouponVo claim(UserVo user, Integer couponTemplateId);
	
	/**
	 * 核销券
	 * @param couponNo
	 */
	public void writeOff(String couponNo);
	public void writeOff(Long couponId);
	
	/**
	 * 券过期
	 * @param couponId
	 */
	public void expire(Long couponId);
	
	/**
	 *  临时锁定用户的券
	 * @param couponId
	 */
	public void lock(Long couponId);
	
	/**
	 *  解锁用户的券
	 * @param couponId
	 */
	public void unlock(Long couponId);

}
