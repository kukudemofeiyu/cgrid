package com.things.cgomp.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.order.mapper.OrderShareholdersMapper;
import com.things.cgomp.order.service.IOrderShareholdersService;
import org.springframework.stereotype.Service;

/**
 * @author things
 * @date 2025/3/3
 */
@Service
public class OrderShareholdersService extends ServiceImpl<OrderShareholdersMapper, OrderShareholders> implements IOrderShareholdersService {


    @Override
    public OrderShareholders selectByUserId(Long userId) {
        OrderShareholders shareholders = baseMapper.selectOne(
                OrderShareholders::getUserId, userId,
                OrderShareholders::getStatus, CommonStatus.OK.getCode()
        );
        return shareholders;
    }
}
