package com.things.cgomp.app.mapper;

import com.things.cgomp.app.domain.AppPaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.app.domain.vo.PaymentTypeDTO;

import java.util.List;

/**
* @description 针对表【app_payment_type(支付类型表)】的数据库操作Mapper
* @createDate 2025-03-24 19:17:18
* @Entity com.things.cgomp.app.domain.AppPaymentType
*/
public interface AppPaymentTypeMapper extends BaseMapper<AppPaymentType> {

    List<PaymentTypeDTO> selectPaymentTypeList();
}




