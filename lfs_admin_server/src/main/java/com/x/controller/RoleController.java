package com.x.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.x.data.bo.AddRoleBo;
import com.x.data.po.Role;
import com.x.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping("/role")
@Api("角色接口")
public class RoleController {

	@Autowired
	RoleService roleService;
	@Autowired
	MapperFacade orikaMapper;
	
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "查询所有角色", notes = "查询所有角色")
	public List<Role> listRole() {
		List<Role> roleList = roleService.listRole();
		return roleList;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "添加角色", notes = "添加角色")
	public Role addRole(@RequestBody AddRoleBo addRoleBo) {
		Role role = roleService.addRole(addRoleBo);
		return role;
	}
}
