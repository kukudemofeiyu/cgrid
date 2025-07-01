package com.things.cgomp.app.service.impl;

import com.things.cgomp.app.queue.PortSessionManager;
import com.things.cgomp.app.service.WebsocketService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class WebsocketDeviceDataServiceImpl implements WebsocketService {
    @Override
    public void process(WebSocketSession session, String message) {
     //订阅数据
        Long portId = Long.valueOf(message);
        PortSessionManager.addSocketServerMap(portId, session);
    }
}
