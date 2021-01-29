package com.x.lfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.context.bo.NewOrderBo;
import com.x.lfs.context.vo.UserVo;
import com.x.lfs.entity.Order;
import com.x.lfs.service.OrderService;
import com.x.tools.util.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/order")
@Api("订单接口")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "创建订单", notes = "添加我的订单")
	public Order add(
			@ApiParam(name = "newOrderBo", value = "添加我的游客信息") @RequestBody @Valid NewOrderBo newOrderBo,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		Order ret = orderService.newOrder(user.getId(), newOrderBo);
		
		return ret;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询订单", notes = "查询我的订单")
	public List<Order> query(
			@ApiParam(name = "queryOrderBo", value = "添加我的游客信息") @Valid Object queryOrderBo,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		List<Order> ret = orderService.getMyOrder(user.getId());
		
		return ret;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "创建订单", notes = "添加我的订单")
	public Order get(
			@ApiParam(name = "id", value = "添加我的游客信息") @PathVariable(name="id") @NotBlank String id,
			HttpServletRequest request, HttpServletResponse response) {

		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_KEY, UserVo.class);
		
		return null;
	}
	
}
