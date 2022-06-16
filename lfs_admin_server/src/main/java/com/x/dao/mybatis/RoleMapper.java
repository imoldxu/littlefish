package com.x.dao.mybatis;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.x.data.po.Role;

public interface RoleMapper extends BaseMapper<Role>{

	@Select("select * from t_role where t_role.key = #{key} limit 1")
	public Role getRoleByKey(@Param("key") String key);

}
