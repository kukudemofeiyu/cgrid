package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.pojo.queue.AuthReqMsg;
import com.things.cgomp.common.device.pojo.queue.AuthResponseMsg;
import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.message.callback.CallBackMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceLoginIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceLoginOut;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceTimeSyncOut;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSessionContext;
import com.things.cgomp.gateway.device.broker.ykc.utils.RSAUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class YkcDeviceLoginHandler extends YkcAbstractMessageHandler<YkcDeviceLoginIn, YkcDeviceLoginOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceLoginIn message) {
        if (session != null) {
            log.error("重复登录, session:{}", session);
        }
        Channel channel = ctx.channel();
        log.info("登录消息from ip={},port={}: {} ", getClientIP(channel), getClientPort(channel), message);

        Map<String, Object> authParam = new HashMap<>(8);
        authParam.put("sn", String.valueOf(message.getDeviceNo()));
        AuthReqMsg authReqMsg = AuthReqMsg.builder()
                .authParam(authParam)
                .build();
        IDeviceServiceApi deviceService = sessionContext.getDeviceService();
        deviceService.auth(authReqMsg, new SimpleServiceCallback<CallBackMsg<AuthResponseMsg>>() {
                    @Override
                    public void onSuccess(CallBackMsg<AuthResponseMsg> msg) {
                        Integer recode = msg.getCode();
                        if (recode.intValue() == 0) {
                            AuthResponseMsg authResponseMsg = msg.getMsg();
                            Session session = sessionContext.createSession(
                                    channel,
                                    channel.id().asLongText(),
                                    getClientIP(channel),
                                    authResponseMsg.getDeviceId(),
                                    authResponseMsg.getProductId(),
                                    authResponseMsg.getSn(),
                                    authResponseMsg.getConfig()
                            );
                            sessionContext.putSession(authResponseMsg.getDeviceId(), session);
                            sessionContext.keepSession(session);

                            try {
                                //AES密钥解密
                                Map<String, String> rsaKeyPair = requestDataService.getRSAKey(session.getDeviceId());
                                String decryptRandomKey = RSAUtils.decryptRSADefault(rsaKeyPair.get("privateKey"), message.getRandomKey());

                                log.info("AESKey解密成功,  deviceNo: {}  AESKey is {} ", message.getDeviceNo(), decryptRandomKey);
                                channel.attr(YkcSessionContext.DEVICE_SECRET_KEY).set(decryptRandomKey);

                                // 回复登录结果
                                sendAckMsg(sessionContext, channel, session, new YkcDeviceLoginOut(session.nexMsgId(), message.getDeviceNo(), Boolean.TRUE, rsaKeyPair.get("publicKey")));

                                // 子设备信息
                                List<DeviceInfo> guns = deviceService.getGuns(session.getDeviceId());
                                if(guns != null && guns.size() > 0){
                                    guns.forEach(gun ->{
                                        SessionDeviceInfo deviceInfo = SessionDeviceInfo.builder().deviceId(gun.getDeviceId()).sn(gun.getAliasSn())
                                                .qrSetRetryTimes(new AtomicInteger(0))
                                                .qrSetResult(false).build();
                                        session.addChildDeviceInfo(gun.getAliasSn(), deviceInfo);
                                    });
                                }

                            } catch (Exception e) {
                                log.error("登录失败 deviceNo={}, error:{}", message.getDeviceNo(), e);
                                sendAckMsg(sessionContext, channel, session, new YkcDeviceLoginOut(session.nexMsgId(), message.getDeviceNo(), Boolean.FALSE));
                                ctx.close();
                            }

                            //发送校时
                            sendAckMsg(sessionContext, channel, session, new YkcDeviceTimeSyncOut(session.nexMsgId(), message.getDeviceNo(), LocalDateTime.now()));

                            //发送登录报文
                            try {
                                String s = channel.attr(YkcSessionContext.DEVICE_SECRET_KEY).get();
                                sendCmdLog(sessionContext, session, message, 1,   s);

                            } catch (Exception e) {
                                log.error("发送登录日志失败: deviceId={}", session.getDeviceId(), e);
                            }

                        } else {
                            log.error("登录失败, 认证不成功 , deviceNo={}", message.getDeviceNo());
                            ctx.close();
                        }

                    }


                    @Override
                    public void onError(Throwable e) {
                        log.error("认证失败 , deviceNo={}", message.getDeviceNo());
                        ctx.close();
                    }
                }

        );
    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceLoginOut out) {
        writeDeviceNo(byteBuf, out.getDeviceNo());
        byteBuf.writeByte(out.getLoginResult() ? 0x00 : 0x01);
        byte[] rasPublicKey = out.getRsaPublicKey().getBytes(StandardCharsets.US_ASCII);
        byteBuf.writeByte(rasPublicKey.length);
        byteBuf.writeBytes(rasPublicKey);
    }

    private String getClientIP(Channel channel) {
        try {
            InetSocketAddress address = (InetSocketAddress) channel
                    .remoteAddress();
            return address.getAddress().getHostAddress();


        } catch (Exception e) {
            log.warn("getClientIP error", e);
        }
        return null;
    }

    private Integer getClientPort(Channel channel) {
        try {
            InetSocketAddress address = (InetSocketAddress) channel
                    .remoteAddress();
            return address.getPort();


        } catch (Exception e) {
            log.warn("getClientPort error", e);
        }
        return null;
    }
}
