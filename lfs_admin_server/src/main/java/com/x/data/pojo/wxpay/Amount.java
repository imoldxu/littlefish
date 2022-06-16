package com.x.data.pojo.wxpay;

import lombok.Data;

@Data
public class Amount {

	private Integer total; //订单金额
	
	private String currency = "CNY";
	
	private Integer payer_total; //支付金额
	
	private String payer_currency; //支付币种
	
}
