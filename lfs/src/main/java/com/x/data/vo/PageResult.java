package com.x.data.vo;

import lombok.Data;

import java.util.List;

import com.x.commons.mongo.pageHelper.MongoPageResult;

/**
 * 分页结果.
 * @author Ryan
 */
@Data
public class PageResult<T> {

    private Long total;
    
    private List<T> data;

    private boolean success;

	@SuppressWarnings("unchecked")
	public static <T> PageResult<T> buildPageResult(List<List<?>> sqlResult, Class<T> T) {
		List<T> list = ( List<T>) sqlResult.get(0);
		Long total = (Long)sqlResult.get(1).get(0);
		
		PageResult<T> result = new PageResult<T>();
		result.setData(list);
		result.setTotal(total);
		result.setSuccess(true);
		return result;
	}

//	public static <T> PageResult<T> buildPageResult(IPage<T> pageData,
//			Class<T> T) {
//		
//		PageResult<T> result = new PageResult<T>();
//		result.setData(pageData.getRecords());
//		result.setTotal(pageData.getTotal());
//		result.setSuccess(true);
//		return result;
//	}
	
	public static <T> PageResult<T> buildPageResult(MongoPageResult<T> pageData, Class<T> T) {

		PageResult<T> result = new PageResult<T>();
		result.setData(pageData.getData());
		result.setTotal(pageData.getTotal());
		result.setSuccess(true);
		return result;
	} 
}
