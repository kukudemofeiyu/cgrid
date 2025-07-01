package com.things.cgomp.order.convert;

import com.things.cgomp.order.api.domain.CommissionRecordData;
import com.things.cgomp.order.vo.CommissionRecordTotalVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author things
 */
@Mapper
public interface CommissionRecordConvert {

    CommissionRecordConvert INSTANCE = Mappers.getMapper(CommissionRecordConvert.class);

    @Mappings({
            @Mapping(source = "platformAmount", target = "platformTotalAmount"),
            @Mapping(source = "operatorAmount", target = "operatorTotalAmount"),
    })
    CommissionRecordTotalVO convert(CommissionRecordData bean);

    default CommissionRecordTotalVO convertTotal(CommissionRecordData bean){
        CommissionRecordTotalVO resp = convert(bean);
        resp.setOrderTotalAmount(resp.getOperatorTotalAmount().add(resp.getPlatformTotalAmount()));
        return resp;
    }
}
