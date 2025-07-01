package com.things.cgomp.gateway.device.broker.ykc;


import com.alibaba.fastjson2.JSONObject;
import com.google.gson.JsonObject;
import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.broker.tcp.MessageProcessHandler;
import com.things.cgomp.common.gw.device.context.broker.tcp.TcpBrokerTemplateService;
import com.things.cgomp.common.gw.device.context.broker.tcp.TcpConfigParam;
import com.things.cgomp.common.gw.device.context.broker.tcp.UnpackRule;
import com.things.cgomp.common.gw.device.context.rest.IPushService;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcDecoder;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcEncoder;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcLengthFrameDecoder;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSession;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSessionContext;
import com.things.cgomp.gateway.device.broker.ykc.handler.YkcChannelHandler;
import com.things.cgomp.gateway.device.broker.ykc.message.YkcMessageProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ResourceLeakDetector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service("ykcBrokerService")
public class YkcBrokerService extends TcpBrokerTemplateService<TcpConfigParam> {

    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private MessageProcessHandler messageProcessHandler;

    @Autowired
    private IDeviceServiceApi deviceServiceApi;
    @Autowired
    private YkcEncoder ykcEncoder;

    @Override
    protected SessionContext sessionContextLoad() {
        return new YkcSessionContext(deviceServiceApi, this);
    }

    @Override
    protected TcpConfigParam processInit() throws Exception {

        Map map = JSONObject.parseObject(this.getBrokerConfig().getConfigInfo(), Map.class);
        JSONObject unpackRuleCfg
                = (JSONObject)map.get("unpackRule");
        if (map == null) {
            return null;
        }
        YkcSessionContext ykcSessionContext = (YkcSessionContext) getSessionContext();
        messageProcessHandler = new YkcMessageProcessHandler(ykcEncoder, ykcSessionContext);
        return TcpConfigParam
                        .builder()
                        .bindPort(this.getListenerPort())
                        .bossGroupThreadCount(map.get("bossGroupThreadCount") == null ? 5 :
                                (Integer) map.get("bossGroupThreadCount"))
                        .workGroupThreadCount(map.get("workGroupThreadCount") == null ? 10 :
                                Integer.valueOf(map.get("workGroupThreadCount").toString()))
                        .max_pay_load_size(map.get("max_pay_load_size") == null ? 10 :
                                (Integer) map.get("max_pay_load_size"))
                        .msgProcessHandler(null)
                        .leak_detector_level(map.get("leak_detector_level") == null ? "DISABLED" :
                                (String) map.get("leak_detector_level"))
                        .so_keep_alive(true)
                        .msgProcessHandler(messageProcessHandler)
                        .build();
    }

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
                            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(initConfig.getKeepaliveTime(), 0, 0, TimeUnit.MINUTES))
                                    .addLast("ykcLengthFrameDecoder", new YkcLengthFrameDecoder())
                                    .addLast("ykcDecoder", new YkcDecoder())
//                                    .addLast("ykcEncoder", new YkcEncoder())
                                    .addLast("ykcChannelHandler", getHandler(getSessionContext(), config.getMsgProcessHandler()));
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

    @Override
    protected ChannelHandler getHandler(SessionContext brokerSessionContext, MessageProcessHandler messageProcessHandler) {
        return new YkcChannelHandler(brokerSessionContext, (YkcMessageProcessHandler) messageProcessHandler);
    }


    @Override
    public void starBrokerContextTask() {
        super.starBrokerContextTask();
    }


    @Override
    public IPushService getPushService() {
        return (YkcMessageProcessHandler)messageProcessHandler;
    }

    @Override
    public boolean destroySession(Long eventTime, Long deviceId) {
        SessionContext sessionContext = this.getSessionContext();
        Session session = sessionContext.getSession(deviceId);
        if (session == null) {
            return false;
        } else {
            if (eventTime < session.getStartTime()) {
                return false;
            }
            if (session instanceof YkcSession) {
                YkcSession mqttSession = (YkcSession) session;
                Channel channel = mqttSession.getChannel();
                if (channel.isActive()) {
                    channel.close();
                    return true;
                }
            }
        }

        return false;
    }
}


