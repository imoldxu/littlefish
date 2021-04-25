package com.x.lfs.data.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection="selfTour")
@Data
public class SelfTour {

	@Id
	private String id;

}
