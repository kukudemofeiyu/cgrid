package com.things.cgomp.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.app.api.domain.AppRechargeOrder;
import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;

import java.util.List;

/**
* @description 针对表【app_recharge_order(充值订单表)】的数据库操作Mapper
* @createDate 2025-04-01 10:26:46
* @Entity com.things.cgomp.app.domain.AppRechargeOrder
*/
public interface AppRechargeOrderMapper extends BaseMapper<AppRechargeOrder> {

    AppRechargeOrder selectOrderByOrderNo(String orderNo);

    int updateStatus(AppRechargeOrder orderOld);

    List<AppRechargeTrendData> selectTrendData(TrendQueryDTO queryDTO);
}




