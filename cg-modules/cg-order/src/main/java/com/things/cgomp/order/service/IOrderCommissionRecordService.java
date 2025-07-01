package com.things.cgomp.order.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.order.api.domain.CommissionRecordStatisticsData;
import com.things.cgomp.order.api.domain.OrderCommissionRecord;
import com.things.cgomp.order.api.domain.CommissionRecordData;
import com.things.cgomp.order.dto.CommissionRecordQueryDTO;
import com.things.cgomp.order.dto.CommissionRecordStatisticsQueryDTO;

/**
 * @author things
 */
public interface IOrderCommissionRecordService {

    /**
     * 检查订单是否存在分成记录
     * @param orderId 订单ID
     * @return true/false
     */
    boolean checkOrderExist(Long orderId);
    /**
     * 保存分成记录
     * @param record  记录
     * @return int
     */
    int addRecord(OrderCommissionRecord record);

    /**
     * 查询分成记录累计数据
     * @param queryDTO 查询参数
     * @return OrderCommissionRecordData
     */
    CommissionRecordData getTotalData(CommissionRecordQueryDTO queryDTO);

    /**
     * 分页查询分成记录明细数据
     * @param queryDTO 查询参数
     * @return PageInfo
     */
    PageInfo<CommissionRecordData> selectRecordPage(CommissionRecordQueryDTO queryDTO);

    /**
     * 分页查询分成记录统计数据
     * @param queryDTO 查询参数
     * @return PageInfo
     */
    PageInfo<CommissionRecordStatisticsData> selectRecordStatisticsPage(CommissionRecordStatisticsQueryDTO queryDTO);
}
