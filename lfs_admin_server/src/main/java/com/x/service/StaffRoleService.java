package com.x.service;

import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.x.data.po.Role;
import com.x.data.po.StaffRole;

public interface StaffRoleService extends IService<StaffRole>{

	public void clearStaffRole(Integer staffId);

	public void updateStaffRole(Integer id, Set<Role> roles);

	public void addStaffRole(Integer id, Set<Role> roles);
}
