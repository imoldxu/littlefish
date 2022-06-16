package com.x.data.pojo.wxpay;

import lombok.Data;

@Data
public class RefundAmount {

	private int refund;
	
	private int total;
	
	private String currency = "CNY";
	
	private Integer payer_total;
	
	private Integer payer_refund;
	
	private Integer settlement_refund;
	
	private Integer settlement_total;
	
	private Integer discount_refund;
	
}
