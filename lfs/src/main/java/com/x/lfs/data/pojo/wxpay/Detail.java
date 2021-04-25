package com.x.lfs.data.pojo.wxpay;

import java.util.List;

import lombok.Data;

@Data
public class Detail {
	private Integer cost_price;
	
	private String invoice_id;
	
	private List<GoodsDetail> goods_detail;
}
