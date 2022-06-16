package com.x.service;

import java.util.List;

import com.x.data.bo.AddRoleBo;
import com.x.data.po.Role;

public interface RoleService {

	public Role getRoleByKey(String key);

	public List<Role> listRole();

	public Role addRole(AddRoleBo addRoleBo);

}
