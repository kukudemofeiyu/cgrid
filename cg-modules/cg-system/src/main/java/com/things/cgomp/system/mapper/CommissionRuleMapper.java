package com.things.cgomp.system.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;


/**
 * 分成规则 数据层
 *
 * @author things
 */
@Mapper
public interface CommissionRuleMapper extends BaseMapperX<OrderCommissionRule> {

    int saveOrUpdate(OrderCommissionRule rule);

    @Select("select sum(ratio) from order_commission_rule where level=#{level}")
    BigDecimal selectSumByLevel(@Param("level") Integer level);
}
