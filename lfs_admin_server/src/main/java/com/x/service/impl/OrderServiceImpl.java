package com.x.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.x.constant.ErrorCode;
import com.x.data.bo.NewOrderBo;
import com.x.data.po.Order;
import com.x.data.pojo.enums.OrderState;
import com.x.data.vo.UserVo;
import com.x.exception.HandleException;
import com.x.service.OrderService;

import ma.glasnost.orika.MapperFacade;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public Order newOrder(String uid, NewOrderBo newOrderBo) {
		Order order = orikaMapper.map(newOrderBo, Order.class);
		order.setUid(uid);
		order.setState(OrderState.NEW);
		
		order = mongoTemplate.save(order);
		return order;
	}

	@Override
	public List<Order> getMyOrders(String uid) {
		Query query = new Query(Criteria.where("uid").is(uid));
		List<Order> ret = mongoTemplate.find(query, Order.class);
		return ret;
	}
	
	public Order getMyOrder(String uid, String orderid) {
		Query query = new Query(Criteria.where("id").is(orderid).and("uid").is(uid));
		Order order = mongoTemplate.findOne(query, Order.class);
		return order;
	}

	public Object getCharge(UserVo user, String orderid, String payWay, String ip) {
		Order order = getMyOrder(user.getId(), orderid);
		if(null == order) {
			throw new HandleException(ErrorCode.ARG_ERROR, "order不存在");
		}
		if(payWay.equals("WX_XCX")){
//			order.setPayway(payWay);
//			WXPayCharge charge = MyWxPayUtil.getPayCharge(openid, "租盟-订金", "", order.getSn(), order.getAmount(), ip);
//			order.setPaysn(charge.getPrepay_id());
//			order.setState(Order.STATE_PAYING);
//			orderMapper.updateByPrimaryKey(order);
//			return charge;
			return null;
		}else{
			throw new HandleException(ErrorCode.ARG_ERROR, "不支持的支付方式");
		}
	}
	
}
