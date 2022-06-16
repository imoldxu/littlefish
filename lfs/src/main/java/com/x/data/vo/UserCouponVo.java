package com.x.data.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.data.pojo.enums.CouponType;

import lombok.Data;

/**
 * 用户的优惠券详情
 * @author 老徐
 *
 */
@Data
public class UserCouponVo {

	private Long id;

	private String couponNo;

	@JSONField(format = "yyyy-MM-dd hh:mm:ss")
	private Date expireTime;

	private String name;

	private String cover;

	private String introduction;

	@JSONField(serializeUsing = MyEnumSerializer.class)
	private CouponType type;

	private Integer minAmount;// 最低满足的金额

	private Integer faceValue;// 面值

	private Double discount;// 折扣

	private String reason;// 不能使用的原因
}
