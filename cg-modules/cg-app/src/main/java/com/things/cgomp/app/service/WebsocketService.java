package com.things.cgomp.app.service;


import com.things.cgomp.app.domain.req.WebsocketRequest;
import org.springframework.web.socket.WebSocketSession;

public interface WebsocketService {

    /**
     * 处理数据订阅
     */
    void process(WebSocketSession session, String message);

}
