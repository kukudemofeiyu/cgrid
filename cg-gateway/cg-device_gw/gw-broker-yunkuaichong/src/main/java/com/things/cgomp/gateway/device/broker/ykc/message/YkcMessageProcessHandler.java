package com.things.cgomp.gateway.device.broker.ykc.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.pojo.device.DeviceCommandEnum;
import com.things.cgomp.common.device.pojo.device.push.PushInfo;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.gw.device.context.api.SyncMsgTemplate;
import com.things.cgomp.common.gw.device.context.broker.tcp.DefaultMessageProcessHandler;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.mq.message.DeviceCmdLogReqMsg;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcEncoder;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcMessage;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcMessageOutFactory;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSession;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSessionContext;
import com.things.cgomp.gateway.device.broker.ykc.utils.DeviceOptHandlerHelper;
import com.things.cgomp.gateway.device.broker.ykc.utils.ObjectLockUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.things.cgomp.gateway.device.broker.ykc.session.YkcSessionContext.DEVICE_SECRET_KEY;

@Slf4j
public class YkcMessageProcessHandler extends DefaultMessageProcessHandler implements YkcMessagePush {

    protected YkcSessionContext ykcSessionContext;

    private YkcEncoder ykcEncoder;

    public YkcMessageProcessHandler(YkcEncoder ykcEncoder, SessionContext sessionContext){
        this.ykcSessionContext = (YkcSessionContext)sessionContext;
        this.ykcEncoder = ykcEncoder;
    }

    @Override
    public void processInput(ChannelHandlerContext ctx, YkcSession session, YkcMessageIn msg) {

        //msg.getMessageType()== null说明解析有问题，只将报文存入数据库
        if(msg.getMessageType() == null){
            if(session != null){
                sendCmdLog(session, msg, 1, null);
            }
            return;
        }

        //没有鉴权但是发了其他消息，则关闭连接
        if (!msg.getMessageType().equals(DeviceOpConstantEnum.CONNECT.getOpCode())
                && (session == null || !session.isAuth())) {
            log.error("no auth ,msg : {} ", msg);
            closeChannel(ctx);
        }

        try {
            YkcAbstractMessageHandler handler = DeviceOptHandlerHelper.getHandler(msg.getMessageType());
            if (Objects.isNull(handler)) {
                log.error("no handler for frameType: {}", String.format("%04x", msg.getMessageType()));
                return;
            }
            handler.processRead(ctx, ykcSessionContext, session, msg);

        } catch (Exception e) {

            log.error("YkcMessageProcessHandler.processInput.error, frameType={}, hex={}", String.format("%04x", msg.getMessageType()), msg.getBody(), e);
        } finally {
            if(session != null){
                sendCmdLog(session, msg, 1, ctx.attr(DEVICE_SECRET_KEY).get());
            }
        }
    }

    private Boolean isSendMessage(Integer messageType){
        if(messageType!=null && (messageType.equals(DeviceOpConstantEnum.HEARTBEAT.getOpCode()) ||
                messageType.equals(DeviceOpConstantEnum.HEARTBEAT_RESP.getOpCode()))){
            return false;
        }

        return true;
    }


    @Override
    public PushResult processPush(PushInfo pushInfo) {
        log.info("收到下行指令,  pushInfo={} ",  pushInfo);

        try{

            YkcSession session = ykcSessionContext.getSession(pushInfo.getConnectId());
            if (session == null || !session.getChannel().isActive()) {
                log.error("session exception,  pushInfo={}", pushInfo);
                return PushResult.builder().succeed(false)
                        .code(ErrorCodeConstants.SESSION_IS_NOT_EXIST.getCode())
                        .requestId(pushInfo.getTransactionId())
                        .codeMsg(ErrorCodeConstants.SESSION_IS_NOT_EXIST.getMsg())
                        .build();
            }
            addPushInfo(session, pushInfo);

            int frameSerialNo = session.nexMsgId();
            DeviceCommandEnum deviceCommandEnum = pushInfo.getDeviceCommand();
            YkcMessageOut ykcMessageOut = YkcMessageOutFactory.newMessage(
                    deviceCommandEnum,
                    pushInfo,
                    frameSerialNo);

            if(ykcMessageOut == null){
                return PushResult.builder()
                        .succeed(false)
                        .code(ErrorCodeConstants.SYSTEM_ERROR.getCode())
                        .codeMsg(ErrorCodeConstants.SYSTEM_ERROR.getMsg())
                        .requestId(pushInfo.getTransactionId())
                        .data(null)
                        .build();

            }

            Object result = pushMsg(session, pushInfo.getDeviceNo(),frameSerialNo, ykcMessageOut);
            if(result == null){
                return PushResult.builder()
                        .succeed(false)
                        .code(ErrorCodeConstants.DEVICE_NO_ACK.getCode())
                        .codeMsg(ErrorCodeConstants.DEVICE_NO_ACK.getMsg())
                        .requestId(pushInfo.getTransactionId())
                        .build();
            }

            return JSONObject.parseObject(JSON.toJSONString(result), PushResult.class);


        }catch (Exception e){
            log.error("processPush.fail, connectId={}, gunNo={},", pushInfo.getConnectId(),
                    pushInfo.getGunNo() , e);

            return PushResult.builder()
                    .requestId(pushInfo.getTransactionId())
                    .succeed(false)
                    .code(ErrorCodeConstants.SYSTEM_ERROR.getCode())
                    .codeMsg(e.getMessage())
                    .build();
        }

    }

    private void addPushInfo(YkcSession session, PushInfo pushInfo) {
        pushInfo.setDeviceNo(session.getSn());

        if(pushInfo.getPortId() != null){
            Map<String, SessionDeviceInfo> childDevices = session.getChildDevices();
            if(!CollectionUtils.isEmpty(childDevices)){
                AtomicReference<String> gunNo = new AtomicReference<>("");
                childDevices.forEach((k,v)->{
                    if(v.getDeviceId().equals(pushInfo.getPortId())){
                        gunNo.set(k);
                    }
                });
                if(StringUtils.hasLength(gunNo.get())){
                    pushInfo.setGunNo(gunNo.get());
                }else{
                    DeviceInfo gunDevice = ykcSessionContext.getDeviceService().getDeviceInfoById(pushInfo.getPortId());
                    pushInfo.setGunNo(gunDevice.getAliasSn());
                }
            }
        }
    }



    private Object pushMsg(YkcSession session, String deviceNo, Integer frameSerialNo, YkcMessageOut message) throws Exception {
        Object reuslt = null;
        Channel channel = session.getChannel();
        ByteBuf sendDataBuffer = channel.alloc().directBuffer();
        Boolean encodeSuccess = false;
        try{

            encodeSuccess = ykcEncoder.encode(sendDataBuffer, message, channel.attr(DEVICE_SECRET_KEY).get());
            if(encodeSuccess){
                synchronized (ObjectLockUtils.getSendToDeviceLock(session.getDeviceId().toString())){
                    channel.writeAndFlush(sendDataBuffer);
                }

                SyncMsgTemplate syncMsgTemple = ykcSessionContext.getDeviceService().getSyncMsgTemple();
                reuslt = syncMsgTemple.sendMsg(deviceNo, frameSerialNo, 30 * 1000);
            }


        }catch (Exception e){
            log.error("发送数据失败: deviceId={}", session.getDeviceId(), e);
        }

        finally {

            if(encodeSuccess){
                sendCmdLog(session,message, 2, channel.attr(DEVICE_SECRET_KEY).get() );
            }

        }
        return reuslt;

    }

    public void sendCmdLog(Session session, YkcMessage message, Integer cmdLogType, String secret) {
        try {
            if (isSendMessage(message.getMessageType())) {

                DeviceCmdLogReqMsg deviceCmdLogReqMsg = DeviceCmdLogReqMsg.builder()
                        .cmd(message.getMessageType()!=null?String.format("%04x", message.getMessageType()):null)
                        .serialNo(message.getFrameSerialNo())
                        .body(message.getBody())
                        .upDown(cmdLogType)
                        .cmdDesc(message.getMessageType()!=null?DeviceOpConstantEnum.getOptEnum(message.getMessageType()).getOpName():null)
                        .updateTs(System.currentTimeMillis())
                        .originHex(message.getOriginHex())
                        .secret(secret)
                        .build();
                ykcSessionContext.getDeviceService().sendCmdLogData(deviceCmdLogReqMsg, session, message.getGunNo(),
                        message.getMessageType(), message.getTs());
            }
        } catch (Exception e) {
            log.error("发送交互日志失败: deviceId={}", session.getDeviceId(), e);
        }
    }

    private void closeChannel(ChannelHandlerContext ctx) {
        ctx.close();
    }
}
