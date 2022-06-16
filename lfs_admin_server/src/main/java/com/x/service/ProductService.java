package com.x.service;

import java.util.List;

import javax.validation.Valid;

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
import com.x.data.vo.ProductItemVo;

public interface ProductService {

	public Product create(AddProductBo addProductBo);

	public PageResult<ProductItemVo> query(ProductQuery rawQuery);

	public Product getById(String id);

	public Product modify(ModifyProductBo modifyProductBo);

	public Product updateState(String id, UpperOrLowerState state);

	public Sku addSku(AddSkuBo addSkuBo);

	public Sku modifySku(ModifySkuBo modifySkuBo);
	
	public List<Sku> querySku(SkuQuery rawQuery);

	public List<PriceCalendar> queryPriceCalendar(PriceCalendarQuery query);

	public List<PriceCalendar> batchSetPriceCalendar(BatchSetPriceCalendarBo batchSetPriceCalendarBo);
	
}
