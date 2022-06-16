package com.x.controller;

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

import com.x.commons.mongo.pageHelper.PageResult;
import com.x.data.bo.AddProductBo;
import com.x.data.bo.AddSkuBo;
import com.x.data.bo.BatchSetPriceCalendarBo;
import com.x.data.bo.ModifyProductBo;
import com.x.data.bo.ModifySkuBo;
import com.x.data.bo.PriceCalendarQuery;
import com.x.data.bo.ProductQuery;
import com.x.data.bo.SkuQuery;
import com.x.data.po.PriceCalendar;
import com.x.data.po.Product;
import com.x.data.po.Sku;
import com.x.data.pojo.enums.UpperOrLowerState;
import com.x.data.vo.PriceCalendarVo;
import com.x.data.vo.ProductItemVo;
import com.x.data.vo.ProductVo;
import com.x.data.vo.SkuVo;
import com.x.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/product")
@Api("产品管理接口")
public class ProductController {

	@Autowired
	ProductService productService;
	@Autowired
	MapperFacade orikaMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询产品列表", notes = "查询产品列表")
	public PageResult<ProductItemVo> query(
			@ApiParam(name = "query", value = "查询条件") @Valid ProductQuery query,
			HttpServletRequest request, HttpServletResponse response) {
	
		PageResult<ProductItemVo> result = productService.query(query);
		return result;
	}
	
	@RequestMapping(path="/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "查询产品详情", notes = "查询产品详情")
	public ProductVo getById(
			@ApiParam(name = "id", value = "产品id") @PathVariable(name="id") @NotBlank String id,
			HttpServletRequest request, HttpServletResponse response) {
		
		Product product = productService.getById(id);
		ProductVo ret = orikaMapper.map(product, ProductVo.class);
		return ret;
	}
	
	@RequestMapping(path="/{id}/state", method = RequestMethod.PATCH)
	@ApiOperation(value = "查询产品详情", notes = "查询产品详情")
	public ProductVo changeState(
			@ApiParam(name = "id", value = "产品id") @PathVariable(name="id") @NotBlank String id,
			@ApiParam(name = "state", value = "状态") @RequestParam(name="state") @NotBlank Integer state,
			HttpServletRequest request, HttpServletResponse response) {
		
		Product product = productService.updateState(id, UpperOrLowerState.valueOf(state));
		ProductVo ret = orikaMapper.map(product, ProductVo.class);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加产品", notes = "添加产品")
	public ProductVo add(
			@ApiParam(name = "addProductBo", value = "添加产品")  @RequestBody @Valid AddProductBo addProductBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		Product product = productService.create(addProductBo);
		ProductVo ret = orikaMapper.map(product, ProductVo.class);
		return ret;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@ApiOperation(value = "修改产品信息", notes = "修改产品信息")
	public ProductVo modify(
			@ApiParam(name = "modifyProductBo", value = "修改产品信息") @RequestBody @Valid ModifyProductBo modifyProductBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		Product product = productService.modify(modifyProductBo);
		ProductVo ret = orikaMapper.map(product, ProductVo.class);
		return ret;
	}
	
	@RequestMapping(path="/sku", method = RequestMethod.POST)
	@ApiOperation(value = "添加规格", notes = "添加规格")
	public SkuVo addSku(
			@ApiParam(name = "addSkuBo", value = "添加规格")  @RequestBody @Valid AddSkuBo addSkuBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		Sku sku = productService.addSku(addSkuBo);
		SkuVo skuVo = orikaMapper.map(sku, SkuVo.class);
		
		return skuVo;
	}
	
	@RequestMapping(path="/sku", method = RequestMethod.PUT)
	@ApiOperation(value = "修改规格", notes = "修改规格")
	public SkuVo modifySku(
			@ApiParam(name = "modifySkuBo", value = "修改规格")  @RequestBody @Valid ModifySkuBo modifySkuBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		Sku sku = productService.modifySku(modifySkuBo);
		SkuVo skuVo = orikaMapper.map(sku, SkuVo.class);
		
		return skuVo;
	}
	
	@RequestMapping(path="/sku", method = RequestMethod.GET)
	@ApiOperation(value = "查询规格", notes = "查询规格")
	public List<SkuVo> querySku(
			@ApiParam(name = "query", value = "查询条件") @Valid SkuQuery query,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<Sku> skus = productService.querySku(query);
		List<SkuVo> ret = orikaMapper.mapAsList(skus, SkuVo.class);
		return ret;
	}
	
	@RequestMapping(path="/sku/price-calendar", method = RequestMethod.GET)
	@ApiOperation(value = "查询价格日历", notes = "查询价格日历")
	public List<PriceCalendarVo> queryPriceCalendar(
			@ApiParam(name = "query", value = "查询条件") @Valid PriceCalendarQuery query,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<PriceCalendar> priceCalendars = productService.queryPriceCalendar(query);
		List<PriceCalendarVo> ret = orikaMapper.mapAsList(priceCalendars, PriceCalendarVo.class);
		return ret;
	}
	
	@RequestMapping(path="/sku/price-calendar", method = RequestMethod.POST)
	@ApiOperation(value = "设置价格日历", notes = "设置价格日历")
	public List<PriceCalendarVo> queryPriceCalendar(
			@ApiParam(name = "batchSetPriceCalendarBo", value = "设置内容") @RequestBody @Valid BatchSetPriceCalendarBo batchSetPriceCalendarBo,
			HttpServletRequest request, HttpServletResponse response) {
	
		List<PriceCalendar> priceCalendars = productService.batchSetPriceCalendar(batchSetPriceCalendarBo);
		List<PriceCalendarVo> ret = orikaMapper.mapAsList(priceCalendars, PriceCalendarVo.class);
		return ret;
	}
	
}
