package com.things.cgomp.order.service.impl;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.order.api.domain.OrderFinanceData;
import com.things.cgomp.order.dto.OrderFinanceQueryDTO;
import com.things.cgomp.order.mapper.OrderFinanceMapper;
import com.things.cgomp.order.service.IOrderFinanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * @author things
 */
@Service
public class OrderFinanceServiceImpl implements IOrderFinanceService {

    @Resource
    private OrderFinanceMapper orderFinanceMapper;

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public OrderFinanceData selectTotalData(OrderFinanceQueryDTO queryDTO) {
        return orderFinanceMapper.selectTotalData(queryDTO);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public PageInfo<OrderFinanceData> selectPage(OrderFinanceQueryDTO queryDTO) {
        Integer type = queryDTO.getType();
        if (type == null) {
            return emptyPageInfo();
        }
        List<OrderFinanceData> list;
        startPage();
        switch (type) {
            case 1:
                // 运营商统计
                list = orderFinanceMapper.selectListByOperator(queryDTO);
                break;
            case 2:
                // 站点统计
                list = orderFinanceMapper.selectListBySite(queryDTO);
                break;
            case 3:
                // 充电桩统计
                list = orderFinanceMapper.selectListByPile(queryDTO);
                break;
            default:
                return emptyPageInfo();
        }
        return new PageInfo<>(list);
    }

    private PageInfo<OrderFinanceData> emptyPageInfo(){
        return new PageInfo<>(new ArrayList<>());
    }
}
