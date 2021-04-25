package com.x.lfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.x.commons.util.IPUtils;
import com.x.commons.util.SessionUtil;
import com.x.lfs.data.bo.NewOrderBo;
import com.x.lfs.data.po.Order;
import com.x.lfs.data.vo.OrderVo;
import com.x.lfs.data.vo.UserVo;
import com.x.lfs.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/order")
@Api("订单接口")
public class OrderController {

	@Autowired
	OrderService orderService;
	@Autowired
	MapperFacade orikaMapper;
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "创建订单", notes = "添加我的订单")
	public OrderVo add(
			@ApiParam(name = "newOrderBo", value = "添加我的游客信息") @RequestBody @Valid NewOrderBo newOrderBo,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		Order order = orderService.newOrder(user.getId(), newOrderBo);
		OrderVo ret = orikaMapper.map(order, OrderVo.class);
		return ret;
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询订单", notes = "查询我的订单")
	public List<OrderVo> query(
			@ApiParam(name = "queryOrderBo", value = "添加我的游客信息") @Valid Object queryOrderBo,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		List<Order> list = orderService.getMyOrders(user.getId());
		
		List<OrderVo> ret = orikaMapper.mapAsList(list, OrderVo.class);
		
		return ret;
	}

	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "创建订单", notes = "添加我的订单")
	public OrderVo get(
			@ApiParam(name = "id", value = "添加我的游客信息") @PathVariable(name="id") @NotBlank String id,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		Order order = orderService.getMyOrder(user.getId(), id);
		OrderVo ret = orikaMapper.map(order, OrderVo.class);
		return ret;
	}
	
	@RequestMapping(value = "/Charge", method = RequestMethod.GET)
	@ApiOperation(value = "获取支付凭证", notes = "获取支付凭证")
	public Object getCharge(
			@ApiParam(name="orderid", value="订单编号") @RequestParam(name="orderid") String orderid,
			@ApiParam(name="payWay", value="支付渠道") @RequestParam(name="payWay") String payWay,
			HttpServletRequest request, HttpServletResponse response) {
	
			UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
			//String openid = user.getWxMiniOpenId();
			String ip = IPUtils.getRealIp(request);
			Object ret = orderService.getCharge(user, orderid, payWay, ip);
			return ret;
	}
}
