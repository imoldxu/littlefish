//package com.x.data.po;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Set;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.DBRef;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import lombok.Data;
//
//
//@Data
//@Document(collection="admin")
//public class Admin {
//
//	@Id
//	private String id;
//	
//	private String name;
//	
//	private String phone;
//	
//	private String password;
//
//	private Date createtime;
//	
//	public final static int STATE_VALID = 1;
//	public final static int STATE_INVALID = 0;
//	
//	private Integer state;
//	
//	@DBRef
//	private Set<Role> roles;
//	
//}
