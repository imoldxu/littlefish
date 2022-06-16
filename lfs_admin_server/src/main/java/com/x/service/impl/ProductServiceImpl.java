package com.x.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.x.commons.mongo.pageHelper.PageResult;
import com.x.constant.ErrorCode;
import com.x.dao.mongodb.PriceCalendarDao;
import com.x.dao.mongodb.ProductDao;
import com.x.dao.mongodb.SkuDao;
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
import com.x.exception.HandleException;
import com.x.service.DatePriceService;
import com.x.service.ProductService;

import ma.glasnost.orika.MapperFacade;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductDao productDao;
	@Autowired
	SkuDao skuDao;
	@Autowired
	PriceCalendarDao priceCalendarDao;
	@Autowired
	MapperFacade orikaMapper;
	//@Autowired
	//DatePriceService datePriceService;
	
	public Product create(AddProductBo addProductBo) {
		Product product = orikaMapper.map(addProductBo, Product.class);
		product.setState(UpperOrLowerState.INVALID);//创建后默认是下架的
		productDao.insert(product);
		return product;
	}
	
//	public void addPackage(String tourId, Sku p) {
//		Query query = new Query(Criteria.where("id").is(tourId));
//		Update update = new Update();
//		ObjectId packageId = ObjectId.get();
//		p.setId(packageId.toHexString());
//		//在数组中加入子文档
//		update.push("packages", p);
//		mongoTemplate.updateFirst(query, update, GroupTour.class);
//	}
//	
//	public void deletePackage(String tourId, String packageId) {
//		Query query = new Query(Criteria.where("id").is(tourId));
//		query.addCriteria(Criteria.where("$packages.id").is(packageId));
//		Update update = new Update();
//		//删除数组中的子文档
//		
//		mongoTemplate.updateFirst(query, update, GroupTour.class);
//	}
//	
//	public void modifyPakcage(String tourId, Sku p) {
//		Query query = new Query(Criteria.where("id").is(tourId));
//		query.addCriteria(Criteria.where("$packages.id").is(p.getId()));
//		Update update = new Update();
//		//更新数组中的子文档
//		mongoTemplate.updateFirst(query, update, GroupTour.class);
//	}
//	
	@Override
	public PageResult<ProductItemVo> query(ProductQuery rawQuery){
		
		PageResult<ProductItemVo> ret = productDao.query(rawQuery);
		
		return ret;
	}

	@Override
	public Product getById(String id) {
		Product ret = productDao.selectById(id);
		return ret;
	}

	@Override
	public Product modify(ModifyProductBo modifyProductBo) {
		Product product = orikaMapper.map(modifyProductBo, Product.class);
		
		long effetRows = productDao.update(product);
		if(effetRows != 1) {
			throw new HandleException(ErrorCode.DATA_ERROR, "产品不存在");
		}
		
		return product;
	}

	@Override
	public Product updateState(String id, UpperOrLowerState state) {
		
		Product product = getById(id);
		
		long effetRows = productDao.updateProperty(id, "state", state);
		if(effetRows != 1) {
			throw new HandleException(ErrorCode.DATA_ERROR, "产品不存在");
		}
		product.setState(state);
		
		return product;
	}

	@Override
	public Sku addSku(AddSkuBo addSkuBo) {
		Sku sku = orikaMapper.map(addSkuBo, Sku.class);
		sku.setState(UpperOrLowerState.INVALID);
		skuDao.insert(sku);
		return sku;
	}

	@Override
	public Sku modifySku(ModifySkuBo modifySkuBo) {
		
		Sku sku = skuDao.selectById(modifySkuBo.getId());
		orikaMapper.map(modifySkuBo, sku);
		
		//Sku sku = orikaMapper.map(modifySkuBo, Sku.class);
		long effetRows = skuDao.update(sku);
		if(effetRows != 1) {
			throw new HandleException(ErrorCode.DATA_ERROR, "规格不存在");
		}
		return sku;
	}

	@Override
	public List<Sku> querySku(SkuQuery rawQuery) {
		return skuDao.query(rawQuery);
	}

	@Override
	public List<PriceCalendar> queryPriceCalendar(PriceCalendarQuery query) {
		return priceCalendarDao.query(query);
	}

	@Override
	public List<PriceCalendar> batchSetPriceCalendar(BatchSetPriceCalendarBo batchSetPriceCalendarBo) {
		
		Date current = batchSetPriceCalendarBo.getStartDate();
		Date endDate = batchSetPriceCalendarBo.getEndDate();
		
		List<PriceCalendar> prices = new ArrayList<PriceCalendar>();
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		while(endDate.after(current)) {
			calendar.setTime(current);			
			if(batchSetPriceCalendarBo.getDayOfWeeks()!=null && batchSetPriceCalendarBo.getDayOfWeeks().length>0) {
				int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
				List<Integer> dayOfWeekList = Arrays.asList(batchSetPriceCalendarBo.getDayOfWeeks());
				
				if(dayOfWeekList.contains(dayOfWeek)) {
					PriceCalendar dailyPrice = new PriceCalendar();
					dailyPrice.setSkuId(batchSetPriceCalendarBo.getSkuId());
					dailyPrice.setAdultPrice(batchSetPriceCalendarBo.getAdultPrice());
					dailyPrice.setChildPrice(batchSetPriceCalendarBo.getChildPrice());
					dailyPrice.setSingleRoomPrice(batchSetPriceCalendarBo.getSingleRoomPrice());
					dailyPrice.setDate(current);//MongoDbUtil.getChinaMongoDate(current));
					String id = dailyPrice.getSkuId() + current.getTime();
					dailyPrice.setId(id);
					
					prices.add(dailyPrice);
				}
			}else {
				PriceCalendar dailyPrice = new PriceCalendar();
				dailyPrice.setSkuId(batchSetPriceCalendarBo.getSkuId());
				dailyPrice.setAdultPrice(batchSetPriceCalendarBo.getAdultPrice());
				dailyPrice.setChildPrice(batchSetPriceCalendarBo.getChildPrice());
				dailyPrice.setSingleRoomPrice(batchSetPriceCalendarBo.getSingleRoomPrice());
				dailyPrice.setDate(current);//MongoDbUtil.getChinaMongoDate(current));
				String id = dailyPrice.getSkuId() + current.getTime();
				dailyPrice.setId(id);
				
				prices.add(dailyPrice);
			}
			
			//取下一天的时间
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+1);
			current = calendar.getTime();
		}
		priceCalendarDao.set(prices);
		
		return prices;
	}
	
	
}
