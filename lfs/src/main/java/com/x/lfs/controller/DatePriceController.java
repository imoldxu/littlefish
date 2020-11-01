package com.x.lfs.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.lfs.context.bo.BatchAddDatePriceBo;
import com.x.lfs.context.bo.DatePriceQuery;
import com.x.lfs.entity.DatePrice;
import com.x.lfs.service.DatePriceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/dateprice")
@Api("日期价格")
public class DatePriceController {

	@Autowired
	DatePriceService datePriceService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询日期价格", notes = "查询日期价格")
	public List<DatePrice> query(
			@ApiParam(name = "datePriceQuery", value = "日期查询条件") @Valid DatePriceQuery datePriceQuery,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<DatePrice> list = datePriceService.query(datePriceQuery);
		return list;
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "批量修改日期价格", notes = "批量修改日期价格")
	public void batchAdd(
			@ApiParam(name = "batchAddDatePriceBo", value = "批量添加日期价格") @RequestBody @Valid BatchAddDatePriceBo batchAddDatePriceBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		datePriceService.updateDatePrice(batchAddDatePriceBo);
		return;
	}
}
