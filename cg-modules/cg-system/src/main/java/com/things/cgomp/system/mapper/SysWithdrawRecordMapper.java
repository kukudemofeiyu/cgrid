package com.things.cgomp.system.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import com.things.cgomp.system.api.dto.AppRefundRecordDTO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 * @date 2025/3/7
 */
@Mapper
public interface SysWithdrawRecordMapper extends BaseMapperX<SysWithdrawRecord> {
    List<AppRefundRecordVO> selectRefundRecordList(AppRefundRecordDTO req);
}
