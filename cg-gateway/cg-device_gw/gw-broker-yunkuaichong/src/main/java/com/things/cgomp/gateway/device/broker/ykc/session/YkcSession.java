package com.things.cgomp.gateway.device.broker.ykc.session;

import com.things.cgomp.common.gw.device.context.session.TcpSession;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Data
public class YkcSession extends TcpSession {

    /**
     * 是否要同步计费模型
     */
    public volatile Boolean needSyncFeeModel = false;

    public Long currentPayRuleId;
    public Integer currentPayModeId;


}
