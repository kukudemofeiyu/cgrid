package com.things.cgomp.order.convert;

import com.things.cgomp.order.api.dto.CommissionRuleQueryDTO;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author things
 * @date 2025/3/4
 */
@Mapper
public interface CommissionRuleConvert {

    CommissionRuleConvert INSTANCE = Mappers.getMapper(CommissionRuleConvert.class);

    OrderCommissionRule convert(CommissionRuleQueryDTO bean);

    OrderCommissionRule convert(CommissionRuleUpdateDTO bean);
}
