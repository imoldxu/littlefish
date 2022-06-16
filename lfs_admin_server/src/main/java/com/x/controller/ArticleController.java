package com.x.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.x.data.bo.ArticleQuery;
import com.x.data.bo.ModifyArticleBo;
import com.x.data.bo.PubArticleBo;
import com.x.data.po.Article;
import com.x.data.vo.ArticleItemVo;
import com.x.data.vo.ArticleVo;
import com.x.data.vo.PageResult;
import com.x.service.ArticleService;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	@Autowired
	MapperFacade orikaMapper;
	
	@GetMapping
	public PageResult<ArticleItemVo> query(@Valid ArticleQuery query,
			HttpServletRequest request, HttpServletResponse response) {
		
		PageResult<ArticleItemVo> pageData = articleService.queryArticleItemVo(query);
		
		return pageData;
	}
	
	@GetMapping(path="/{id}")
	public ArticleVo getDetail(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		
		Article article = articleService.getDetail(id);
		ArticleVo vo = orikaMapper.map(article, ArticleVo.class);
		
		return vo;
	}
	
	@PostMapping
	public ArticleVo publish(@Valid @RequestBody PubArticleBo pubArticleBo,
			HttpServletRequest request, HttpServletResponse response) {
		
		Article article = articleService.publish(pubArticleBo);
		ArticleVo vo = orikaMapper.map(article, ArticleVo.class);
		return vo;
	}
	
	@PutMapping
	public ArticleVo modify(@Valid @RequestBody ModifyArticleBo modifyArticleBo,
			HttpServletRequest request, HttpServletResponse response) {
		
		Article article = articleService.modify(modifyArticleBo);
		ArticleVo vo = orikaMapper.map(article, ArticleVo.class);
		return vo;
	}
	
	@DeleteMapping(path="/{id}")
	public void delete(@PathVariable("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		
		articleService.delete(id);
		
		return;
	}
}
