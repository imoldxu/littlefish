package com.x.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.x.constant.RedisConstant;
import com.x.service.CouponService;
import com.x.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * 利用redis的过期事件来实现对过期订单、过期消费券的处理
 * 
 * @author 老徐
 *
 */
@Slf4j
@Service
public class ExpiredEventSubscribe implements MessageListener {

	@Autowired
	OrderService orderService;
	@Autowired
	CouponService couponService;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		log.info(message.toString());

		String body = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
		String channel = (String) redisTemplate.getStringSerializer().deserialize(message.getChannel());
		if ("__keyevent@0__:expired".equals(channel)) {
			String[] keys = body.split(RedisConstant.DIVIDER);
			String prefix = keys[0];
			if (prefix.equals("order") && keys.length > 1) {
				String no = keys[1];
				orderService.expire(no);
			} else if (prefix.equals("coupon") && keys.length > 1) {
				String id = keys[1];
				couponService.expire(Long.valueOf(id));
			}
		}
	}

}
