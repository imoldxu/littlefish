package com.x.data.bo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StaffQuery extends BasePageQuery{

	private String phone;
	
	private String name;
	
	private Integer state;
	
	private Integer role;
}
