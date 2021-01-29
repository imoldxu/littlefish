package com.x.tools.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.x.lfs.context.ErrorCode;
import com.x.lfs.context.HandleException;

public class SessionUtil {

	public static final String SESSION_KEY = "user";
	
	public static void set(HttpServletRequest request, String key, Object value) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
		session.setMaxInactiveInterval(24*60*60);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(HttpServletRequest request, String key, Class<T> valueClass)  throws HandleException{
		HttpSession session = request.getSession();
		T value = (T) session.getAttribute(key);
		if(value==null){
			throw new HandleException(ErrorCode.SESSION_ERROR, "登录已过期,请重新登录");
		}
		return value;
	}
	
	//可能返回null
	@SuppressWarnings("unchecked")
	public static <T> T check(HttpServletRequest request, String key, Class<T> valueClass)  throws HandleException{
		HttpSession session = request.getSession();
		T value = (T) session.getAttribute(key);
		return value;
	}
}
