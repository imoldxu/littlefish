package com.x.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.x.constant.ErrorCode;
import com.x.dao.mybatis.RoleMapper;
import com.x.data.bo.AddRoleBo;
import com.x.data.po.Role;
import com.x.exception.HandleException;
import com.x.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleMapper roleMapper;
	
	@Override
	public Role addRole(AddRoleBo addRoleBo) {
		Role role = roleMapper.getRoleByKey(addRoleBo.getKey());
		if(role != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "角色已存在");
		}else{
			role = new Role();
			role.setName(addRoleBo.getName());
			role.setKey(addRoleBo.getKey());
			roleMapper.insert(role);
		}
		return role;
	}

	@Override
	public List<Role> listRole() {
		return roleMapper.selectList(null);
	}

	@Override
	public Role getRoleByKey(String key) {
		return roleMapper.getRoleByKey(key);
	}

	
}
