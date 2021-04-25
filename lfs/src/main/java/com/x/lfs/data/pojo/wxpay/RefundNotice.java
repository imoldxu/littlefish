package com.x.lfs.data.pojo.wxpay;

import lombok.Data;

@Data
public class RefundNotice {

	private String mchid;	//是	直连商户的商户号，由微信支付生成并下发。
	private String out_trade_no;  //是	返回的商户订单号
	private String transaction_id;  //是	微信支付订单号
	private String out_refund_no; //是	商户退款单号
	private String refund_id;	//是	微信退款单号
	private String refund_status;	//是	退款状态，枚举值：	SUCCESS：退款成功,	CLOSE：退款关闭,ABNORMAL：退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往【服务商平台—>交易中心】，手动处理此笔退款
	private	String success_time;	//否	1、退款成功时间，遵循rfc3339标准格式，格式为YYYY-MM-DDTHH:mm:ss+TIMEZONE，YYYY-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日13点29分35秒。
	private	String user_received_account;	//是	取当前退款单的退款入账方。
	private RefundAmount amount;
	
}
