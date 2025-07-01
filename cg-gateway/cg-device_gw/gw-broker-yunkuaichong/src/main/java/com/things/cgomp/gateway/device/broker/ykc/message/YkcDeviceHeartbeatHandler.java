package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.broker.IBroker;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.broker.config.ServiceNodeInfo;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DeviceConfirmPayRuleMsg;
import com.things.cgomp.common.mq.message.DeviceOnLineReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcDeviceHeartbeatIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceHeartbeatOut;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcDeviceQrcodeOut;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 心跳处理器
 */
@Slf4j
@Component
public class YkcDeviceHeartbeatHandler extends YkcAbstractMessageHandler<YkcDeviceHeartbeatIn, YkcDeviceHeartbeatOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceHeartbeatIn message) {
        log.debug("心跳包数据 :{} ", message);
        //回复心跳包
        sendAckMsg(sessionContext, ctx.channel(), session, YkcDeviceHeartbeatOut.builder(message.getFrameSerialNo(), message.getDeviceNo(), message.getGunNo()));

        sessionContext.keepSession(session);

        gunOnline(sessionContext, session, message);

        // 第一次心跳上来, 说明计费模型已经同步到设备, 更新平台的计费模型id
        sendSyncFeeModel(sessionContext.getDeviceService(), (YkcSession) session);

        setQr(ctx, sessionContext, session, message);

    }

    private  void gunOnline(SessionContext sessionContext, Session session, YkcDeviceHeartbeatIn message) {
        try{
            //子设备上线
            SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());
            if(childDeviceInfo.getIsOnline() == null || !childDeviceInfo.getIsOnline()){
                // 上线
                IBroker broker = sessionContext.getBroker();
                HubBrokerConfig brokerConfig = broker.getBrokerConfig();
                ServiceNodeInfo hubNodeInfo = brokerConfig.getHubNodeInfo();
                Long validTime = sessionContext.getBroker().getValidTime(session);
                DeviceOnLineReqMsg reqMsg = DeviceOnLineReqMsg.builder()
                        .onLineTime(System.currentTimeMillis())
                        .nodeId(hubNodeInfo.getNodeId())
                        .validTime(validTime)
                        .brokerId(brokerConfig.getBrokerId())
                        .deviceId(childDeviceInfo.getDeviceId())
                        .build();
                sessionContext.getDeviceService().onLine(reqMsg, session, new SimpleServiceCallback<Void>() {
                    @Override
                    public void onSuccess(Void msg) {
                        childDeviceInfo.setIsOnline(true);
                        log.info("发送充电枪上线消息成功,deviceNo={}, gunNo={}",session.getSn(),message.getGunNo());
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.error("发送充电枪上线消息失败,deviceNo={}, gunNo={}", session.getSn(),message.getGunNo(), e);
                    }
                });
            }

        }catch (Exception e){
            log.error("gunOnline error, deviceNo={}, gunNo={}", session.getSn(), message.getGunNo(), e);
        }

    }

    /**
     * 设置二维码
     */
    private void setQr(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcDeviceHeartbeatIn message) {
        try {

            SessionDeviceInfo childDeviceInfo = session.getChildDeviceInfo(message.getGunNo());
            if (childDeviceInfo != null) {
                // 更新二维码URL
                if (!childDeviceInfo.getQrSetResult()) {
                    // 如果不成功，重试三次，三次失败则交由桩处理
                    if (childDeviceInfo.getQrSetRetryTimes().getAndIncrement() < 3) {
                        sendAckMsg(sessionContext, ctx.channel(), session, YkcDeviceQrcodeOut.of(session.nexMsgId(),
                                message.getDeviceNo(),
                                childDeviceInfo.getSn(),
                                childDeviceInfo.getDeviceId()));
                    }
                }
            }

        } catch (Exception e) {
            log.error("设置二维码失败, deviceNo={}, gunNo={},", session.getSn(), message.getGunNo(),e);
        }
    }

    private void sendSyncFeeModel(IDeviceServiceApi deviceServiceApi, YkcSession ykcSession) {
        Boolean needSyncFeeModel = ykcSession.getNeedSyncFeeModel();
        if (needSyncFeeModel) {
            synchronized (needSyncFeeModel) {
                try {
                    if (needSyncFeeModel) {
                        DeviceConfirmPayRuleMsg deviceConfirmPayRuleMsg = DeviceConfirmPayRuleMsg.builder()
                                .currentPayRuleId(ykcSession.getCurrentPayRuleId())
                                .currentPayModelId(ykcSession.getCurrentPayModeId())
                                .build();
                        deviceServiceApi.sendData(MQTopics.CONFIRM_MESSAGE,
                                MQTopics.Tag.CONFIRM_PAY_RULE_UPDATE,
                                deviceConfirmPayRuleMsg, ykcSession, null,
                                null,
                                System.currentTimeMillis(),
                                new SimpleServiceCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void msg) {
                                        log.info("发送计费生效通知成功, msg:{}", deviceConfirmPayRuleMsg);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        log.error("发送计费生效通知失败, msg:{}", deviceConfirmPayRuleMsg);
                                    }
                                });


                    }
                    ykcSession.setNeedSyncFeeModel(false);
                } catch (Exception e) {
                    log.error("sendSyncFeeModel.error,", e);
                }
            }
        }


    }

    @Override
    public void write(ByteBuf byteBuf, YkcDeviceHeartbeatOut ykcDeviceHeartbeatOut) {
        writeDeviceNo(byteBuf, ykcDeviceHeartbeatOut.getDeviceNo());
        // 枪号
        writeGunNo(byteBuf, ykcDeviceHeartbeatOut.getGunNo());
        // 心跳应答
        byteBuf.writeByte(ykcDeviceHeartbeatOut.getAck());
    }


}
