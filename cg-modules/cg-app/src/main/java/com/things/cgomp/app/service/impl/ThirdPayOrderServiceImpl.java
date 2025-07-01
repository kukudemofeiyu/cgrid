package com.things.cgomp.app.service.impl;

import cn.hutool.json.JSONUtil;
import com.things.cgomp.app.domain.AppThirdPayOrder;
import com.things.cgomp.app.enums.ErrorCodeConstants;
import com.things.cgomp.app.mapper.AppThirdPayOrderMapper;
import com.things.cgomp.app.service.ThirdPayOrderService;
import com.things.cgomp.common.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

@Slf4j
@Service
public class ThirdPayOrderServiceImpl implements ThirdPayOrderService {
   @Resource
    private AppThirdPayOrderMapper appThirdPayOrderMapper;

    @Override
    public void createOrder(String orderNo, String thirdPartyOrderId, Integer amount, Integer payType, String appId,
                            String merchantId, Object config , List<Long> orderNos) {
    //获取用户ID
        Long userId = SecurityUtils.getUserId();
        if (userId ==0){
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
    //根据规则生成唯一订单号
        AppThirdPayOrder appThirdPayOrder = new AppThirdPayOrder();
        appThirdPayOrder.setAppid(appId);
        appThirdPayOrder.setMchid(merchantId);
        appThirdPayOrder.setId(orderNo);
        appThirdPayOrder.setUserId(userId);
        appThirdPayOrder.setOrderSource(payType);
        appThirdPayOrder.setAmount(amount);
        appThirdPayOrder.setThirdPartyOrderId(thirdPartyOrderId);
        appThirdPayOrder.setAttach(JSONUtil.toJsonStr(config));
        appThirdPayOrder.setChargeOrderSn(JSONUtil.toJsonStr(orderNos));
        appThirdPayOrderMapper.insert(appThirdPayOrder);
    }

    @Override
    public AppThirdPayOrder selectOrderByOrderNo(String orderNo) {
        return appThirdPayOrderMapper.selectOrderByOrderNo(orderNo);
    }

    @Override
    public boolean updateStatus(AppThirdPayOrder orderOld) {
        int i = appThirdPayOrderMapper.updateStatus(orderOld);
        if (i == 0){
            return false;
        }
        return true;
    }

    @Override
    public AppThirdPayOrder selectOrderById(String outTradeNo) {
        return appThirdPayOrderMapper.selectById(outTradeNo);
    }


}
