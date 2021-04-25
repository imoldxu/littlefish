package com.x.lfs.data.pojo.wxpay;

import lombok.Data;

@Data
public class GoodsDetail {
	private String merchant_goods_id;
	private String wechatpay_goods_id;
	private String goods_name;
	private Integer quantity;
	private Integer unit_price;

}