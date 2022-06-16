package com.x.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.x.data.bo.ArticleQuery;
import com.x.data.bo.ModifyArticleBo;
import com.x.data.bo.PubArticleBo;
import com.x.data.po.Article;
import com.x.data.vo.ArticleItemVo;
import com.x.data.vo.PageResult;

public interface ArticleService {

	public PageResult<ArticleItemVo> queryArticleItemVo(ArticleQuery query);
	
	public Article getDetail(String id);
	
	public Article publish(PubArticleBo pubArticleBo);

	public void delete(String id);

	public Article modify(ModifyArticleBo modifyArticleBo);
}
