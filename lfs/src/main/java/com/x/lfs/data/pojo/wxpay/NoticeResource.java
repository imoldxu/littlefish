package com.x.lfs.data.pojo.wxpay;

import lombok.Data;

@Data
public class NoticeResource {

	private String algorithm;
	
	private String ciphertext;
	
	private String associated_data;
	
	private String original_type;
	
	private String nonce;
	
}
