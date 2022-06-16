package com.x.data.po;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.x.data.pojo.enums.CommonState;

import lombok.Data;

@Data
@TableName("t_staff")
public class Staff {

	@TableId(type=IdType.AUTO)
	private Integer id;
	
	private String name;
	
	private String phone;
	
	private String password;

	private Date createTime;
		
	private CommonState state;

	@TableLogic
	private Integer deleted;
}
