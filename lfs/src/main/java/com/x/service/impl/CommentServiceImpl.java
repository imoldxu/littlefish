package com.x.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.dao.mongodb.CommentDao;
import com.x.dao.mongodb.ReplyCommentDao;
import com.x.data.bo.CommentQuery;
import com.x.data.bo.CommitCommentBo;
import com.x.data.bo.ReplyCommentBo;
import com.x.data.bo.ReplyCommentQuery;
import com.x.data.po.Comment;
import com.x.data.po.CommentReply;
import com.x.data.pojo.enums.CommentTargetType;
import com.x.data.vo.CommentVo;
import com.x.data.vo.ReplyCommentVo;
import com.x.data.vo.UserVo;
import com.x.service.CommentService;

import ma.glasnost.orika.MapperFacade;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	CommentDao commentDao;
	@Autowired
	ReplyCommentDao replyCommentDao;
	@Autowired
	MapperFacade orikaMapper;

	@Override
	public MongoPageResult<CommentVo> queryComment(CommentQuery query) {

		MongoPageResult<CommentVo> pageRet = commentDao.pageQuery(query, CommentVo.class);
		
		pageRet.getData().parallelStream().forEach(comment->{
			long replyNum = replyCommentDao.getReplyNum(comment.getId());
			comment.setReplyNum(replyNum);
		});
		
		return pageRet;
	}

	@Override
	@CacheEvict(cacheNames="comment_number", key="#commitCommentBo.type + '_' + #commitCommentBo.targetId")
	public CommentVo create(UserVo user, CommitCommentBo commitCommentBo) {

		Comment comment = new Comment();
		comment.setContent(commitCommentBo.getContent());
		Date now = new Date();
		comment.setCreateTime(now);
		comment.setFromAvatar(user.getHeadImgUrl());
		comment.setFromId(user.getId());
		comment.setFromName(user.getNick());
		comment.setLikeNum(0);
		comment.setTargetId(commitCommentBo.getTargetId());
		comment.setType(commitCommentBo.getType());
		comment.setUpdateTime(now);
		commentMapper.insert(comment);

		CommentVo ret = orikaMapper.map(comment, CommentVo.class);
		ret.setReplyNum(0);
		return ret;
	}

	@Override
	public IPage<ReplyCommentVo> queryReplyComment(ReplyCommentQuery query) {

		Page<ReplyCommentVo> page = new Page<ReplyCommentVo>(query.getCurrent(), query.getPageSize());

		IPage<ReplyCommentVo> pageRet = replyMapper.query(page, query.getCommentId());

		return pageRet;
	}

	@Override
	@CacheEvict(cacheNames="reply_comment_number", key="#replyCommentBo.commentId")
	public ReplyCommentVo reply(UserVo user, ReplyCommentBo replyCommentBo) {

		CommentReply entity = new CommentReply();
		entity.setCommentId(replyCommentBo.getCommentId());
		entity.setContent(replyCommentBo.getContent());
		Date now = new Date();
		if (replyCommentBo.getCommentReplyId() != null) {

			CommentReply lastComment = replyMapper.selectById(replyCommentBo.getCommentReplyId());

			entity.setToAvatar(lastComment.getFromAvatar());
			entity.setToId(lastComment.getFromId());
			entity.setToName(lastComment.getFromName());
		} else {
			Comment lastComment = commentMapper.selectById(replyCommentBo.getCommentId());
			entity.setToAvatar(lastComment.getFromAvatar());
			entity.setToId(lastComment.getFromId());
			entity.setToName(lastComment.getFromName());
		}
		entity.setCreateTime(now);
		entity.setFromAvatar(user.getHeadImgUrl());
		entity.setFromId(user.getId());
		entity.setFromName(user.getNick());
		entity.setUpdateTime(now);
		replyMapper.insert(entity);

		ReplyCommentVo vo = orikaMapper.map(entity, ReplyCommentVo.class);
		vo.setReplyNum(0);
		return vo;
	}

	@Override
	@Cacheable(cacheNames="comment_number", key="#type + '_' + #targetId")
	public Integer getCommentNumber(CommentTargetType type, Integer targetId) {
		
		QueryWrapper<Comment> query = new QueryWrapper<Comment>();
		query.eq("type", type.getValue()).eq("target_id", targetId);
		Integer count = commentMapper.selectCount(query);
		return count;
	}
	
	@Override
	@Cacheable(cacheNames="reply_comment_number", key="#commentId")
	public Integer getReplyNumber(Long commentId) {
		
		QueryWrapper<CommentReply> query = new QueryWrapper<CommentReply>();
		query.eq("comment_id", commentId);
		Integer count = replyMapper.selectCount(query);
		return count;
	}

}
