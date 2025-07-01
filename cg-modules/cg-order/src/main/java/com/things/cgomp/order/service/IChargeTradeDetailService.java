package com.things.cgomp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.order.domain.ChargeTradeDetail;
import com.things.cgomp.device.api.dto.RuleDTO;

import java.util.List;

/**
 * <p>
 * 订单充电明细表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-15
 */
public interface IChargeTradeDetailService extends IService<ChargeTradeDetail> {

    void saveChargeTradeDetails(
            Long orderId,
            List<ChargeTradeDetail> chargeTradeDetails
    );

    List<ChargeTradeDetail> getChargeTradeDetails(
            TradingRecordConfirmReqMsg reqMsg,
            String ptRule
    );

    List<ChargeTradeDetail> getChargeTradeDetails(
            TradingRecordConfirmReqMsg reqMsg,
            RuleDTO ruleDTO
    );
}
