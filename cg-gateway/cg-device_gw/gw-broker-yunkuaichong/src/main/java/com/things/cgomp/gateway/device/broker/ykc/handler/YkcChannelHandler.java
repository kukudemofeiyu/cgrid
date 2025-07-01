package com.things.cgomp.gateway.device.broker.ykc.handler;

import com.things.cgomp.common.gw.device.context.broker.tcp.TcpDefaultChannelHandler;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.TcpSessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSession;
import com.things.cgomp.gateway.device.broker.ykc.message.YkcMessageProcessHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class YkcChannelHandler extends TcpDefaultChannelHandler {

    public YkcChannelHandler(SessionContext brokerSessionContext, YkcMessageProcessHandler ykcMessageProcessHandler) {
        super(brokerSessionContext, ykcMessageProcessHandler);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //验证消息
        if (!(msg instanceof YkcMessageIn)) {
            log.warn("no support msg: {}", msg);
            return;
        }

        YkcMessageIn ykcMessageIn = (YkcMessageIn) msg;
        //获取绑定的session
        session = ctx.channel().attr(TcpSessionContext.S_DEVICE_SESSION).get();
        try {

            ((YkcMessageProcessHandler)messageProcessHandler).processInput(ctx,
                    (YkcSession) session,
                    ykcMessageIn);
        } catch (Exception e) {
            log.error("msg={},session={}", msg, session, e);
        } finally {

            if(ykcMessageIn.getMessageBody() != null){
                ReferenceCountUtil.safeRelease(ykcMessageIn.getMessageBody());
            }

        }

    }

}
