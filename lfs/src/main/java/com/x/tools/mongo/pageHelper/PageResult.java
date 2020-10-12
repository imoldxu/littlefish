package com.x.tools.mongo.pageHelper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * 分页结果.
 * @author Ryan
 */
@JsonInclude(Include.NON_NULL)
public class PageResult<T> {

	@ApiModelProperty("分页信息")
    private Pagination pagination;
    
    @ApiModelProperty("数据")
    private List<T> list;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
