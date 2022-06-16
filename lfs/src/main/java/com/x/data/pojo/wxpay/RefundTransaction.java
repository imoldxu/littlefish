package com.x.data.pojo.wxpay;

import java.util.List;

import lombok.Data;

@Data
public class RefundTransaction {

	private String refund_id;
	
	private String out_refund_no;
	
	private String transaction_id; //原支付流水号
	
	private String out_trade_no; //原订单号
	
	private String channel;
	
	private String user_received_account;
	
	private String success_time;
	
	private String create_time;
	
	private String status;
	
	private String funds_account;
	
	private RefundAmount amount;
	
	private List<RefundPromotion> promotion_detail;
	
}
