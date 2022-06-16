package com.x.data.dto;

import java.io.Serializable;
import java.util.List;

import com.x.data.po.Permission;

import lombok.Data;

@Data
public class RoleDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5497938944102935618L;

	private Integer id;
	
	private String name;
	
	private String key;
	
	private List<Permission> permissions;
	
}
