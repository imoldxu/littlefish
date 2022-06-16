package com.x.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.x.data.bo.AddStaffBo;
import com.x.data.bo.ModifyPasswordBo;
import com.x.data.bo.ModifyStaffBo;
import com.x.data.bo.StaffQuery;
import com.x.data.dto.StaffDto;
import com.x.data.pojo.enums.CommonState;

public interface StaffService{

	public StaffDto login(String phone, String password);
	
	public StaffDto register(AddStaffBo addUserBo);
	
	public void modifyPassword(Integer id, ModifyPasswordBo modifyPasswordBo);
	
	public void resetPassword(Integer id);
	
	public IPage<StaffDto> queryStaff(StaffQuery query);
	
	public StaffDto updateStaff(ModifyStaffBo modifyStaffBo);

	public void changeState(Integer id, CommonState invalid);
	
}
