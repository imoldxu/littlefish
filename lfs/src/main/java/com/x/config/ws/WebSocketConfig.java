package com.x.config.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * 配置websocket
 * @author 老徐
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig extends ServerEndpointConfig.Configurator{

	/* 修改握手,就是在握手协议建立之前修改其中携带的内容,将httpsession放入UserProperties */
    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession)request.getHttpSession();
        if (httpSession != null) {
            // 读取session域中存储的数据
            config.getUserProperties().put(HttpSession.class.getName(),httpSession);
        }
        super.modifyHandshake(config, request, response);
    }
	
    @Bean
    public ServerEndpointExporter serverEndpoint() {
        return new ServerEndpointExporter();
    }
}