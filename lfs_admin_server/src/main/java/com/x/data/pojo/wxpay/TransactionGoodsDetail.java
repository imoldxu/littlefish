package com.x.data.pojo.wxpay;

import lombok.Data;

@Data
public class TransactionGoodsDetail {
	private String goods_id;
	private Integer discount_amount;
	private String goods_remark;
	private Integer quantity;
	private Integer unit_price;

}