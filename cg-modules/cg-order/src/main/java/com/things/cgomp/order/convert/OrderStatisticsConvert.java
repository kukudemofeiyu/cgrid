package com.things.cgomp.order.convert;

import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.order.api.domain.OrderStatisticsData;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import com.things.cgomp.order.domain.ManageDeviceSiteData;
import com.things.cgomp.order.domain.ManageTotalData;
import com.things.cgomp.order.dto.CommissionRecordQueryDTO;
import com.things.cgomp.order.dto.ManageDataReqDTO;
import com.things.cgomp.order.dto.OrderStatisticsQueryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface OrderStatisticsConvert {

    OrderStatisticsConvert INSTANCE = Mappers.getMapper(OrderStatisticsConvert.class);

    @Mapping(source = "pileSn", target = "deviceSn")
    OrderStatisticsQueryDTO convertQueryDTO(ManageDataReqDTO bean);

    OrderStatisticsQueryDTO convertQueryDTO(OrderStatisticsReq bean);

    CommissionRecordQueryDTO convertCommissionQueryDTO(OrderStatisticsReq bean);

    ManageTotalData convertManageTotalResp(OrderStatisticsData bean);

    @Mappings({
            @Mapping(target = "chargeTime", ignore = true),
            @Mapping(source = "chargeTime", target = "chargeHour"),
            @Mapping(source = "orderAmount", target = "totalFee")
    })
    ManageDeviceSiteData convertMangeDeviceResp(OrderStatisticsData bean);

    TrendQueryDTO convertTrendQuery(OrderStatisticsReq bean);

    List<ManageDeviceSiteData> convertMangeDeviceRespList(List<OrderStatisticsData> list);
}
