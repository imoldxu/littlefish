package com.x.commons.mongo.pageHelper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分页结果.
 * @author Ryan
 */
@Data
public class PageResult<T> {

	@ApiModelProperty("分页信息")
    private Long total;
    
    @ApiModelProperty("数据")
    private List<T> data;

    private boolean success = true;

}
