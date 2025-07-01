package com.things.cgomp.app.mapper;

import com.things.cgomp.app.domain.AppUserRefund;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 0120
* @description 针对表【app_user_refund(提现记录表)】的数据库操作Mapper
* @createDate 2025-02-26 14:36:12
* @Entity com.things.cgomp.app.domain.AppUserRefund
*/
@Mapper
public interface AppUserRefundMapper extends BaseMapper<AppUserRefund> {

}




