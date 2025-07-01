package com.things.cgomp.order.mapper;

import com.things.cgomp.order.api.domain.OrderFinanceData;
import com.things.cgomp.order.dto.OrderFinanceQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface OrderFinanceMapper {

    OrderFinanceData selectTotalData(OrderFinanceQueryDTO queryDTO);

    List<OrderFinanceData> selectListByOperator(OrderFinanceQueryDTO queryDTO);

    List<OrderFinanceData> selectListBySite(OrderFinanceQueryDTO queryDTO);

    List<OrderFinanceData> selectListByPile(OrderFinanceQueryDTO queryDTO);
}
