package com.x.lfs.data.pojo.wxpay;

import lombok.Data;

@Data
public class WxTransactionReq {

	private String appid;
	
	private String mchid;
	
	private String description;
	
	private String out_trade_no;
	
	private String time_expire;
	
	private String attach;
	
	private String notify_url;
	
	private String goods_tag;
	
	private Amount amount;
	
	private Payer payer;
	
	private Detail detail;
	
	private SceneInfo scene_info;
	
	private SettleInfo 	settle_info;
}
