package com.x.data.bo;

import java.util.List;

import javax.validation.constraints.NotBlank;
import com.x.data.po.Product.Schedule;

public class AddSkuBo {
	
	@NotBlank(message="sku名称不能为空")
	private String name;
	
	private String hotelStandard;//住宿标准
	
	private String carStandard;//车标准

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHotelStandard() {
		return hotelStandard;
	}

	public void setHotelStandard(String hotelStandard) {
		this.hotelStandard = hotelStandard;
	}

	public String getCarStandard() {
		return carStandard;
	}

	public void setCarStandard(String carStandard) {
		this.carStandard = carStandard;
	}

	public List<String> getIntroduceImageUrls() {
		return introduceImageUrls;
	}

	public void setIntroduceImageUrls(List<String> introduceImageUrls) {
		this.introduceImageUrls = introduceImageUrls;
	}

	public List<Schedule> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}

	public String getBigTrafficFeeDes() {
		return bigTrafficFeeDes;
	}

	public void setBigTrafficFeeDes(String bigTrafficFeeDes) {
		this.bigTrafficFeeDes = bigTrafficFeeDes;
	}

	public String getLocalTrafficFeeDes() {
		return localTrafficFeeDes;
	}

	public void setLocalTrafficFeeDes(String localTrafficFeeDes) {
		this.localTrafficFeeDes = localTrafficFeeDes;
	}

	public String getHotelFeeDes() {
		return hotelFeeDes;
	}

	public void setHotelFeeDes(String hotelFeeDes) {
		this.hotelFeeDes = hotelFeeDes;
	}

	public String getFoodFeeDes() {
		return foodFeeDes;
	}

	public void setFoodFeeDes(String foodFeeDes) {
		this.foodFeeDes = foodFeeDes;
	}

	public String getTicketFeeDes() {
		return ticketFeeDes;
	}

	public void setTicketFeeDes(String ticketFeeDes) {
		this.ticketFeeDes = ticketFeeDes;
	}

	public String getGuideFeeDes() {
		return guideFeeDes;
	}

	public void setGuideFeeDes(String guideFeeDes) {
		this.guideFeeDes = guideFeeDes;
	}

	public String getOtherFeeDes() {
		return otherFeeDes;
	}

	public void setOtherFeeDes(String otherFeeDes) {
		this.otherFeeDes = otherFeeDes;
	}

	public String getWithoutFeeDes() {
		return withoutFeeDes;
	}

	public void setWithoutFeeDes(String withoutFeeDes) {
		this.withoutFeeDes = withoutFeeDes;
	}

	public String getOtherWithoutFeeDes() {
		return otherWithoutFeeDes;
	}

	public void setOtherWithoutFeeDes(String otherWithoutFeeDes) {
		this.otherWithoutFeeDes = otherWithoutFeeDes;
	}

	public String getChildrenPolicy() {
		return childrenPolicy;
	}

	public void setChildrenPolicy(String childrenPolicy) {
		this.childrenPolicy = childrenPolicy;
	}

	public String getOwnfee() {
		return ownfee;
	}

	public void setOwnfee(String ownfee) {
		this.ownfee = ownfee;
	}

	public String getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(String serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public String getCancelPolicy() {
		return cancelPolicy;
	}

	public void setCancelPolicy(String cancelPolicy) {
		this.cancelPolicy = cancelPolicy;
	}
	
}
