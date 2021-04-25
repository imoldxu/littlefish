package com.x.lfs.data.bo;

import javax.validation.constraints.Positive;

import lombok.Data;

/**
 * 通用的分页请求
 * @author 老徐
 *
 */
@Data
public class BasePageQuery {

	@Positive
	private Integer current;
	
	@Positive
	private Integer pageSize;
	
	private String lastId;
}
