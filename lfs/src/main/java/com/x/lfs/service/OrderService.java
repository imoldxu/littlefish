package com.x.lfs.service;

import java.util.List;

import com.x.lfs.context.bo.NewOrderBo;
import com.x.lfs.entity.Order;

public interface OrderService {

	public Order newOrder(String uid, NewOrderBo newOrderBo);
	
	public List<Order> getMyOrder(String uid);
}
