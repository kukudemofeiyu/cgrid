package com.things.cgomp.system.service;

import com.things.cgomp.system.domain.dto.AppRechargeOrderDTO;
import com.things.cgomp.system.domain.vo.AppRechargeOrderVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充值订单管理
 */
public interface IAppRechargeOrderService {

    String addRechargeOrder(Long userId, BigDecimal amount,BigDecimal payAmount, BigDecimal discountAmount);

    List<AppRechargeOrderVO> selectOrderList(AppRechargeOrderDTO req);
}
