package com.x.data.po;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class Product {

	@Id
	private String id;

	static enum ProductType {
		GENTUANYOU, // 跟团游
		ZIYOUXING, // 自由行
		BAOCHE // 包车
	}

	private ProductType type; // 产品类型

	private List<String> tags;// 标签，重点

	private String name;// 产品名称

	private String departure;// 出发地，可以转id来标识

	private String provider;// 供应商，可以转id来标识

	private Integer days; // 旅行天数，几天

	private Integer nights;// 住宿天数，几晚

	@Data
	public static class Schedule {

		private Integer day;// 第几天

		private String route; // '地点|交通类型|地点' {vehicle:"",position:""}

		@Data
		public static class Todo {

			public static final int FREE = 1;// 自由活动
			public static final int MEAL = 2;// 餐饮
			public static final int HOTEL = 3; // 住宿
			public static final int TRAFFIC = 4; // 交通
			public static final int SCENIC_SPOT = 5; // 景点

			public enum TodoType{
				FREE, MEAL, HOTEL, TRAFFIC, SCENIC_SPOT
			}
			
			private int type; //活动类型

			private String time;//时间

			private String title;//活动名称

			private String desc;//内容

			private List<String> imageList;//附图

		}

		private List<Todo> todoList;

	}

	private Schedule schedule;// 行程

	public static class Expenses{
		
		static class ClauseItem{
			private int id;
			private JSONObject items;
		}
		
		static class IncludeExpenses{
			private String meal;
			
			private String ticket;//
			
			private String localTraffic;//本地交通
			
			private String bigTraffic;//大交通
			
			private String guide;//导游
			
			private String stay;//住宿
		}
		
		static class ExcludeExpenses{
			private String common;
			
			private String localTraffic;
			
			private String others;
		}
		
		static class ChildPolicy{
			private String common;
		}
		
		private Object include;//费用包含
		
		private Object exclude;//费用不含
		
		private Object childPolicy;//儿童政策
		
		private Object paySelf;//自费
	}
	
	private Expenses expenses;//费用
	
	@Data
	static class Standard {

		private String id;// 标准编号

		private String name;// 标准名称
	}

	private List<Standard> standards;// 规格

	static class Refund{
		private int lastDay; //剩余天数
		private int lastCost;//退款比例
		private String lastTime;//最晚时间
	}
	
	private List<Refund> refund;
}
