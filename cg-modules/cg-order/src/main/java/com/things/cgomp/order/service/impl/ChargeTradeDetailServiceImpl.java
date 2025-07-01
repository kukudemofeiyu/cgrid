package com.things.cgomp.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.mq.message.ChargeFeeRate;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.order.domain.ChargeTradeDetail;
import com.things.cgomp.order.mapper.ChargeTradeDetailMapper;
import com.things.cgomp.order.service.IChargeTradeDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.device.api.dto.RuleFeeDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * <p>
 * 订单充电明细表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-15
 */
@Service
public class ChargeTradeDetailServiceImpl extends ServiceImpl<ChargeTradeDetailMapper, ChargeTradeDetail>
        implements IChargeTradeDetailService {

    @Override
    public void saveChargeTradeDetails(
            Long orderId,
            List<ChargeTradeDetail> chargeTradeDetails
    ) {
        chargeTradeDetails.stream()
                .peek(chargeTradeDetail -> chargeTradeDetail.setOrderId(orderId))
                .forEach(chargeTradeDetail -> baseMapper.insert(chargeTradeDetail));
    }

    @Override
    public List<ChargeTradeDetail> getChargeTradeDetails(
            TradingRecordConfirmReqMsg reqMsg,
            String ptRule
    ) {
        RuleDTO ruleDTO = JSON.parseObject(ptRule, RuleDTO.class);

        return getChargeTradeDetails(
                reqMsg,
                ruleDTO
        );
    }

    @Override
    public List<ChargeTradeDetail> getChargeTradeDetails(
            TradingRecordConfirmReqMsg reqMsg,
            RuleDTO ruleDTO
    ) {
        List<ChargeFeeRate> deviceFeeRates = reqMsg.getFeeRates();
        if(CollectionUtils.isEmpty(deviceFeeRates)){
            return new ArrayList<>();
        }

        Map<LocalTime, RuleFeeDTO> halfHourFeeMap = buildHalfHourFeeMap(ruleDTO);

        LocalDateTime startTime = DateUtils.toLocalDateTime(reqMsg.getStartTime());
        LocalDateTime endTime = DateUtils.toLocalDateTime(reqMsg.getEndTime());


        LocalDateTime currentStartTime = startTime;
        List<ChargeTradeDetail> details = new ArrayList<>();
        for (ChargeFeeRate deviceFeeRate : deviceFeeRates) {
            LocalDateTime startTimeHalfHour = DateUtils.getPreviousHalfOrFullHour(currentStartTime);

            LocalDateTime currentEndTime = startTimeHalfHour.plusMinutes(30);
            if(currentEndTime.isAfter(endTime)){
                currentEndTime = endTime;
            }

            RuleFeeDTO ruleFeeDTO = halfHourFeeMap.get(startTimeHalfHour.toLocalTime());

            ChargeTradeDetail chargeTradeDetail = getChargeTradeDetail(
                    ruleFeeDTO,
                    deviceFeeRate,
                    currentStartTime,
                    currentEndTime
            );

            details.add(chargeTradeDetail);

            currentStartTime = currentEndTime;
        }
        return details;
    }

    private Map<LocalTime, RuleFeeDTO> buildHalfHourFeeMap(RuleDTO ruleDTO) {
        if(ruleDTO == null){
            return new HashMap<>();
        }
        return ruleDTO.buildHalfHourFeeMap();
    }

    private ChargeTradeDetail getChargeTradeDetail(
            RuleFeeDTO ruleFeeDTO ,
            ChargeFeeRate deviceFeeRate,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        ChargeTradeDetail chargeTradeDetail = new ChargeTradeDetail()
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setDeviceUnitPrice(deviceFeeRate.getUnitPrice())
                .setEnergy(deviceFeeRate.getEnergy())
                .setLoseEnergy(deviceFeeRate.getLoseEnergy())
                .setDeviceAmount(deviceFeeRate.getAmount());

        if(ruleFeeDTO != null){
            chargeTradeDetail.setPtUnitService(ruleFeeDTO.getServiceCharge())
                    .setPtUnitElec(ruleFeeDTO.getElectrovalence())
                    .setPtUnitPrice();
        }
        return chargeTradeDetail;
    }

}
