package com.things.cgomp.order.convert;

import com.things.cgomp.order.api.domain.OrderFinanceData;
import com.things.cgomp.order.api.domain.OrderFinanceTotalData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author things
 */
@Mapper
public interface OrderFinanceConvert {

    OrderFinanceConvert INSTANCE = Mappers.getMapper(OrderFinanceConvert.class);

    @Mappings({
            @Mapping(source = "daySales", target = "daySalesTotal"),
            @Mapping(source = "monthSales", target = "monthSalesTotal"),
            @Mapping(source = "yearSales", target = "yearSalesTotal")
    })
    OrderFinanceTotalData convertTotal(OrderFinanceData bean);
}
