package com.x.lfs.data.bo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Valid
public class WxUserInfoBo {

	private String rawData;
	private String signature;
	@NotBlank
	private String encryptedData;
	@NotBlank
	private String iv;
	
	public String getRawData() {
		return rawData;
	}
	public void setRawData(String rawData) {
		this.rawData = rawData;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getEncryptedData() {
		return encryptedData;
	}
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	public String getIv() {
		return iv;
	}
	public void setIv(String iv) {
		this.iv = iv;
	}
	
}
