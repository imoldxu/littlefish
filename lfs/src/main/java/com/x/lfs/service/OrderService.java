package com.x.lfs.service;

import java.util.List;

import com.x.lfs.data.bo.NewOrderBo;
import com.x.lfs.data.po.Order;
import com.x.lfs.data.vo.UserVo;

public interface OrderService {

	public Order newOrder(String uid, NewOrderBo newOrderBo);
	
	public List<Order> getMyOrders(String uid);
	
	public Order getMyOrder(String uid, String orderId);

	public Object getCharge(UserVo user, String orderid, String payWay, String ip);
}
