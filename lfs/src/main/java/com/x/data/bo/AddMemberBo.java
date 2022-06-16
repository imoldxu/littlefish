package com.x.data.bo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.x.commons.fastjson.MyEnumSerializer;
import com.x.config.validator.PhoneFormat;
import com.x.data.pojo.enums.IDType;

import lombok.Data;

@Data
public class AddMemberBo {

	@NotBlank
	@Length(max=50)
	private String name;
	
	@NotNull
	@JSONField(deserializeUsing=MyEnumSerializer.class)
	private IDType idType;
	
	@NotBlank
	@Length(max=18)
	private String idNumber;
	
	@PhoneFormat
	private String phone;
	
}
