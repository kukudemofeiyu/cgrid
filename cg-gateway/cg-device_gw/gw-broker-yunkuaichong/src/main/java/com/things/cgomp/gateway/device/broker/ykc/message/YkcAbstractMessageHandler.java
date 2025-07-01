package com.things.cgomp.gateway.device.broker.ykc.message;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.util.HexUtil;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.gw.device.context.api.SyncMsgTemplate;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.mq.message.DeviceCmdLogReqMsg;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcEncoder;
import com.things.cgomp.gateway.device.broker.ykc.codec.YkcMessage;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeStandardFeeOut;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.DeviceOpConstantEnum;
import com.things.cgomp.gateway.device.broker.ykc.service.RequestDataServiceImpl;
import com.things.cgomp.gateway.device.broker.ykc.utils.ObjectLockUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.things.cgomp.gateway.device.broker.ykc.session.YkcSessionContext.DEVICE_SECRET_KEY;

@Slf4j
public abstract class YkcAbstractMessageHandler<IN extends YkcMessageIn, OUT extends YkcMessageOut> {

    @Autowired
    private YkcEncoder ykcEncoder;

    @Autowired
    public RequestDataServiceImpl requestDataService;


    public Boolean sendAckMsg(SessionContext sessionContext, Channel channel, Session session, YkcMessageOut message )  {
        ByteBuf sendDataBuffer = channel.alloc().directBuffer();
        Boolean encodeSuccess = false;
        try{

            encodeSuccess = ykcEncoder.encode(sendDataBuffer, message, channel.attr(DEVICE_SECRET_KEY).get());
            if(encodeSuccess){
                synchronized (ObjectLockUtils.getSendToDeviceLock(session.getDeviceId().toString())){
                    channel.writeAndFlush(sendDataBuffer);
                }
            }
        }catch (Exception e){
            log.error("发送数据失败, deviceId={}", session.getDeviceId(), e );
            encodeSuccess = false;
        }

        finally {

            if(encodeSuccess){
                sendCmdLog(sessionContext, session, message, 2, channel.attr(DEVICE_SECRET_KEY).get());
            }
        }

        return encodeSuccess;

    }

    public void sendCmdLog(SessionContext sessionContext,
                           Session session,
                           YkcMessage message,
                           Integer cmdLogType,
                           String secretKey) {
        try {

            if (isSendMessage(message.getMessageType())) {

                DeviceCmdLogReqMsg deviceCmdLogReqMsg = DeviceCmdLogReqMsg.builder()
                        .cmd(String.format("%04x", message.getMessageType()))
                        .serialNo(message.getFrameSerialNo())
                        .body(message.getBody()).upDown(cmdLogType)
                        .updateTs(System.currentTimeMillis())
                        .originHex(message.getOriginHex())
                        .cmdDesc(DeviceOpConstantEnum.getOptEnum(message.getMessageType()).getOpName())
                        .secret(secretKey)
                        .build();
                sessionContext.getDeviceService().sendCmdLogData(deviceCmdLogReqMsg, session, message.getGunNo(),
                        message.getMessageType(), message.getTs());
            }
        } catch (Exception e) {
            log.error("发送交互日志失败: deviceId={}", session.getDeviceId(), e);
        }
    }

    private Boolean isSendMessage(Integer messageType){
        if(messageType.equals(DeviceOpConstantEnum.HEARTBEAT.getOpCode()) ||
                messageType.equals(DeviceOpConstantEnum.HEARTBEAT_RESP.getOpCode())){
            return false;
        }

        return true;
    }

    /**
     * 写入设备编号
     * @param byteBuf byteBuf
     * @param deviceNo deviceNo
     */
    protected void writeDeviceNo(ByteBuf byteBuf, String deviceNo) {
        String formatDeviceNo = StringUtils.leftPad(deviceNo, 14, "0");
        byteBuf.writeBytes(BCD.strToBcd(formatDeviceNo));
    }

    /**
     * 写入枪编号
     * @param byteBuf byteBuf
     * @param gunNo gunNo
     */
    protected void writeGunNo(ByteBuf byteBuf, String gunNo) {
        byteBuf.writeBytes(BCD.strToBcd(gunNo));
    }

    /**
     * 写入orderNo
     * @param byteBuf byteBuf
     * @param orderNo orderNo
     */
    protected void writeOrderNo(ByteBuf byteBuf, String orderNo) {
        byteBuf.writeBytes(HexUtil.decodeHex(String.format("%32s", orderNo).replace(' ', '0')));
    }

    protected void writeBCD(ByteBuf byteBuf, String value, Integer len) {
        String formatDeviceNo = StringUtils.leftPad(value, len, "0");
        byteBuf.writeBytes(BCD.strToBcd(formatDeviceNo));
    }

    public void writeChargeStandOut(ByteBuf byteBuf,
                                    String deviceNo, Integer chargeStandardModelNo,
                                    byte feeSize, List<YkcChargeStandardFeeOut> feeOutList,
                                    Byte lossCalculationRatio,
                                    List<Integer> YkcTimeRangeFeeRate) {


        writeDeviceNo(byteBuf, deviceNo);
        // 固定值：0100
        byteBuf.writeBytes(BCD.strToBcd(String.format("%04d", chargeStandardModelNo)));
        // 费率数量
        byteBuf.writeByte(feeSize);
        // 费用精确到五位小数
        for (YkcChargeStandardFeeOut feeOut :
                feeOutList) {
            byteBuf.writeIntLE(formatFee(feeOut.getElectricityFee()));
            byteBuf.writeIntLE(formatFee(feeOut.getServiceFee()));
        }
        // 计损比例
        byteBuf.writeByte(lossCalculationRatio);
        // 48个时段费率号
        for (int i = 0; i < YkcTimeRangeFeeRate.size(); i++) {
            byteBuf.writeByte((YkcTimeRangeFeeRate.get(i) & 0xFF));
        }
    }

    private Integer formatFee(BigDecimal value) {
        BigDecimal bigDecimal = value.setScale(5, RoundingMode.HALF_UP);
        Integer integerVal = Integer.valueOf(String.valueOf(bigDecimal).replace(".", ""));
        return integerVal;
    }

    public void processResult(SessionContext sessionContext, Boolean success, String deviceNo, Integer frameSerialNo,  String errorMsg) throws Exception {
        PushResult pushResult = null;
        if(success){
            pushResult = PushResult.success();
        }else{
            pushResult = PushResult.fail(errorMsg);
        }
        SyncMsgTemplate syncMsgTemple = sessionContext.getDeviceService().getSyncMsgTemple();
        syncMsgTemple.backMsg(deviceNo, frameSerialNo, pushResult);
    }

    /**
     * 完成数据解析后处理
     * @param ctx channel
     * @param message message
     */
    public abstract void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, IN message) throws Exception;

    /**
     * java对象转成ByteBuf
     * @param byteBuf byteBuf
     * @param out out
     */
    public abstract void write(ByteBuf byteBuf, OUT out);

}
