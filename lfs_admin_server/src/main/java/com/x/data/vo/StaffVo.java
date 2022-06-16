package com.x.data.vo;

import java.util.Date;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MybatisPlusEnumSerializer;
import com.x.data.po.Role;
import com.x.data.pojo.enums.CommonState;

import lombok.Data;

@Data
public class StaffVo {

	private Integer id;
	
	private String name;
	
	private String phone;
	
	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@JSONField(serializeUsing=MybatisPlusEnumSerializer.class)
	private CommonState state;
	
	private Set<Role> roles;

}
