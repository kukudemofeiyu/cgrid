package com.things.cgomp.app.handler;

import com.things.cgomp.app.queue.PortSessionManager;
import com.things.cgomp.app.service.WebsocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    @Resource
    private WebsocketService websocketService;
    //保存用户和枪的关系
    private static final ConcurrentHashMap<String, Long> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 连接建立时触发
        session.sendMessage(new TextMessage("Connected to WebSocket with ID: " + session.getId()));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        // 处理前端发送的消息
        String message = textMessage.getPayload();
        //保存用户和枪的关系
        String sessionId = session.getId();
        sessions.put(sessionId,Long.valueOf(message));
        log.info("sessionId={},收到数据客户端请求:{}", session.getId(), message);
        websocketService.process(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NotNull CloseStatus closeStatus) throws Exception {
        // 连接关闭时触发
        String sessionId = session.getId();
        Long portId = sessions.get(sessionId);
        sessions.remove(sessionId);
        //取消订阅
        PortSessionManager.removeTopic(portId);
    }

    /**
     * 向连接的客户端发送消息
     */

    public static void sendMessage(WebSocketSession session,String message) {
        try {
            if (!session.isOpen()) {
                return;
            }
            session.sendMessage(new TextMessage(message));
        } catch (Exception e) {
            log.info("sessionId={},发送数据失败={}",session.getId(), message, e);
        }
    }
}

