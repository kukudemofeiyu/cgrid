package com.things.cgomp.order.service.impl.order;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.IsFeeEnum;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.ErrorCodeConstants;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.IOrderLogService;
import com.things.cgomp.order.service.ISiteOccupyFeeService;
import com.things.cgomp.order.service.impl.OrderSerialCodeService;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.device.api.dto.RuleDTO;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.math.BigDecimal;

public abstract class OrderState {

    @Resource
    protected ApplicationContext applicationContext;
    @Resource
    protected IOrderLogService orderLogService;
    @Resource
    private RemoteDeviceService remoteDeviceService;
    @Resource
    protected RedisService redisService;
    @Resource
    private RemoteRuleService remoteRuleService;
    @Resource
    protected ISiteOccupyFeeService siteOccupyFeeService;

    protected BigDecimal getPayAmount(
            OrderInfo orderInfo,
            BigDecimal amount
    ) {
        if(IsFeeEnum.FREE.getType().equals(orderInfo.getIsFee())){
            return new BigDecimal(0);
        }

        return amount;
    }

    protected RuleDTO getRuleDTO(
            Long ruleId,
            Integer payModelId
    ) {
        R<RuleDTO> ruleR = remoteRuleService.selectRule(
                ruleId,
                payModelId
        );
        if(!ruleR.success()){
            throw new ServiceException(ErrorCodeConstants.FAILED_TO_QUERY_RULE_DATA);
        }
        return ruleR.getData();
    }

    protected DeviceInfo getDeviceInfo(Long deviceId) {
        R<DeviceInfo> deviceInfoR = remoteDeviceService.selectDevice(deviceId);
        if(!deviceInfoR.success()){
            throw new ServiceException(ErrorCodeConstants.FAILED_TO_QUERY_DEVICE_DATA);
        }
        return deviceInfoR.getData();
    }

    protected IOrderInfoService getOrderInfoService() {
        return applicationContext.getBean(IOrderInfoService.class);
    }

    public abstract Long addOrder(
            AddOrderDTO order
    );

    public abstract void endCharging(
            OrderInfo orderInfo,
            EndChargingReqMsg reqMsg
    );

    public abstract OrderInfo confirmTradingRecord(
            OrderInfo orderInfo,
            TradingRecordConfirmReqMsg reqMsg
    );

    public abstract void drawGun(
            OrderInfo orderInfo,
            DrawGunReqMsg reqMsg
    );

}
