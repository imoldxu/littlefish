package com.x.lfs.data.po;

import java.util.List;

import lombok.Data;

/**
 * 套餐
 * @author 老徐
 *
 */
@Data
public class Sku {

	private String id;//objectId
	
	private String name;
	
	//private String hotelStandard;//住宿标准
	
	//private String carStandard;//车标准

	private List<String> introduceImageUrls;//图文介绍
	
	private List<Schedule> scheduleList;//行程路线
	
	private String bigTrafficFeeDes;//大交通费说明
	
	private String localTrafficFeeDes;//本地交通费说明
	
	private String hotelFeeDes;//住宿费说明
	
	private String foodFeeDes;//餐食费说明
	
	private String ticketFeeDes;//景点门票说明
	
	private String guideFeeDes;//导游服务费说明
	
	private String otherFeeDes;//其他费用说明
	
	private String withoutFeeDes;//费用不含说明
	
	private String otherWithoutFeeDes;//其他费用不含说明
	
	private String childrenPolicy;//儿童政策
	
	private String ownfee;//自费说明
	
	private String serviceInfo;//服务说明
	
	private String cancelPolicy;//取消政策
	
}
