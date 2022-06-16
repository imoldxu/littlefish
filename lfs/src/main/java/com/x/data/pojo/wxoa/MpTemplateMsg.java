package com.x.data.pojo.wxoa;

import lombok.Data;

@Data 
public class MpTemplateMsg{
	String appid;   //公众号appid，要求与小程序有绑定且同主体
	String template_id;  //公众号模板id
	String url;		//公众号模板消息所要跳转的url
	String miniprogram;	//公众号模板消息所要跳转的小程序，小程序的必须与公众号具有绑定关系
	String data;	//公众号模板消息的数
}