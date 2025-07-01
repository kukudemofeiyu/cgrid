package com.things.cgomp.app.service;

import com.things.cgomp.app.domain.AppThirdPayOrder;

import java.util.List;

public interface ThirdPayOrderService {
    void createOrder(String orderNo, String thirdPartyOrderId, Integer amount, Integer payType, String appId, String merchantId, Object config, List<Long> orderNos);

    AppThirdPayOrder selectOrderByOrderNo(String orderNo);

    boolean updateStatus(AppThirdPayOrder orderOld);

    AppThirdPayOrder selectOrderById(String outTradeNo);
}
