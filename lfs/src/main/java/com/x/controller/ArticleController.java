package com.x.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.ArticleQuery;
import com.x.data.bo.PubArticleBo;
import com.x.data.po.Article;
import com.x.data.pojo.enums.CommentTargetType;
import com.x.data.vo.ArticleItemVo;
import com.x.data.vo.ArticleVo;
import com.x.data.vo.PageResult;
import com.x.service.ArticleService;
import com.x.service.CommentService;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	@Autowired
	CommentService commentService;
	@Autowired
	MapperFacade orikaMapper;
	
	@GetMapping
	public PageResult<ArticleItemVo> query(@Valid ArticleQuery query,
			HttpServletRequest request, HttpServletResponse response) {
		
		MongoPageResult<ArticleItemVo> pageData = articleService.queryArticleItemVo(query);
		PageResult<ArticleItemVo> ret = PageResult.buildPageResult(pageData, ArticleItemVo.class);
		
		return ret;
	}
	
	@GetMapping(path="/{id}")
	public ArticleVo getDetail(@PathVariable("id") Integer id,
			HttpServletRequest request, HttpServletResponse response) {
		
		Article entity = articleService.getDetail(id);

		ArticleVo vo = orikaMapper.map(entity, ArticleVo.class);
		
		Integer commentNumber = commentService.getCommentNumber(CommentTargetType.ARTICLE, id);
		
		vo.setCommentNumber(commentNumber);
		return vo;
	}
	
//	@PostMapping
//	public ArticleVo publish(@Valid @RequestBody PubArticleBo pubArticleBo,
//			HttpServletRequest request, HttpServletResponse response) {
//		
//		Article entity = articleService.publish(pubArticleBo);
//
//		ArticleVo vo = orikaMapper.map(entity, ArticleVo.class);
//		return vo;
//	}
}
