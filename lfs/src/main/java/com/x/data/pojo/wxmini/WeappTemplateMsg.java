package com.x.data.pojo.wxmini;

import lombok.Data;

@Data 
public class WeappTemplateMsg{
	String template_id; //小程序模板ID
	String page; //小程序页面路径
	String form_id; //小程序模板消息formid, formid需要在提交form时携带传到后台才能发送模板消息
	String data; //小程序模板数据
	String emphasis_keyword; //小程序模板放大关键词
}