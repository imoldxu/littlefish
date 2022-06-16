package com.x.data.po;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.x.data.pojo.enums.ArticleType;

import lombok.Data;

@Data
@Document(collection="article")
public class Article {

	@Id
	private Integer id;
	
	private String title;
	
	private ArticleType type;
	
	private List<String> covers; 
	
	private String content;
	
	private Date createTime;

	private Integer deleted;
}
