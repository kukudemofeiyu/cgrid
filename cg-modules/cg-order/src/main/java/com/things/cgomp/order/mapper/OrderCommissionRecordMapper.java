package com.things.cgomp.order.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.order.api.domain.CommissionRecordStatisticsData;
import com.things.cgomp.order.api.domain.OrderCommissionRecord;
import com.things.cgomp.order.api.domain.CommissionRecordData;
import com.things.cgomp.order.dto.CommissionRecordQueryDTO;
import com.things.cgomp.order.dto.CommissionRecordStatisticsQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface OrderCommissionRecordMapper extends BaseMapperX<OrderCommissionRecord> {

    CommissionRecordData selectTotalData(CommissionRecordQueryDTO queryDTO);

    List<CommissionRecordData> selectOrderRecordList(CommissionRecordQueryDTO queryDTO);

    List<CommissionRecordStatisticsData> selectStatisticsListByOperator(CommissionRecordStatisticsQueryDTO queryDTO);

    List<CommissionRecordStatisticsData> selectStatisticsListBySite(CommissionRecordStatisticsQueryDTO queryDTO);
}
