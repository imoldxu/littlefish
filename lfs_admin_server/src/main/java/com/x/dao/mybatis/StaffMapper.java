package com.x.dao.mybatis;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.x.data.bo.StaffQuery;
import com.x.data.dto.StaffDto;
import com.x.data.po.Staff;

public interface StaffMapper extends BaseMapper<Staff>{

	public StaffDto selectByPhone(@Param(value = "phone") String phone);
	
//	public StaffDto selectById(@Param(value = "uid") Integer uid);

	public IPage<StaffDto> queryStaff(Page<StaffDto> page, @Param("query") StaffQuery query);
}
