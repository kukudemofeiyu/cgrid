package com.things.cgomp.app.service;

import com.things.cgomp.app.domain.dto.OrderPayDTO;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.dto.PayWalletMoneyDTO;

import javax.servlet.http.HttpServletRequest;

public interface PayService {

    Object Pay(OrderPayDTO orderPayDTO);
    void handleWxpayCallback(String xmlData, HttpServletRequest request);

    Integer wechatOrderQuery(String orderNo);

    R<?> walletPay(PayWalletMoneyDTO orderPayDTO);

    Boolean refund(String orderNo);
}
