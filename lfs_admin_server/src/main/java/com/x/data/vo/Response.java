package com.x.data.vo;

import com.x.constant.ErrorCode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
	
    public static final String SUCCESS_MSG = "成功";
    
	private int code;

    private Object data;

    private String message;

    public Response(){
    	this.code = ErrorCode.NORMAL_ERROR;
    	this.data = null;
    	this.message = "失败";
    }
    
    public Response(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.message = msg;
    }

	
//	public static Response OK(Object object) {
//		return new Response(ErrorCode.OK, object, SUCCESS_MSG);
//	}
	
	public static Response Error(int code, String msg) {
		return new Response(code, null, msg);
	}

	public static Response SystemError() {
		return new Response(ErrorCode.NORMAL_ERROR, null, "系统异常");
	}

	public static Response NormalError(String msg) {
		return new Response(ErrorCode.NORMAL_ERROR, null, msg);
	}
}
