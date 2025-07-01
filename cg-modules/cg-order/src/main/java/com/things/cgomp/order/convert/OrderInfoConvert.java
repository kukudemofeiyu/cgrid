package com.things.cgomp.order.convert;

import com.things.cgomp.order.api.dto.UnSettledOrderInfo;
import com.things.cgomp.order.api.domain.OrderInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author things
 */
@Mapper
public interface OrderInfoConvert {

    OrderInfoConvert INSTANCE = Mappers.getMapper(OrderInfoConvert.class);

    UnSettledOrderInfo convertUnSettled(OrderInfo bean);
}
