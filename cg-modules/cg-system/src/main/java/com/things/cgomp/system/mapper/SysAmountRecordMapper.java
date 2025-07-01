package com.things.cgomp.system.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.dto.AppRechargeRecordDTO;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.domain.dto.SysAmountRecordReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 * @date 2025/3/7
 */
@Mapper
public interface SysAmountRecordMapper extends BaseMapperX<SysAmountRecord> {

    List<SysAmountRecord> selectRecordList(SysAmountRecordReq req);

    List<AppRechargeRecordVO> selectAppRechargeRecordList(AppRechargeRecordDTO req);
}
