package com.things.cgomp.common.gw.device.context.broker.tcp;


import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionCloseCause;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.TcpSessionContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


@Slf4j
public class TcpDefaultChannelHandler<ProcessHandler extends MessageProcessHandler> extends ChannelInboundHandlerAdapter {
    protected SessionContext brokerSessionContext;
    protected volatile ProcessHandler messageProcessHandler;


    public TcpDefaultChannelHandler(SessionContext brokerSessionContext, ProcessHandler messageProcessHandler) {
        this.brokerSessionContext = brokerSessionContext;
        this.messageProcessHandler = messageProcessHandler;
    }

    protected Session session;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

    }

    /**
     * 终端不活跃,断线了
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        releaseSession(ctx.channel(), SessionCloseCause.INACITVE);

    }

    protected void releaseSession(Channel channel, SessionCloseCause inacitve) {

        Boolean reLease = channel.attr(TcpSessionContext.S_DEVICE_RELEASE).get();
        if (null != reLease && reLease) {
            log.info("已处理过,channelId={}" , channel.id());
            channel.close();
        } else {
            Session session = channel.attr(TcpSessionContext.S_DEVICE_SESSION).get();
            if (null == session) {
                //没有通过解码的会话,可能是端口探测的数据包
                //认证没有通过的链接
                log.info("没有认证过的会话,channel={}" , channel);
            } else {
                channel.attr(TcpSessionContext.S_DEVICE_RELEASE).set(true);
                brokerSessionContext.releaseSession(session, inacitve, true);
                channel.close();
            }

        }

    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Channel channel = ctx.channel();
//                log.info("read idle. channelId={}" , channel.id());
                releaseSession(channel, SessionCloseCause.SERVER_IO_ACITVE);
            } else if (event.state() == IdleState.WRITER_IDLE) {
                // logger.info("write idle.h" +
                // te.getTerminalId(ctx.channel()));
            } else if (event.state() == IdleState.ALL_IDLE) {
                // logger.info("all idle.h" + te.getTerminalId(ctx.channel()));
                // Channel channel = ctx.channel();
                // context.serverDownLineForAllIDle(channel);
                // ctx.close();
            }

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("TcpBrokerHandler exception cause={}", cause);
        //releaseSession(ctx.channel(), SessionCloseCause.READ);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }




}
