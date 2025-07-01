package com.things.cgomp.common.gw.device.context.broker.tcp;


import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.common.gw.device.context.broker.BaseBrokerService;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;


@Slf4j
public abstract class TcpBrokerTemplateService<P extends TcpConfigParam> extends BaseBrokerService<TcpConfigParam> {

    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;


    @Override
    protected void processStart(TcpConfigParam config) throws Exception {
        try {

            log.info("Setting resource leak detector level to {}", config.getLeak_detector_level());
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.valueOf(config.getLeak_detector_level().toUpperCase()));
            log.info("Starting {} brokering...", initConfig.getName());
            bossGroup = new NioEventLoopGroup(config.getBossGroupThreadCount());
            workerGroup = new NioEventLoopGroup(config.getWorkGroupThreadCount());
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(initConfig.getKeepaliveTime(), 0, 0, TimeUnit.MINUTES));
                            UnpackRule rule = config.getUnpackRule();

                            ChannelHandler baseDecoder = getCodes(rule.getBasedFrameDecoder());
                            ChannelHandler decode = getCodes(rule.getDecodeClass());
                            ChannelHandler encode = getCodes(rule.getEncodeClass());
                            if (null != baseDecoder) {
                                ch.pipeline().addLast(baseDecoder);
                            }

                            if (null != decode) {
                                ch.pipeline().addLast(decode);
                            }

                            if (null != encode) {
                                ch.pipeline().addLast(encode);
                            }
                            SessionContext sessionContext = getSessionContext();
                            MessageProcessHandler handler = config.getMsgProcessHandler();
                            ChannelHandler channelHandler = getHandler(sessionContext, handler);
                            ch.pipeline().addLast("brokerHandler", channelHandler);
                        }

                    });


            /**
             * 有设IP，则绑定指定IP
             */
            if (StringUtils.hasLength(config.getBindIp())) {
                InetSocketAddress address = new InetSocketAddress(config.getBindIp(), config.getBindPort());
                channelFuture = serverBootstrap.bind(address).sync();
            } else {

                channelFuture = serverBootstrap.bind(config.getBindPort()).sync();
            }
            log.info("start {} broker succeed,bindIP:{},bindPort:{}", initConfig.getName(), StringUtils.hasLength(config.getBindIp()) ? config.getBindIp() : "0.0.0.0", config.getBindPort());
        } catch (InterruptedException e) {
            log.error("start {} failed", initConfig.getName(), e);
            throw e;
        }

    }

    /**
     * 要重写该方法来自定义ChannelHandler处理
     */
    protected ChannelHandler getHandler(
            SessionContext brokerSessionContext,
            MessageProcessHandler messageProcessHandler
    ) {

        return new TcpDefaultChannelHandler(brokerSessionContext, messageProcessHandler);
    }

    private ChannelHandler getCodes(String decode) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (!StringUtils.hasLength(decode)) {
            return null;
        }
        return (ChannelHandler)Class.forName(decode).newInstance();
    }

    @Override
    protected void processDestroy() throws Exception {

    }


    @Override
    public boolean destroySession(Long eventTime, Long deviceId) {
        return false;
    }


    @Override
    public boolean refreshSession(Long eventTime, Long deviceId) {
        return false;
    }


}
