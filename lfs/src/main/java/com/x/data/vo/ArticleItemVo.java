package com.x.data.vo;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.OssSerializer;

import lombok.Data;

/**
 * 文章列表项信息
 * @author 老徐
 *
 */
@Data
public class ArticleItemVo {

	private Integer id;
	
	private String title;
	
	@JSONField(serializeUsing=OssSerializer.class)
	private List<String> covers;
	
	//@JSONField(format="yyyy-MM-dd") 返回时间戳由前端来转换和计算时间间隔
	private Date createTime;
}
