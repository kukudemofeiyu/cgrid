package com.things.cgomp.order.service.impl;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.order.api.domain.CommissionRecordStatisticsData;
import com.things.cgomp.order.api.domain.OrderCommissionRecord;
import com.things.cgomp.order.api.domain.CommissionRecordData;
import com.things.cgomp.order.dto.CommissionRecordQueryDTO;
import com.things.cgomp.order.dto.CommissionRecordStatisticsQueryDTO;
import com.things.cgomp.order.mapper.OrderCommissionRecordMapper;
import com.things.cgomp.order.service.IOrderCommissionRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * @author things
 */
@Service
public class OrderCommissionRecordServiceImpl implements IOrderCommissionRecordService {

    @Resource
    private OrderCommissionRecordMapper commissionRecordMapper;

    @Override
    public boolean checkOrderExist(Long orderId) {
        OrderCommissionRecord record = commissionRecordMapper.selectOne(OrderCommissionRecord::getOrderId, orderId);
        return record != null;
    }

    @Override
    public int addRecord(OrderCommissionRecord record) {
        return commissionRecordMapper.insert(record);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public CommissionRecordData getTotalData(CommissionRecordQueryDTO queryDTO) {
        return commissionRecordMapper.selectTotalData(queryDTO);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public PageInfo<CommissionRecordData> selectRecordPage(CommissionRecordQueryDTO queryDTO) {
        startPage();
        List<CommissionRecordData> list = commissionRecordMapper.selectOrderRecordList(queryDTO);
        return new PageInfo<>(list);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public PageInfo<CommissionRecordStatisticsData> selectRecordStatisticsPage(CommissionRecordStatisticsQueryDTO queryDTO) {
        if (queryDTO.getDateType() == null) {
            return emptyPageInfo();
        }
        switch (queryDTO.getDateType()){
            case 1:
                // 按月统计
                queryDTO.setDateFormat("%Y-%m");
                break;
            case 2:
                // 按年统计
                queryDTO.setDateFormat("%Y");
                break;
            default:
                return emptyPageInfo();
        }
        if (queryDTO.getType() == null) {
            return emptyPageInfo();
        }
        List<CommissionRecordStatisticsData> list;
        startPage();
        switch (queryDTO.getType()){
            case 1:
                // 运营商统计
                list = commissionRecordMapper.selectStatisticsListByOperator(queryDTO);
                break;
            case 2:
                // 站点统计
                list = commissionRecordMapper.selectStatisticsListBySite(queryDTO);
                break;
            default:
                return emptyPageInfo();
        }
        return new PageInfo<>(list);
    }

    private PageInfo<CommissionRecordStatisticsData> emptyPageInfo(){
        return new PageInfo<>(new ArrayList<>());
    }
}
