package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.device.pojo.queue.AuthReqMsg;
import com.things.cgomp.common.device.pojo.queue.AuthResponseMsg;
import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.message.callback.CallBackMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceVinStatusIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceVinStatusOut;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 充电桩上报 vin 码
 */
@Slf4j
@Component
public class YkcDeviceVinStatusHandler extends YkcAbstractMessageHandler<YkcDeviceVinStatusIn, YkcDeviceVinStatusOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceVinStatusIn message) throws Exception {
        log.info("插枪 :{}", message);

        Map<String , Object> authParam  = new HashMap<>(8);
        authParam.put("aliasSn", message.getGunNo());
        authParam.put("parentId", String.valueOf(session.getDeviceId()));
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

                    //更新子设备session会话
                    SessionDeviceInfo deviceInfo = SessionDeviceInfo.builder().deviceId(authResponseMsg.getDeviceId()).build();
                    session.addChildDeviceInfo(authResponseMsg.getAliasSn(), deviceInfo);

                    try {
                        String orderNo = requestDataService.getOrderNo(authResponseMsg.getDeviceId(), message.getVinTs(), message.getVin());
                        if(orderNo != null){
                            log.info("请求生成订单号成功, deviceNo={}, gunNo={}, orderNo={}", message.getDeviceNo(), message.getGunNo(), orderNo);

                            sendAckMsg(sessionContext, ctx.channel(), session, new YkcDeviceVinStatusOut(message.getFrameSerialNo(),
                                    message.getDeviceNo(), message.getGunNo(),Boolean.TRUE, orderNo));
                        }else{
                            log.error("请求生成订单号失败, deviceNo={}, gunNo={}", message.getDeviceNo(), message.getGunNo());
                            sendErrorVinRespMsg(sessionContext, ctx.channel(), session, message.getFrameSerialNo(), message.getDeviceNo(),
                                    message.getGunNo());
                        }

                    } catch (Exception e) {
                        log.error("请求生成订单号异常, deviceNo={}, gunNo={}", message.getDeviceNo(), message.getGunNo(), e);
                        sendErrorVinRespMsg(sessionContext, ctx.channel(), session, message.getFrameSerialNo(), message.getDeviceNo(),
                                message.getGunNo());
                    }

                }else{
                    log.error("插枪失败 : 枪信息不存在, deviceNo:{}, gunNo:{}", message.getDeviceNo(), message.getGunNo());
                    sendErrorVinRespMsg(sessionContext, ctx.channel(), session,
                            message.getFrameSerialNo(),
                            message.getDeviceNo(),
                            message.getGunNo());
                }
            }

            @Override
            public void onError(Throwable e) {
                log.error("插枪失败 :{}", message, e);
                sendErrorVinRespMsg(sessionContext, ctx.channel(), session, message.getFrameSerialNo(), message.getDeviceNo(),
                        message.getGunNo());
            }
        });
    }

    private void sendErrorVinRespMsg(SessionContext sessionContext, Channel channel, Session session,  Integer frameSerialNo,
                                     String deviceNo, String gunNo){
        YkcDeviceVinStatusOut ykcDeviceVinStatusOut = new YkcDeviceVinStatusOut(
                frameSerialNo,
                deviceNo,
                gunNo,
                Boolean.FALSE,
                "00000000000000000000000000000000");
        sendAckMsg(sessionContext, channel, session, ykcDeviceVinStatusOut);
    }



    @Override
    public void write(ByteBuf byteBuf, YkcDeviceVinStatusOut out) {
        log.info("回复插枪:{}", out);
        writeDeviceNo(byteBuf, out.getDeviceNo());
        writeGunNo(byteBuf,out.getGunNo());
        byteBuf.writeByte(out.getAuthSuccess() ? 0x01 : 0x00);
        writeOrderNo(byteBuf, out.getOrderNo());
    }
}
