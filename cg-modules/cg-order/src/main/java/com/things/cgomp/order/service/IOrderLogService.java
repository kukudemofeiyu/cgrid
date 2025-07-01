package com.things.cgomp.order.service;

import com.things.cgomp.order.api.domain.OrderLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.order.api.vo.OrderAppLogVO;
import com.things.cgomp.order.enums.OrderLogEnum;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单日志表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-12
 */
public interface IOrderLogService extends IService<OrderLog> {

    List<OrderLog> selectLogs(
         Long orderId
    );

    void saveLog(
            Long orderId,
            LocalDateTime creatTime,
            OrderLogEnum orderLogEnum
    );

    List<OrderAppLogVO> selectAppLogs(Long orderId);
}
