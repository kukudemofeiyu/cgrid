package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.app.api.domain.AppRechargeOrder;
import com.things.cgomp.app.api.enums.OrderSource;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.domain.dto.AppRechargeOrderDTO;
import com.things.cgomp.system.domain.vo.AppRechargeOrderVO;
import com.things.cgomp.system.mapper.AppRechargeOrderMapper;
import com.things.cgomp.system.service.IAppRechargeOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AppRechargeOrderServiceImpl  extends ServiceImpl<AppRechargeOrderMapper, AppRechargeOrder> implements IAppRechargeOrderService {

    @Override
    public String addRechargeOrder(Long userId, BigDecimal amount, BigDecimal payAmount, BigDecimal discountAmount) {
        //根据规则生成唯一订单号
        String orderNo = generateOrderNo(userId);
        AppRechargeOrder appRechargeOrder = new AppRechargeOrder();
        appRechargeOrder.setId(orderNo);
        appRechargeOrder.setUserId(userId);
        appRechargeOrder.setType(OrderSource.SYSTEM.getCode());
        appRechargeOrder.setAmount(amount);
        appRechargeOrder.setPayAmount(payAmount);
        appRechargeOrder.setDiscountAmount(discountAmount);
        appRechargeOrder.setThirdPartyOrderId(orderNo);
        appRechargeOrder.setStatus(1);
        appRechargeOrder.setPayTime(new Date());
        baseMapper.insert(appRechargeOrder);
        return orderNo;
    }

    @Override
    public List<AppRechargeOrderVO> selectOrderList(AppRechargeOrderDTO req) {
       return baseMapper.selectOrderList(req);

    }

    private String generateOrderNo(Long userId) {
        // 格式化用户ID为11位，不足部分前面补零
        String formattedUserId = String.format("%011d", userId);
        // 生成随机数（0到999）
        int randomNum = (int) (Math.random() * 1000);
        // 拼接订单号
        return "RC_SY" + formattedUserId + System.currentTimeMillis() + randomNum;
    }
}
