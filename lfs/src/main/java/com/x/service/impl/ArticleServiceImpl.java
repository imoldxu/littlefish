package com.x.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.dao.mongodb.ArticleDao;
import com.x.data.bo.ArticleQuery;
import com.x.data.po.Article;
import com.x.data.vo.ArticleItemVo;
import com.x.service.ArticleService;
import com.x.service.CommentService;

import ma.glasnost.orika.MapperFacade;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleDao articleDao;
	@Autowired
	CommentService commentService;
	@Autowired
	MapperFacade orikaMapper;

	@Override
	public MongoPageResult<ArticleItemVo> queryArticleItemVo(ArticleQuery query) {
		MongoPageResult<ArticleItemVo> pageData = articleDao.pageQuery(query, ArticleItemVo.class);
		return pageData;
	}

	@Override
	@Cacheable(cacheNames="article", key="#id")
	public Article getDetail(String id) {
		Article article = articleDao.getById(id);
		return article;
	}

//	@Override
//	@Cacheable(cacheNames="article", key="#result.id")
//	public Article publish(PubArticleBo pubArticleBo) {
//		Article entity = orikaMapper.map(pubArticleBo, Article.class);
//		articleMapper.insert(entity);
//		return entity;
//	}

}
