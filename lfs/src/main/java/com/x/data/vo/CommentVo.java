package com.x.data.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * 评论信息
 * @author 老徐
 *
 */
@Data
public class CommentVo {

	private String id;
	
	private String fromName;
	
	private String fromAvatar;
	
	private String content;
	
	private long replyNum;
	
	private Integer likeNum;
	
//	@JSONField(format="yyyy-MM-dd") 返回时间戳由前端来转换和计算时间间隔
	private Date createTime;
}
