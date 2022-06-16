package com.x.dao.mybatis;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.data.po.StaffRole;

public interface StaffRoleMapper extends BaseMapper<StaffRole>{

	@Delete("DELETE FROM t_staff_role WHERE staff_id = #{staffId}")
	public int deleteAllRoleByStaff(@Param("staffId") Integer staffId);
	
}
