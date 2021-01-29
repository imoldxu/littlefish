package com.x.lfs.entity;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.x.lfs.config.constant.OrderState;

@Document(collection="order")
public class Order {

	@Id
	private String id;

	private String goodsTitle;
	
	private Sku sku;
	
	private Date departDate;//出发日期
	
	private List<Tourist> adults;//成人
	
	private List<Tourist> children;//儿童
	
	private Integer rooms;//需求房间，默认是2人一间，儿童不占房间
	
	private Integer price;//订单总价，分，从系统中计算得出
	
	class Contact {
		private String name;
		
		private String phone;
		@Email
		private String email;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
	}
	
	private Contact contact ;//联系人

	private String notes;//备注
	
	private String uid;//用户id

	private OrderState State;//状态

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public List<Tourist> getAdults() {
		return adults;
	}

	public void setAdults(List<Tourist> adults) {
		this.adults = adults;
	}

	public List<Tourist> getChildren() {
		return children;
	}

	public void setChildren(List<Tourist> children) {
		this.children = children;
	}

	public Integer getRooms() {
		return rooms;
	}

	public void setRooms(Integer rooms) {
		this.rooms = rooms;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public OrderState getState() {
		return State;
	}

	public void setState(OrderState state) {
		State = state;
	}

	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

}
