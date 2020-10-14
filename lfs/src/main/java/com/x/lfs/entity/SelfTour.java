package com.x.lfs.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="selfTour")
public class SelfTour {

	@Id
	private String id;

}
