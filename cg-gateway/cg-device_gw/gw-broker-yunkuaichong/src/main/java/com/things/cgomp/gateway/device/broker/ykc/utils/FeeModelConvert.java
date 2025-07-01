package com.things.cgomp.gateway.device.broker.ykc.utils;

import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcChargeStandardFeeOut;
import com.things.cgomp.device.api.dto.RuleFeeDTO;
import com.things.cgomp.device.api.dto.RuleTimeDTO;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FeeModelConvert {

    /**
     * 转换成费率
     * @param ruleDataFees
     * @return
     */
    public static List<YkcChargeStandardFeeOut> getYkcChargeStandardFeeOuts(List<RuleFeeDTO> ruleDataFees) {
        List<YkcChargeStandardFeeOut> standardFeeOuts = new ArrayList<>(ruleDataFees.size());
        YkcChargeStandardFeeOut ykcChargeStandardFeeOut;
        for (RuleFeeDTO ruleFeeDTO :
                ruleDataFees) {
            ykcChargeStandardFeeOut = new YkcChargeStandardFeeOut();
            ykcChargeStandardFeeOut.setFeeNo(ruleFeeDTO.getType());
            ykcChargeStandardFeeOut.setServiceFee(ruleFeeDTO.getServiceCharge());
            ykcChargeStandardFeeOut.setElectricityFee(ruleFeeDTO.getElectrovalence());
            standardFeeOuts.add(ykcChargeStandardFeeOut);
        }
        return standardFeeOuts;
    }

    /**
     * 转换成费率号
     * @return
     */
    public static List<Integer> getTimeRangeFeeRate(List<RuleTimeDTO> timeRanges) {
        List<Integer> YkcTimeRangeFeeRate = new ArrayList<>(48);

        //按开始时间排序
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        timeRanges.sort(Comparator.comparing(timeRange -> LocalTime.parse(timeRange.getStartTime(), formatter)));

        for (int i = 0; i < timeRanges.size(); i++) {
            RuleTimeDTO ruleTimeDTO = timeRanges.get(i);
            Integer type = ruleTimeDTO.getType();
            LocalTime startTime = LocalTime.parse(ruleTimeDTO.getStartTime(), formatter);
            LocalTime endTime = LocalTime.parse(ruleTimeDTO.getEndTime(), formatter);
            //最后一个时段
            long loopNum = 0;
            if (i == timeRanges.size() - 1) {
                loopNum = (24 * 60 * 60 - startTime.toSecondOfDay()) / (30 * 60);
            } else {
                loopNum = Duration.between(startTime, endTime).getSeconds() / (30 * 60);
            }

            for (int j = 0; j < loopNum; j++) {
                YkcTimeRangeFeeRate.add(type + 1);
            }
        }
        return YkcTimeRangeFeeRate;
    }

}
