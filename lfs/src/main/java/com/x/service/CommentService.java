package com.x.service;

import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.CommentQuery;
import com.x.data.bo.CommitCommentBo;
import com.x.data.bo.ReplyCommentBo;
import com.x.data.bo.ReplyCommentQuery;
import com.x.data.pojo.enums.CommentTargetType;
import com.x.data.vo.CommentVo;
import com.x.data.vo.ReplyCommentVo;
import com.x.data.vo.UserVo;

/**
 * 评论服务
 * @author 老徐
 *
 */
public interface CommentService{

	/**
	 * 查询评论
	 * @param query
	 * @return
	 */
	public MongoPageResult<CommentVo> queryComment(CommentQuery query);

	/**
	 * 发表评论
	 * @param user
	 * @param commitCommentBo
	 * @return
	 */
	public CommentVo create(UserVo user, CommitCommentBo commitCommentBo);

	/**
	 * 查询评论的回复
	 * @param query
	 * @return
	 */
	public MongoPageResult<ReplyCommentVo> queryReplyComment(ReplyCommentQuery query);

	/**
	 * 回复评论
	 * @param user
	 * @param replyCommentBo
	 * @return
	 */
	public ReplyCommentVo reply(UserVo user, ReplyCommentBo replyCommentBo);

	/**
	 * 获取评论目标id对应的评论数
	 * @param targetId
	 * @return
	 */
	public Integer getCommentNumber(CommentTargetType type, Integer targetId);

	/**
	 * 获取评论对应的回复数
	 * @param targetId
	 * @return
	 */
	public Integer getReplyNumber(Long commentId);

}
