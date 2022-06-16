package com.x.data.pojo.wxpay;

import lombok.Data;

@Data
public class RefundGoodsDetail {

	private String merchant_goods_id;
	
	private String wechatpay_goods_id;
	
	private String goods_name;
	
	private int unit_price;
	
	private int refund_amount;
	
	private int refund_quantity;
}
