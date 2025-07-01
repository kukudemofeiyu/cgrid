package com.things.cgomp.app.queue;

import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PortSessionManager {

    /**
     * 订阅主题map
     *
     */
    public static final Map<Long/*端口Id*/, WebSocketSession> PORT_SESSION_MAP = new ConcurrentHashMap<>();


    public static WebSocketSession getWebSocketSession(Long portId) {
        return PORT_SESSION_MAP.get(portId);
    }

    public static void removeTopic(Long portId) {
        PORT_SESSION_MAP.remove(portId);
    }

    public static void addSocketServerMap(Long portId, WebSocketSession webSocketSession) {
        PORT_SESSION_MAP.put(portId, webSocketSession);
    }
}
