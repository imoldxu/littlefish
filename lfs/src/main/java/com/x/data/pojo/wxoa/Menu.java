package com.x.data.pojo.wxoa;

import java.util.List;

import lombok.Data;

@Data
public class Menu {

	@Data
	public static class MenuItem{
		private String name;
		
		private String type;
		
		private String key;
		
		private String url;
		
		private String appid;
		
		private String pagepath;
		
		private String media_id;
		
		private String article_id;
		
		private List<MenuItem> sub_button;
	}
	
	private List<MenuItem> button;
	
}
