package com.x.data.pojo.wxpay;

import lombok.Data;

/**
 * 微信通知的响应
 * @author 老徐
 *
 */
@Data
public class WxResponse {

	private String code;
	
	private String message;
}
