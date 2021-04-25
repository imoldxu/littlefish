package com.x.lfs.data.bo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

import com.x.lfs.data.po.Sku;

import lombok.Data;

@Data
public class ModifyGroupTourBo {

	@NotBlank
	private String id;

	private String title;//title名称

	private int days;//几天
	
	private int nights;//几晚
	
	private List<String> imageUrls;
	
	private List<String> tags;//标签，重点
	
	//国家
	//省
	//景区
	//供应商产品名称
	//供应商产品编号
	
	//预定限制
	//提前预定限制
	//儿童可预订否？
	//儿童年龄？
	//儿童身高
	//预定人数限制？
	
	private String departPlace;//出发地
	
	private List<Sku> skus;
}
