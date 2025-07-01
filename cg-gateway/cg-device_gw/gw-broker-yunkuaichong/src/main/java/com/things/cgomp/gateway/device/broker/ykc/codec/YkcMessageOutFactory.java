package com.things.cgomp.gateway.device.broker.ykc.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.device.pojo.device.DeviceCommandEnum;
import com.things.cgomp.common.device.pojo.device.StartChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.push.PushInfo;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.*;
import com.things.cgomp.gateway.device.broker.ykc.utils.FeeModelConvert;
import com.things.cgomp.device.api.dto.RuleDTO;

import java.math.BigDecimal;
import java.util.List;

public final class YkcMessageOutFactory {


    public static YkcMessageOut newMessage(DeviceCommandEnum optType, PushInfo pushInfo, int frameSerialNo){

        switch (optType){
            case startCharge:
                return getStartChargeMessage(pushInfo, frameSerialNo);
            case stopCharge:
                return getStopChargeMessage(pushInfo, frameSerialNo);
            case getChargeRecord:
                return getChargeRecordGetMessage(pushInfo, frameSerialNo);
            case setChargeFeeModel:
                return getSetChargeFeeModel(pushInfo, frameSerialNo);
            default:
                throw new ServiceException(ErrorCodeConstants.NO_SUPPORT_FRAME);
        }


    }

    private static YkcMessageOut getSetChargeFeeModel(PushInfo pushInfo, int frameSerialNo) {
        RuleDTO ruleData = JSONObject.parseObject(JSON.toJSONString(pushInfo.getContext()), RuleDTO.class);
        List<YkcChargeStandardFeeOut> standardFeeOuts = FeeModelConvert.getYkcChargeStandardFeeOuts(ruleData.getFees());

        List<Integer> YkcTimeRangeFeeRate = FeeModelConvert.getTimeRangeFeeRate(ruleData.getTimes());

        YkcChargeStandardSetOut ykcChargeStandardOut = YkcChargeStandardSetOut.of(frameSerialNo,
                pushInfo.getDeviceNo(),
                YkcTimeRangeFeeRate,
                standardFeeOuts);
        ykcChargeStandardOut.setChargeStandardModelNo(ruleData.getModelId());

        return ykcChargeStandardOut;

    }

    private static YkcMessageOut getChargeRecordGetMessage(PushInfo pushInfo, int frameSerialNo) {
        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(pushInfo.getContext()));
        String orderNo = jsonObject.getString("orderSn");
        return YkcDeviceGetChargeRecordOut.of(frameSerialNo, orderNo, pushInfo.getDeviceNo(), pushInfo.getGunNo());
    }

    private static YkcMessageOut getStopChargeMessage(PushInfo pushInfo, int frameSerialNo) {
        return new YkcDeviceStopChargeOut(frameSerialNo,pushInfo.getDeviceNo(),pushInfo.getGunNo());
    }

    private static YkcDeviceStartChargeOut getStartChargeMessage(PushInfo pushInfo, int frameSerialNo) {

        StartChargingConfigDTO startChargingConfig = JSONObject.parseObject(JSON.toJSONString(pushInfo.getContext()), StartChargingConfigDTO.class);

        return YkcDeviceStartChargeOut.of(frameSerialNo,
                startChargingConfig.getOrderNo(),
                pushInfo.getDeviceNo(),
                pushInfo.getGunNo(),
                startChargingConfig.getCardNo(),
                formatBalance(startChargingConfig.getAccountBalance()),
                startChargingConfig.getIccid());

    }

    /**
     * 格式化余额 保留两个小数点
     */
    public static Integer formatBalance(double value) {
        // 将数值乘以100后截断，再除以100
        double truncated = Math.floor(value * 100) / 100;
        String format = String.format("%.2f", truncated);
        return Integer.valueOf(format.replace(".", ""));
    }

    public static void main(String[] args) {
        System.out.println(formatBalance(1.456));
        System.out.println(formatBalance(1456d));
        System.out.println(formatBalance(1.4));
    }




}
