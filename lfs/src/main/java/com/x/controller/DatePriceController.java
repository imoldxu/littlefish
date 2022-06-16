package com.x.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.data.bo.BatchAddDatePriceBo;
import com.x.data.bo.DatePriceQuery;
import com.x.data.po.DatePrice;
import com.x.data.vo.DatePriceVo;
import com.x.service.DatePriceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/dateprice")
@Api("日期价格")
public class DatePriceController {

	@Autowired
	DatePriceService datePriceService;
	@Autowired
	MapperFacade orikaMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询日期价格", notes = "查询日期价格")
	public List<DatePriceVo> query(
			@ApiParam(name = "datePriceQuery", value = "日期查询条件") @Valid DatePriceQuery datePriceQuery,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<DatePrice> list = datePriceService.query(datePriceQuery);
		List<DatePriceVo> ret = orikaMapper.mapAsList(list, DatePriceVo.class);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "批量修改日期价格", notes = "批量修改日期价格")
	public List<DatePriceVo> batchAdd(
			@ApiParam(name = "batchAddDatePriceBo", value = "批量添加日期价格") @RequestBody @Valid BatchAddDatePriceBo batchAddDatePriceBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<DatePrice> list = datePriceService.updateDatePrice(batchAddDatePriceBo);
		List<DatePriceVo> ret = orikaMapper.mapAsList(list, DatePriceVo.class);
		return ret;
	}
}
