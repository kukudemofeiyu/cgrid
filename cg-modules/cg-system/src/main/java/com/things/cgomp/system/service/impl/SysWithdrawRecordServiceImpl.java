package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import com.things.cgomp.system.api.dto.AppRefundRecordDTO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import com.things.cgomp.system.mapper.SysWithdrawRecordMapper;
import com.things.cgomp.system.service.ISysWithdrawRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author things
 * @date 2025/3/7
 */
@Service
public class SysWithdrawRecordServiceImpl extends ServiceImpl<SysWithdrawRecordMapper, SysWithdrawRecord> implements ISysWithdrawRecordService {

    @Override
    public int insertWithdrawRecord(SysWithdrawRecord record) {
        return baseMapper.insert(record);
    }
    @Override
    public List<AppRefundRecordVO> selectRefundRecordList(AppRefundRecordDTO req) {
        return baseMapper.selectRefundRecordList(req);
    }
}
