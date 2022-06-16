package com.x.data.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_staff_role")
public class StaffRole {

	public Integer staffId;

	public Integer roleId;
	
}
