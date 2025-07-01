package com.things.cgomp.app.domain.req;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WebsocketRequest {
    /**
     * 消息
     */
    private String message;
    /**
     * sessionId
     */
    private String sessionId;
}
