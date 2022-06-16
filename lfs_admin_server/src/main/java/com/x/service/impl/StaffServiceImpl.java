package com.x.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.x.constant.ErrorCode;
import com.x.dao.mybatis.StaffMapper;
import com.x.data.bo.AddStaffBo;
import com.x.data.bo.ModifyPasswordBo;
import com.x.data.bo.ModifyStaffBo;
import com.x.data.bo.StaffQuery;
import com.x.data.dto.RoleDto;
import com.x.data.dto.StaffDto;
import com.x.data.po.Role;
import com.x.data.po.Staff;
import com.x.data.pojo.enums.CommonState;
import com.x.exception.HandleException;
import com.x.service.RoleService;
import com.x.service.StaffRoleService;
import com.x.service.StaffService;

import ma.glasnost.orika.MapperFacade;

//import tk.mybatis.mapper.entity.Example;

/**
 * 用户、角色管理
 * @author 老徐
 *
 */
@Service
public class StaffServiceImpl extends ServiceImpl<BaseMapper<Staff>, Staff> implements StaffService{
	
	@Autowired
	StaffRoleService staffRoleService;
	@Autowired
	RoleService roleService;
	
	@Autowired
	StaffMapper staffMapper;
	@Autowired
	MapperFacade orikaMapper;
	
	public StaffDto login(String phone, String password) {
		StaffDto staff = staffMapper.selectByPhone(phone);
		if(staff == null){
			throw new HandleException(ErrorCode.USER_NOFOUND_ERROR, "用户不存在");
		}else{
			if(staff.getState() == CommonState.VALID) {
				if(staff.getPassword().equals(password)){
					return staff;
				}else{
					throw new HandleException(ErrorCode.PASSWORD_ERROR, "密码错误");
				}
			}else {
				throw new HandleException(ErrorCode.USER_STATE_ERROR, "用户已被停用");
			}
		}
	}

	@Transactional
	public StaffDto register(AddStaffBo addStaffBo) {
		String name = addStaffBo.getName();
		String phone =addStaffBo.getPhone();
		String password = "7dd75c55c0f3a84969cacc5fcdbbd980";//md5("123456"+"x")->hex 
		
		Staff staff = staffMapper.selectOne(new QueryWrapper<Staff>().lambda().eq(Staff::getPhone, phone));
	
		if(staff != null){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "手机号重复、用户已存在");
		}
		staff = new Staff();
		staff.setName(name);
		staff.setPhone(phone);
		staff.setPassword(password);
		staff.setState(CommonState.VALID);
		staffMapper.insert(staff);
		
		Set<Role> roles = addStaffBo.getRoles().stream().map(key->{
			return roleService.getRoleByKey(key);
		}).collect(Collectors.toSet());
				
		staffRoleService.addStaffRole(staff.getId(), roles);
		
		StaffDto dto = orikaMapper.map(staff, StaffDto.class);
		dto.setRoles(orikaMapper.mapAsSet(roles, RoleDto.class));
		return dto;
	}

	public void modifyPassword(Integer id, ModifyPasswordBo modifyPasswordBo) {
		Staff staff = staffMapper.selectById(id);
		if(staff.getPassword().equals(modifyPasswordBo.getOldPassword())) {
			staff.setPassword(modifyPasswordBo.getNewPassword());
			staffMapper.updateById(staff);
		}else {
			throw new HandleException(ErrorCode.NORMAL_ERROR, "旧密码错误");
		}
	}
	
	public void changeState(Integer id, CommonState state) {
		Staff staff = staffMapper.selectById(id);
		staff.setState(state);
		staffMapper.updateById(staff);
	}

	@Override
	public void resetPassword(Integer id) {
		Staff staff = staffMapper.selectById(id);
		staff.setPassword("7dd75c55c0f3a84969cacc5fcdbbd980");
		staffMapper.updateById(staff);
	}

	@Override
	public IPage<StaffDto> queryStaff(StaffQuery query) {
		Page<StaffDto> page = new Page<StaffDto>(query.getCurrent(), query.getPageSize());
		
		IPage<StaffDto> pageResult = staffMapper.queryStaff(page, query);
		
		return pageResult;
	}

	@Override
	public StaffDto updateStaff(ModifyStaffBo modifyStaffBo) {
		Staff staff = staffMapper.selectById(modifyStaffBo.getId());
		staff.setName(modifyStaffBo.getName());
		staff.setPhone(modifyStaffBo.getPhone());
		
		staffMapper.updateById(staff);
		
		Set<Role> roles = modifyStaffBo.getRoles().stream().map(key->{
			return roleService.getRoleByKey(key);
		}).collect(Collectors.toSet());
		
		staffRoleService.updateStaffRole(staff.getId(), roles);
		
		StaffDto dto = orikaMapper.map(staff, StaffDto.class);
		dto.setRoles(orikaMapper.mapAsSet(roles, RoleDto.class));
		return dto;
	}
	
}
