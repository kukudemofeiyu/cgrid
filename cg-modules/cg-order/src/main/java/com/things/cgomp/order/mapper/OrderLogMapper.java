package com.things.cgomp.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.order.api.domain.OrderLog;
import com.things.cgomp.order.api.vo.OrderAppLogVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单日志表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-12
 */
public interface OrderLogMapper extends BaseMapper<OrderLog> {

    List<OrderLog> selectLogs(
            @Param("orderId") Long orderId
    );


    List<OrderAppLogVO> selectAppLogs(Long orderId);
}
