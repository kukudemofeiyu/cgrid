package com.things.cgomp.app.mapper;

import com.things.cgomp.app.domain.AppThirdPayOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @description 针对表【app_third_pay_order(第三方支付订单表)】的数据库操作Mapper
* @createDate 2025-03-19 16:19:26
* @Entity com.things.cgomp.app.domain.AppThirdPayOrder
*/
public interface AppThirdPayOrderMapper extends BaseMapper<AppThirdPayOrder> {

    AppThirdPayOrder selectOrderByOrderNo(String orderNo);

    int updateStatus(AppThirdPayOrder orderOld);
}




