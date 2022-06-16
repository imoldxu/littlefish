package com.x.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.dao.mybatis.StaffRoleMapper;
import com.x.data.po.Role;
import com.x.data.po.StaffRole;
import com.x.service.StaffRoleService;

@Service
public class StaffRoleServiceImpl extends ServiceImpl<BaseMapper<StaffRole>, StaffRole> implements StaffRoleService{

	@Autowired
	StaffRoleMapper staffRoleMapper;
	
	@Override
	public void clearStaffRole(Integer staffId) {
		staffRoleMapper.deleteAllRoleByStaff(staffId);
	}

	@Override
	@Transactional
	public void updateStaffRole(Integer id, Set<Role> roles) {
	
		clearStaffRole(id);
		
		addStaffRole(id, roles);
	}
	
	@Override
	public void addStaffRole(Integer id, Set<Role> roles) {
		roles.forEach(role->{
			StaffRole staffRole = new StaffRole();
			staffRole.setRoleId(role.getId());
			staffRole.setStaffId(id);
			staffRoleMapper.insert(staffRole);
		});		
	}
}
