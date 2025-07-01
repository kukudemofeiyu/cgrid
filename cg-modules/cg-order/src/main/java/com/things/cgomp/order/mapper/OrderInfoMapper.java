package com.things.cgomp.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.vo.OrderAppVO;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.dto.OrderAppPageDTO;
import com.things.cgomp.order.dto.OrderPageDTO;
import com.things.cgomp.order.dto.OrderStatisticsQueryDTO;
import com.things.cgomp.order.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 充电订单表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    Long selectLatestUserId(
            @Param("vin") String vin
    );

    List<OrderVo> selectOrders(
            OrderPageDTO orderPageDTO
    );

    List<OrderInfo> selectChildOrders(
            @Param("parentIds") List<Long> parentIds
    );

    OrderInfo selectOrderBySn(
            @Param("sn") String sn
    );

    List<OrderInfo> selectOrdersBySns(
            @Param("sns") List<String> sns
    );

    List<OrderInfo> selectChargingOrderByUserId(@Param("userId") Long userId);

    List<OrderAppVO> selectAppOrders(OrderAppPageDTO pageDTO);

    List<OrderInfo> selectPayingOrderByUserId(@Param("userId")Long userId);

    OrderStatisticsData selectStatisticsTotalData(OrderStatisticsQueryDTO queryDTO);

    List<OrderStatisticsData> selectStatisticsDeviceData(OrderStatisticsQueryDTO queryDTO);

    List<OrderTrendDateData> selectDateTrendData(TrendQueryDTO queryDTO);

    List<OrderTrendHourData> selectHourTrendData(TrendQueryDTO queryDTO);

    List<OrderTrendUserData> selectUserTrendData(TrendQueryDTO queryDTO);

    List<DeviceOrderStatisticsData> selectDeviceOrderStatistics(OrderStatisticsQueryDTO queryDTO);

    OrderInfo selectOrderByTradeNo(String tradeNo);
}
