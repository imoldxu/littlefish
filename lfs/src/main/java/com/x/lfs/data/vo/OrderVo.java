package com.x.lfs.data.vo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;

import com.x.lfs.constant.OrderState;
import com.x.lfs.data.po.Sku;
import com.x.lfs.data.po.Tourist;

import lombok.Data;

@Data
public class OrderVo {

	private String id;

	private String goodsTitle;
	
	private Sku sku;
	
	private Date departDate;//出发日期
	
	private List<Tourist> adults;//成人
	
	private List<Tourist> children;//儿童
	
	private Integer rooms;//需求房间，默认是2人一间，儿童不占房间
	
	private Integer price;//订单总价，分，从系统中计算得出
	
	@Data
	class Contact {
		private String name;
		
		private String phone;
		@Email
		private String email;
		
	}
	
	private Contact contact ;//联系人

	private String notes;//备注
	
	private String uid;//用户id

	private OrderState State;//状态
}
