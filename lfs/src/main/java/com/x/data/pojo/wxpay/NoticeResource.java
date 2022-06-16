package com.x.data.pojo.wxpay;

import lombok.Data;

/**
 * 微信支付通知加密的数据
 * @author 老徐
 *
 */
@Data
public class NoticeResource {

	private String algorithm;
	
	private String ciphertext;
	
	private String associated_data;
	
	private String original_type;
	
	private String nonce;
	
}
