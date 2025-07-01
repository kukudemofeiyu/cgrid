package com.things.cgomp.system.convert;

import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.domain.dto.CommissionRuleAdd;
import com.things.cgomp.system.domain.vo.ShareholdersSimpleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author things
 * @date 2025/2/28
 */
@Mapper
public interface ShareholdersConvert {

    ShareholdersConvert INSTANCE = Mappers.getMapper(ShareholdersConvert.class);

    List<ShareholdersSimpleVO> convertList(List<OrderShareholders> list);

    ShareholdersSimpleVO convertSimple(OrderShareholders bean);

    SysUser convertUser(OrderShareholders bean);

    default OrderCommissionRule convertRule(OrderShareholders bean){
        OrderCommissionRule rule = new OrderCommissionRule();
        rule.setUserId(bean.getUserId());
        rule.setOperatorId(bean.getOperatorId());
        rule.setLevel(CommissionLevel.SHAREHOLDERS.getLevel());
        rule.setRatio(bean.getCommissionPercent());
        rule.setCreateBy(SecurityUtils.getUserId());
        rule.setUpdateBy(SecurityUtils.getUserId());
        return rule;
    }

    default CommissionRuleUpdateDTO convertRuleUpt(CommissionRuleAdd bean){
        CommissionRuleUpdateDTO rule = new CommissionRuleUpdateDTO();
        rule.setUserId(bean.getUserId());
        rule.setRatio(bean.getRatio());
        rule.setSiteId(bean.getSiteId());
        rule.setLevel(CommissionLevel.SHAREHOLDERS_SITE.getLevel());
        return rule;
    }
}
