package com.x.lfs.data.pojo.wxpay;

import lombok.Data;

@Data
public class SceneInfo {

	private String payer_client_ip;
	
	private String device_id;   //商户端设备号
	
	private StoreInfo store_info;
}
