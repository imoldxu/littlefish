package com.x.data.pojo.enums;

import lombok.Getter;

@Getter
public enum CredentialsType {

	ID(1,"身份证"),
	PASSPORT(2,"护照"),	
	RESIDENCEBOOKLET(3,"户口簿");
	
	private final Integer type;
    private final String name;
    
    private CredentialsType(Integer type, String name) {
		this.type = type;
		this.name = name;
	}
}
