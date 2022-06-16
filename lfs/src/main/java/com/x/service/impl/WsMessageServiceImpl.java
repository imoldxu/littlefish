package com.x.service.impl;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.springframework.stereotype.Service;

import com.x.service.WsMessageService;

@Service
public class WsMessageServiceImpl implements WsMessageService {

	private ConcurrentHashMap<Long, Session> sessionMap = new ConcurrentHashMap<Long, Session>();

	public void register(Long uid, Session session) {
		sessionMap.put(uid, session);
	}

	public void remove(Long uid) {
		sessionMap.remove(uid);
	}

	public void asyncSendMessage(Long uid, Object message) {
		Session session = sessionMap.get(uid);
		// 异步发送
		session.getAsyncRemote().sendObject(message);
	}

	public void sendMessage(Long uid, Object message) throws IOException, EncodeException {
		Session session = sessionMap.get(uid);
		// 同步发送
		session.getBasicRemote().sendObject(message);
	}

}
