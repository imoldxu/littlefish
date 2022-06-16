package com.x.service;

import com.x.commons.mongo.pageHelper.MongoPageResult;
import com.x.data.bo.ArticleQuery;
import com.x.data.po.Article;
import com.x.data.vo.ArticleItemVo;

public interface ArticleService {

	public MongoPageResult<ArticleItemVo> queryArticleItemVo(ArticleQuery query);
	
	public Article getDetail(String articleId);
	
}
