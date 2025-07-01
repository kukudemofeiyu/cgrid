package com.things.cgomp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.OrderPaySuccessReqMsg;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.dto.AppOrderDiscountDTO;
import com.things.cgomp.order.api.dto.OrderDiscountDTO;
import com.things.cgomp.order.api.dto.PayWalletMoneyDTO;
import com.things.cgomp.order.api.vo.*;
import com.things.cgomp.order.dto.OrderAppPageDTO;
import com.things.cgomp.order.dto.OrderPageDTO;
import com.things.cgomp.order.dto.OrderStatisticsQueryDTO;
import com.things.cgomp.order.vo.OrderVo;

import java.util.List;

/**
 * <p>
 * 充电订单表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
public interface IOrderInfoService extends IService<OrderInfo> {

    Long selectLatestUserId(String vin);

    PageInfo<OrderVo> selectPage(
            OrderPageDTO pageDTO
    );

    String generateSn(
            Long portId,
            Long insertTime
    );

    String selectOrderSn(
           Long portId
    );

    List<OrderInfo> selectOrdersBySns(
            List<String> sns
    );

    void endCharging(
            EndChargingReqMsg reqMsg
    );

    void confirmTradingRecord(TradingRecordConfirmReqMsg reqMsg);

    void drawGun(DrawGunReqMsg reqMsg);

    OrderDetailVo selectOrderDetail(
            Long id
    );

    Long addOrder(
            AddOrderDTO orderDTO
    );

    OrderInfo selectOrderBySn(
            String sn
    );

    Boolean isChargingOrderByUserId(Long userId);

    PageInfo<OrderAppVO> selectAppPage(OrderAppPageDTO pageDTO);

    OrderAppDetailVO selectAppOrderDetail(Long id);

    List<OrderAppLogVO> selectAppOrderLogs(Long orderId);

    Boolean isPayingOrderByUserId(Long userId);

    void updateOrderPayStatus(
            OrderPaySuccessReqMsg reqMsg
    );

    void savePayOrderId(
            List<Long> orderIds,
            String payOrderId
    );

    void payWalletMoney(
            PayWalletMoneyDTO payWalletMoneyDTO
    );

    OrderDiscountVo getOrderDiscount(
            OrderDiscountDTO orderDiscountDTO
    );

    AppOrderDiscountVo getAppOrderDiscount(
            AppOrderDiscountDTO orderDiscountDTO
    );

    OrderInfo selectUnsettledOrder(Long portId, String orderSn);

    OrderStatisticsData selectStatisticsTotalData(OrderStatisticsQueryDTO queryDTO);

    List<OrderStatisticsData> selectStatisticsDeviceData(OrderStatisticsQueryDTO queryDTO);

    List<OrderTrendDateData> selectDateTrendData(TrendQueryDTO queryDTO);

    List<OrderTrendHourData> selectHourTrendData(TrendQueryDTO queryDTO);

    List<OrderTrendUserData> selectUserTrendData(TrendQueryDTO queryDTO);

    List<DeviceOrderStatisticsData> selectDeviceOrderStatistics(OrderStatisticsQueryDTO queryDTO);

    List<OrderInfo> selectSubOrder(Long orderId);

    List<OrderInfo> selectProcessLossOrders();

    OrderInfo selectChargeOrderByTradeNo(String tradeNo);

    OrderInfo selectChargeOrderByOrderNo(String orderNo);
}
