package com.x.lfs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.x.lfs.config.constant.OrderState;
import com.x.lfs.context.bo.NewOrderBo;
import com.x.lfs.entity.Order;
import com.x.lfs.service.OrderService;

import ma.glasnost.orika.MapperFacade;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	MongoTemplate MongoTemplate;
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public Order newOrder(String uid, NewOrderBo newOrderBo) {
		Order order = orikaMapper.map(newOrderBo, Order.class);
		order.setUid(uid);
		order.setState(OrderState.NEW);
		
		
		return null;
	}

	@Override
	public List<Order> getMyOrder(String uid) {
		// TODO Auto-generated method stub
		return null;
	}

}
