package com.things.cgomp.app.service.impl;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.domain.dto.OrderPageDTO;
import com.things.cgomp.app.service.OrderService;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.vo.OrderAppDetailVO;
import com.things.cgomp.order.api.vo.OrderAppLogVO;
import com.things.cgomp.order.api.vo.OrderAppVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired(required = false)
    private RemoteOrderService remoteOrderService;
    @Override
    public PageInfo<OrderAppVO> selectPage(OrderPageDTO pageDTO) {
        Long userId = SecurityUtils.getUserId();
//        Long userId =1l;
        if (userId == null || userId == 0){
            log.error("查询订单分页列表失败;用户未登录");
            throw exception(GlobalErrorCodeConstants.UNAUTHORIZED);
        }
        R<PageInfo<OrderAppVO>> pageInfoR = remoteOrderService.selectAppPage(userId,pageDTO.getOrderType(),pageDTO.getStatus(),
                pageDTO.getCurrent(), pageDTO.getPageSize(), SecurityConstants.INNER);
        if (pageInfoR == null || pageInfoR.getCode() != 200){
            log.error("查询订单分页列表失败;{}", pageInfoR==null?null:pageInfoR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return pageInfoR.getData();
    }

    @Override
    public OrderAppDetailVO selectOrderDetail(Long id) {
        R<OrderAppDetailVO> detailVO = remoteOrderService.selectAppOrderDetail(id, SecurityConstants.INNER);
        if (detailVO == null || detailVO.getCode() != 200){
            log.error("查询订单详情失败;{}", detailVO==null?null:detailVO.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return detailVO.getData();
    }

    @Override
    public List<OrderAppLogVO> selectOrderLogs(Long orderId) {
        R<List<OrderAppLogVO>> orderStatusVO = remoteOrderService.selectAppOrderLogs(orderId, SecurityConstants.INNER);
        if (orderStatusVO == null || orderStatusVO.getCode() != 200){
            log.error("查询订单状态跟踪失败;{}", orderStatusVO==null?null:orderStatusVO.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return orderStatusVO.getData();
    }

    @Override
    public OrderInfo selectOrderByOrderNo(String tradeSn) {
        R<OrderInfo> orderByTradeNo = remoteOrderService.getChargeOrderByTradeNo(tradeSn, SecurityConstants.INNER);
        if (orderByTradeNo == null || orderByTradeNo.getCode() != 200){
            log.error("根据交易流水号查询订单失败;{}", orderByTradeNo==null?null:orderByTradeNo.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return orderByTradeNo.getData();
    }

    @Override
    public List<OrderInfo> selectSubOrders(Long orderId) {
        R<List<OrderInfo>> subOrders = remoteOrderService.getSubOrder(orderId, SecurityConstants.INNER);
        if (subOrders == null || subOrders.getCode() != 200){
            log.error("根据订单ID查询子订单失败;{}", subOrders==null?null:subOrders.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return subOrders.getData();
    }

}
