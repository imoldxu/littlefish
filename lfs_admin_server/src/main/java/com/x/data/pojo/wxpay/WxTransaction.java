package com.x.data.pojo.wxpay;

import lombok.Data;

/**
 * 支付通知解密数据
 * @author 老徐
 *
 */
@Data
public class WxTransaction {

	private String appid;
	
	private String mchid;
	
	private String out_trade_no;
	
	private String transaction_id;
	
	private String trade_type;
	
	private String trade_state;
	
	private String trade_state_desc;
	
	private String bank_type;
	
	private String attach;
	
	private String success_time;
	
	private Payer payer;
	
	private Amount amount;
	
	private SceneInfo scene_info;
	
	private PromotionDetail promotion_detail;
}
