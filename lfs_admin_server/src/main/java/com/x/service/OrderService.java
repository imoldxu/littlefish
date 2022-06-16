package com.x.service;

import java.util.List;

import com.x.data.bo.NewOrderBo;
import com.x.data.po.Order;
import com.x.data.vo.UserVo;

public interface OrderService {

	public Order newOrder(String uid, NewOrderBo newOrderBo);
	
	public List<Order> getMyOrders(String uid);
	
	public Order getMyOrder(String uid, String orderId);

	public Object getCharge(UserVo user, String orderid, String payWay, String ip);
}
