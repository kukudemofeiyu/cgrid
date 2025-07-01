package com.things.cgomp.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.order.api.domain.OrderShareholders;

/**
 * @author things
 * @date 2025/2/28
 */
public interface IOrderShareholdersService extends IService<OrderShareholders> {

    OrderShareholders selectByUserId(Long userId);
}
