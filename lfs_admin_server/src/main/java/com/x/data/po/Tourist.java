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
	private String surname;
	private String givenNames;
	private String phone;
	
	@Data
	public static class Credentials{
		private CredentialsType idType;
		private String idNo;
		private String validDate;
	}
	
	private List<Credentials> credentials;
	
	private String uid;
	
}