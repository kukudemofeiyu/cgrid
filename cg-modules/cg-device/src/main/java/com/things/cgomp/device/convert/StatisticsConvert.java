package com.things.cgomp.device.convert;

import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.device.dto.statistics.*;
import com.things.cgomp.device.dto.statistics.total.TotalDecimalData;
import com.things.cgomp.device.dto.statistics.total.TotalLongData;
import com.things.cgomp.device.dto.statistics.total.trend.TotalDecimalTrend;
import com.things.cgomp.device.dto.statistics.total.trend.TotalLongTrend;
import com.things.cgomp.order.api.domain.OrderStatisticsData;
import com.things.cgomp.order.api.domain.OrderTrendDateData;
import com.things.cgomp.order.api.domain.OrderTrendHourData;
import com.things.cgomp.order.api.domain.OrderTrendUserData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author thigns
 */
@Mapper
public interface StatisticsConvert {

    StatisticsConvert INSTANCE = Mappers.getMapper(StatisticsConvert.class);

    @Mappings({
            @Mapping(target = "chargeTime", ignore = true),
            @Mapping(source = "chargeTime", target = "chargeHour"),
            @Mapping(source = "chargeFee", target = "chargeAmount"),
            @Mapping(source = "serviceFee", target = "serviceAmount"),
            @Mapping(source = "consumeElectricity", target = "chargeElectricity")
    })
    StatisticsSiteDataResp convertSiteTotal(OrderStatisticsData bean);

    @Mappings({
            @Mapping(source = "orderAmount", target = "chargeAmount"),
            @Mapping(source = "consumeElectricity", target = "chargeElectricity")
    })
    StatisticsOrderTrendDateResp convertDateTrend(OrderTrendDateData bean);

    @Mappings({
            @Mapping(source = "orderAmount", target = "chargeAmount"),
            @Mapping(source = "consumeElectricity", target = "chargeElectricity")
    })
    StatisticsOrderTrendHourResp convertHourTrend(OrderTrendHourData bean);

    @Mappings({
            @Mapping(source = "orderAmount", target = "chargeAmount"),
            @Mapping(source = "consumeElectricity", target = "chargeElectricity"),
            @Mapping(source = "hour", target = "date")
    })
    StatisticsOrderTrendDateResp convertHourTrend2(OrderTrendHourData bean);

    StatisticsOrderTrendUserResp convertUserTrend(OrderTrendUserData bean);

    StatisticsRechargeTrendResp convertRechargeTrend(AppRechargeTrendData bean);

    StatisticsManageDataResp convertManage(OrderStatisticsData bean);

    List<StatisticsOrderTrendDateResp> convertDateTrendList(List<OrderTrendDateData> list);

    List<StatisticsOrderTrendHourResp> convertHourTrendList(List<OrderTrendHourData> list);

    List<StatisticsOrderTrendDateResp> convertHourTrendList2(List<OrderTrendHourData> list);

    List<StatisticsOrderTrendUserResp> convertUserTrendList(List<OrderTrendUserData> list);

    List<StatisticsRechargeTrendResp> convertRechargeTrendList(List<AppRechargeTrendData> list);

    default StatisticsTotalDataResp convertTotal(OrderStatisticsData bean) {
        StatisticsTotalDataResp resp = new StatisticsTotalDataResp();
        if (bean == null) {
            return resp;
        }
        resp.setTotalAmount(new TotalDecimalTrend(bean.getOrderAmount()));
        resp.setTotalCount(new TotalLongTrend(bean.getChargeCount()));
        resp.setTotalElectricity(new TotalDecimalTrend(bean.getConsumeElectricity()));
        resp.setTotalTime(new TotalDecimalTrend(bean.getChargeTime()));
        return resp;
    }

    default ChargeGridStatisticsTotalDataResp convertDeviceTotal(OrderStatisticsData reqData, OrderStatisticsData totalData) {
        ChargeGridStatisticsTotalDataResp resp = new ChargeGridStatisticsTotalDataResp();
        if (reqData == null || totalData == null) {
            return resp;
        }
        resp.setTotalIncome(new TotalDecimalData(reqData.getTotalIncome(), totalData.getTotalIncome()));
        resp.setRealIncome(new TotalDecimalData(reqData.getRealIncome(), totalData.getRealIncome()));
        resp.setRefundAmount(new TotalDecimalData(reqData.getRefundAmount(), totalData.getRefundAmount()));
        resp.setChargeFee(new TotalDecimalData(reqData.getChargeFee(), totalData.getChargeFee()));
        resp.setServiceFee(new TotalDecimalData(reqData.getServiceFee(), totalData.getServiceFee()));
        // 目前订单数据量只计算充电订单，即充电次数
        resp.setOrderQuantity(new TotalLongData(reqData.getChargeCount(), totalData.getChargeCount()));
        resp.setChargeCount(new TotalLongData(reqData.getChargeCount(), totalData.getChargeCount()));
        resp.setChargeTime(new TotalDecimalData(reqData.getChargeTime(), totalData.getChargeTime()));
        resp.setTotalElectricity(new TotalDecimalData(reqData.getConsumeElectricity(), totalData.getConsumeElectricity()));
        return resp;
    }
}
