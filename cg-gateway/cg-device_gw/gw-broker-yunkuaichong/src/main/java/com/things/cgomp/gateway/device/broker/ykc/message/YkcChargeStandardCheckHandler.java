package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeStandardCheckIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeStandardCheckOut;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSession;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 计费标准验证器
 */
@Slf4j
@Component
public class YkcChargeStandardCheckHandler extends YkcAbstractMessageHandler<YkcChargeStandardCheckIn, YkcChargeStandardCheckOut>{

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcChargeStandardCheckIn message) throws Exception {
        log.info("计费模型验证请求, message:{}", message    );

        YkcSession ykcSession = (YkcSession) session;

        try{
            YkcChargeStandardCheckOut ykcChargeStandardCheckOut = new YkcChargeStandardCheckOut(
                    message.getFrameSerialNo(),
                    message.getDeviceNo(),
                    message.getChargeStandardModelNo(),
                    Boolean.TRUE);
            DeviceInfo deviceInfo = sessionContext.getDeviceService().getDeviceInfoById(session.getDeviceId());
            Integer latestRuleId = deviceInfo.getPayModelId();
            Long payRuleId = deviceInfo.getPayRuleId();
            Integer currentPayModelId = deviceInfo.getCurrentPayModelId();
            Long currentPayRuleId = deviceInfo.getCurrentPayRuleId();
            //如果计费规则不一致
            if (!latestRuleId.equals(message.getChargeStandardModelNo())) {
                ykcChargeStandardCheckOut.setChargeStandardModelNo(latestRuleId);
                ykcChargeStandardCheckOut.setAck(Boolean.FALSE);

                if(!latestRuleId.equals(currentPayModelId) || !payRuleId.equals(currentPayRuleId) ){
                    //同步计费模型
                    ykcSession.setNeedSyncFeeModel(true);
                    ykcSession.setCurrentPayRuleId(payRuleId);
                    ykcSession.setCurrentPayModeId(latestRuleId);

                }

            }

            sendAckMsg(sessionContext, ctx.channel(), session, ykcChargeStandardCheckOut);
        }catch (Exception e){

            log.error("计费模型验证请求处理失败,  deviceNo:{}", session.getSn(), e);
        }

    }

    @Override
    public void write(ByteBuf byteBuf, YkcChargeStandardCheckOut ykcChargeStandardCheckOut) {
        writeDeviceNo(byteBuf, ykcChargeStandardCheckOut.getDeviceNo());
        // 计费模式编号
        writeBCD(byteBuf, String.valueOf(ykcChargeStandardCheckOut.getChargeStandardModelNo()), 4);
        // 验证结果
        byteBuf.writeByte(ykcChargeStandardCheckOut.getAck() ? 0x00 : 0x01);
    }
}
