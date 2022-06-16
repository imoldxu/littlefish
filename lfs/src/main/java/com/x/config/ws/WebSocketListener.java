package com.x.config.ws;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.x.commons.util.SessionUtil;
import com.x.service.WsMessageService;


@Component
public class WebSocketListener implements ServletRequestListener{
    
	@Override
    public void requestInitialized(ServletRequestEvent event)  {
        HttpSession session = ((HttpServletRequest) event.getServletRequest()).getSession();
        
	}
 
    @Override
    public void requestDestroyed(ServletRequestEvent event)  {
    	
    }
}
