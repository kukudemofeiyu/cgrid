package com.things.cgomp.app.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.domain.dto.OrderPageDTO;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.vo.OrderAppDetailVO;
import com.things.cgomp.order.api.vo.OrderAppLogVO;
import com.things.cgomp.order.api.vo.OrderAppVO;

import java.util.List;

public interface OrderService {
    PageInfo<OrderAppVO> selectPage(OrderPageDTO pageDTO);

    OrderAppDetailVO selectOrderDetail(Long id);

    List<OrderAppLogVO> selectOrderLogs(Long orderId);

    OrderInfo selectOrderByOrderNo(String tradeSn);
    List<OrderInfo> selectSubOrders(Long orderId);
}
