package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.rest.IPushService;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSession;
import io.netty.channel.ChannelHandlerContext;

public interface YkcMessagePush extends IPushService {

    void processInput(ChannelHandlerContext ctx, YkcSession session, YkcMessageIn inMsg);

}
