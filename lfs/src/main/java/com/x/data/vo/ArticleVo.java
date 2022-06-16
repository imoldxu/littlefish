package com.x.data.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * 文章内容信息
 * @author 老徐
 *
 */
@Data
public class ArticleVo {

	private Integer id;
	
	private String title;
	
	private String content;
	
//	@JSONField(format="yyyy-MM-dd") 返回时间戳由前端来转换和计算时间间隔
	private Date createTime;
	
	private Integer commentNumber; //评论数量
}
