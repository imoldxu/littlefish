package com.x.data.vo;

import java.util.List;

import lombok.Data;

/**
 * 订单预览信息
 * @author 老徐
 *
 */
@Data
public class OrderPreviewVo {

	private List<UserCouponVo> coupons; //用户可用的优惠券
	
	private List<UserCouponVo> noUseCoupons; //用户不可用的优惠券
	
	private Integer totalPrice;//商品总价
	
	private Integer discount;//优惠金额
	
	private Integer postage;//邮费
	
	private Integer donation;//捐款
	
}
