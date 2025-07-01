package com.things.cgomp.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.app.api.domain.AppRechargeOrder;
import com.things.cgomp.system.domain.dto.AppRechargeOrderDTO;
import com.things.cgomp.system.domain.vo.AppRechargeOrderVO;

import java.util.List;

/**
* @description 针对表【app_recharge_order(充值订单表)】的数据库操作Mapper
* @createDate 2025-04-01 10:26:46
* @Entity com.things.cgomp.app.domain.AppRechargeOrder
*/
public interface AppRechargeOrderMapper extends BaseMapper<AppRechargeOrder> {

    List<AppRechargeOrderVO> selectOrderList(AppRechargeOrderDTO req);
}




