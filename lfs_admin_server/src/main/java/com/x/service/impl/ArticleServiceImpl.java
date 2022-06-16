package com.x.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.x.dao.mongodb.ArticleDao;
import com.x.data.bo.ArticleQuery;
import com.x.data.bo.ModifyArticleBo;
import com.x.data.bo.PubArticleBo;
import com.x.data.po.Article;
import com.x.data.vo.ArticleItemVo;
import com.x.data.vo.PageResult;
import com.x.service.ArticleService;

import ma.glasnost.orika.MapperFacade;

@Service
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	ArticleDao articleDao;
	@Autowired
	MapperFacade orikaMapper;
	
	@Override
	public PageResult<ArticleItemVo> queryArticleItemVo(ArticleQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Article getDetail(String id) {
		return articleDao.selectById(id);
	}

	@Override
	public Article publish(PubArticleBo pubArticleBo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		articleDao.logicDelete(id);
	}

	@Override
	public Article modify(ModifyArticleBo modifyArticleBo) {
		// TODO Auto-generated method stub
		return null;
	}

}
