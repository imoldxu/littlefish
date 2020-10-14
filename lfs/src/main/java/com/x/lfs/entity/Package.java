package com.x.lfs.entity;

import java.util.List;

public class Package {

	private String id;//tourid+name+hotelStandard+carStandard;
	
	private String name;
	
	private String hotelStandard;
	
	private String carStandard;
	
	private List<Schedule> scheduleList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public List<Schedule> getScheduleList() {
		return scheduleList;
	}

	public void setScheduleList(List<Schedule> scheduleList) {
		this.scheduleList = scheduleList;
	}
	
}
