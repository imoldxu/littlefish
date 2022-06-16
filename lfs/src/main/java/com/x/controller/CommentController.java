package com.x.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x.commons.util.SessionUtil;
import com.x.data.bo.CommentQuery;
import com.x.data.bo.CommitCommentBo;
import com.x.data.bo.ReplyCommentBo;
import com.x.data.bo.ReplyCommentQuery;
import com.x.data.vo.CommentVo;
import com.x.data.vo.PageResult;
import com.x.data.vo.ReplyCommentVo;
import com.x.data.vo.UserVo;
import com.x.service.CommentService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentService commentService;
	
	@GetMapping
	@ApiOperation(value = "查询评论", notes = "查询评论")
	public PageResult<CommentVo> queryComment(@Valid CommentQuery query,
			HttpServletRequest request, HttpServletResponse response) {
		
		<CommentVo> ret = commentService.queryComment(query);
		
		PageResult<CommentVo> pageRet = PageResult.buildPageResult(ret, CommentVo.class);
		
		return pageRet;
	}
	
	@PostMapping
	@ApiOperation(value = "发表评论", notes = "发表评论")
	public CommentVo commit(
			@RequestBody @Valid CommitCommentBo commitCommentBo,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_USER_KEY, UserVo.class);
		
		CommentVo comment = commentService.create(user, commitCommentBo);
		
		return comment;
	}
	
	@GetMapping(path="/reply")
	@ApiOperation(value = "查询回复", notes = "查询回复")
	public PageResult<ReplyCommentVo> queryReplyComment(@Valid ReplyCommentQuery query,
			HttpServletRequest request, HttpServletResponse response) {
		
		IPage<ReplyCommentVo> ret = commentService.queryReplyComment(query);
		
		PageResult<ReplyCommentVo> pageRet = PageResult.buildPageResult(ret, ReplyCommentVo.class);
		
		return pageRet;
	}
	
	@PostMapping(path="/reply")
	@ApiOperation(value = "回复评论", notes = "回复评论")
	public ReplyCommentVo reply(
			@RequestBody @Valid ReplyCommentBo replyCommentBo,
			HttpServletRequest request, HttpServletResponse response) {
		
		UserVo user = SessionUtil.get(request, SessionUtil.SESSION_USER_KEY, UserVo.class);
		
		ReplyCommentVo replyComment = commentService.reply(user, replyCommentBo);
		
		return replyComment;
	}
}
