package com.x.data.pojo.wxpay;

import java.util.List;

import lombok.Data;

@Data
public class PromotionDetail {

	private String coupon_id;
	
	private String name;
	
	private String scope;
	
	private String type;
	
	private Integer amount;
	
	private String stock_id;
	
	private Integer wechatpay_contribute;
	
	private Integer merchant_contribute;
	
	private Integer other_contribute;
	
	private String currency;
	
	private List<TransactionGoodsDetail> goods_detail;
	
}
