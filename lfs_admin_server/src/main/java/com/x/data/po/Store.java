package com.x.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_store")
public class Store {

	@TableId(type=IdType.AUTO)
	private Integer id;
	
	private String name;
	
	private String address;
	
	private double longitude;
	
	private double latitude;
	
}
