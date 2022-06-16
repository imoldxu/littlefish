package com.x.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.x.data.pojo.enums.CommonState;

import lombok.Data;

@Data
public class StaffDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 212523657815550929L;

	private Integer id;
	
	private String name;
	
	private String phone;
	
	private String password;

	private Date createTime;
		
	private CommonState state;

	private Integer deleted;
		
	private Set<RoleDto> roles;
	
}
