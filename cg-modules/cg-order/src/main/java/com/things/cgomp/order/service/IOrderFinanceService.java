package com.things.cgomp.order.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.order.api.domain.OrderFinanceData;
import com.things.cgomp.order.dto.OrderFinanceQueryDTO;

/**
 * @author things
 */
public interface IOrderFinanceService {

    /**
     * 查询订单财务总销售数据
     * @param queryDTO  查询参数
     * @return OrderFinanceData
     */
    OrderFinanceData selectTotalData(OrderFinanceQueryDTO queryDTO);

    /**
     * 分页查询订单财务数据
     * @param queryDTO 查询参数
     * @return PageInfo
     */
    PageInfo<OrderFinanceData> selectPage(OrderFinanceQueryDTO queryDTO);
}
