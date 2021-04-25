package com.x.lfs.data.po;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection="role")
public class Role {
	
	@Id
	private String id;
	
	private String name;
	
	@DBRef
	private List<Permission> permissions;
	
}
