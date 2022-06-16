package com.x.service;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Session;

/**
 * websocket消息服务
 * @author 老徐
 *
 */
public interface WsMessageService {

	public void register(Long uid, Session session);
	
	public void remove(Long uid);
	
	public void asyncSendMessage(Long uid, Object message);
	
	public void sendMessage(Long uid, Object message) throws IOException, EncodeException;
	
}
