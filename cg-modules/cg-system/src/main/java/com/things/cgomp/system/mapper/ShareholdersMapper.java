package com.things.cgomp.system.mapper;

import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.system.domain.ShareholdersSurplus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 分成者 数据层
 *
 * @author things
 */
@Mapper
public interface ShareholdersMapper extends BaseMapperX<OrderShareholders> {

    List<OrderShareholders> selectShareholdersList(OrderShareholders shareholders);

    OrderShareholders selectShareholdersById(Long shareholdersId);

    ShareholdersSurplus getSurplusByOperatorId(Long operatorId);

    int insertShareholders(OrderShareholders shareholders);

    default int deleteShareholdersById(Long shareholdersId){
        OrderShareholders shareholders = new OrderShareholders().setId(shareholdersId).setDelFlag(CommonStatus.DELETED.getCode());
        return updateById(shareholders);
    }
}
