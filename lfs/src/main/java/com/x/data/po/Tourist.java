package com.x.data.po;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.x.data.pojo.enums.CredentialsType;

import lombok.Data;

          
@Document(collection="tourist")
@Data
public class Tourist {
	
	@Id
	private String id;
	private String name;
	private String surname; //英文的姓
	private String givenNames; //英文的名
	private String phone;
	
	//多种证件
	@Data
	public static class Credentials{
		private CredentialsType type;
		private String no;
		private String validDate;
	}
	
	private List<Credentials> credentials;
	
	private String uid;
	
}