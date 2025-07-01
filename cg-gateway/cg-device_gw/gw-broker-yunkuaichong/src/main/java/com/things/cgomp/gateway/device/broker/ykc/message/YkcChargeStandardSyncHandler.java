package com.things.cgomp.gateway.device.broker.ykc.message;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcChargeStandardIn;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeStandardFeeOut;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeStandardOut;
import com.things.cgomp.gateway.device.broker.ykc.utils.FeeModelConvert;
import com.things.cgomp.device.api.dto.RuleDTO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 计费标准同步处理器
 */
@Slf4j
@Component
public class YkcChargeStandardSyncHandler extends YkcAbstractMessageHandler<YkcChargeStandardIn, YkcChargeStandardOut> {

    @Override
    public void processRead(ChannelHandlerContext ctx, SessionContext sessionContext, Session session, YkcChargeStandardIn message) throws Exception {
        log.info("计费模型请求, deviceNo:{}", message.getDeviceNo());

        try {

            RuleDTO ruleData = requestDataService.getPayRule(session.getDeviceId());

            List<YkcChargeStandardFeeOut> standardFeeOuts = FeeModelConvert.getYkcChargeStandardFeeOuts(ruleData.getFees());

            List<Integer> YkcTimeRangeFeeRate = FeeModelConvert.getTimeRangeFeeRate(ruleData.getTimes());

            YkcChargeStandardOut ykcChargeStandardOut = YkcChargeStandardOut.of(message.getFrameSerialNo(),
                    message.getDeviceNo(),
                    YkcTimeRangeFeeRate,
                    standardFeeOuts);
            sendAckMsg(sessionContext, ctx.channel(), session, ykcChargeStandardOut);
        } catch (Exception e) {
            log.error("计费模型请求处理失败, deviceNo:{}", session.getSn(), e);
        }

    }


    @Override
    public void write(ByteBuf byteBuf, YkcChargeStandardOut ykcChargeStandardOut) {
        log.info("同步到设备的计费规则: {}", ykcChargeStandardOut);
        writeChargeStandOut(byteBuf, ykcChargeStandardOut.getDeviceNo(),
                ykcChargeStandardOut.getChargeStandardModelNo(),
                ykcChargeStandardOut.getFeeSize(), ykcChargeStandardOut.getFeeOutList(),
                ykcChargeStandardOut.getLossCalculationRatio(), ykcChargeStandardOut.getYkcTimeRangeFeeRate());
    }



}
