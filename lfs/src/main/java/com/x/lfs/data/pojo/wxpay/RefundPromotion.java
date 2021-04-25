package com.x.lfs.data.pojo.wxpay;

import java.util.List;

import lombok.Data;

@Data
public class RefundPromotion {

	private String promotion_id;
	
	private String scope;
	
	private String type;
	
	private int amount;
	
	private int refund_amount;
	
	private List<RefundGoodsDetail> goods_detail;
}
