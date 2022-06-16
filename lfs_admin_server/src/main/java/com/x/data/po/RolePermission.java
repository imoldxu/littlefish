package com.x.data.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_role_permission")
public class RolePermission {
	
	public Integer roleId;
	
	public Integer permissionId;

}
