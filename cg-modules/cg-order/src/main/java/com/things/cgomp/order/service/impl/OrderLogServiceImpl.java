package com.things.cgomp.order.service.impl;

import com.things.cgomp.order.api.domain.OrderLog;
import com.things.cgomp.order.api.vo.OrderAppLogVO;
import com.things.cgomp.order.enums.OrderLogEnum;
import com.things.cgomp.order.mapper.OrderLogMapper;
import com.things.cgomp.order.service.IOrderLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 订单日志表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-12
 */
@Service
public class OrderLogServiceImpl extends ServiceImpl<OrderLogMapper, OrderLog> implements IOrderLogService {


    @Override
    public List<OrderLog> selectLogs(Long orderId) {
        return baseMapper.selectLogs(orderId);
    }

    @Override
    public void saveLog(
            Long orderId,
            LocalDateTime creatTime,
            OrderLogEnum orderLogEnum
    ) {
        OrderLog orderLog = new OrderLog()
                .setCreateTime(creatTime)
                .setContent(orderLogEnum.getContent())
                .setTitle(orderLogEnum.getTile())
                .setOrderId(orderId);

        baseMapper.insert(orderLog);
    }

    @Override
    public List<OrderAppLogVO> selectAppLogs(Long orderId) {
       return baseMapper.selectAppLogs(orderId);
    }
}
