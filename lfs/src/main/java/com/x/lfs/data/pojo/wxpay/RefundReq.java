package com.x.lfs.data.pojo.wxpay;

import java.util.List;

import lombok.Data;

@Data
public class RefundReq {

	private String transaction_id; //支付流水号
	
	private String out_trade_no;  //支付订单号
	
	private String out_refund_no; //退款订单号
	
	private String reason;
	
	private String notify_url;
	
	private String funds_account;
	
	private RefundAmount amount;
	
	private List<RefundGoodsDetail> goods_detail;
}
