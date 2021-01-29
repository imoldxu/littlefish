package com.x.lfs.config.constant;

public enum OrderState {

	NEW(0, "待成团"),
	UNPAY(1, "待支付"),
	PAYED(2, "已支付"),
	COMPALTE(3, "已成团");

    private final Integer code;
    private final String message;

 // 构造方法  
    private OrderState(Integer code, String message) {  
        this.code = code;
        this.message = message;
    }

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
    
    
}
