package com.x.data.vo;

import lombok.Data;

/**
 * 用户收货地址信息
 * @author 老徐
 *
 */
@Data
public class AddressVo {

	private Long id;
	
	private String recipient;

	private String recipientPhone;
	
	private String province;
	
	private String city;
	
	private String district;
	
	private String address;
}
