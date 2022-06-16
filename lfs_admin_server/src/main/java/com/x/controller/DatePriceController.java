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

import com.x.data.bo.BatchSetPriceCalendarBo;
import com.x.data.bo.PriceCalendarQuery;
import com.x.data.po.PriceCalendar;
import com.x.data.vo.PriceCalendarVo;
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
	public List<PriceCalendarVo> query(
			@ApiParam(name = "datePriceQuery", value = "日期查询条件") @Valid PriceCalendarQuery datePriceQuery,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<PriceCalendar> list = datePriceService.query(datePriceQuery);
		List<PriceCalendarVo> ret = orikaMapper.mapAsList(list, PriceCalendarVo.class);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "批量修改日期价格", notes = "批量修改日期价格")
	public List<PriceCalendarVo> batchAdd(
			@ApiParam(name = "batchAddDatePriceBo", value = "批量添加日期价格") @RequestBody @Valid BatchSetPriceCalendarBo batchAddDatePriceBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<PriceCalendar> list = datePriceService.updateDatePrice(batchAddDatePriceBo);
		List<PriceCalendarVo> ret = orikaMapper.mapAsList(list, PriceCalendarVo.class);
		return ret;
	}
}
