package com.things.cgomp.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.record.enums.RecordModule;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.dto.AppRechargeRecordDTO;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.domain.dto.SysAmountRecordReq;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.SysAmountRecordMapper;
import com.things.cgomp.system.service.ISysAmountRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * @author things
 * @date 2025/3/7
 */
@Service
public class SysAmountRecordServiceImpl extends ServiceImpl<SysAmountRecordMapper, SysAmountRecord> implements ISysAmountRecordService {

    @Override
    public int insertAmountRecord(SysAmountRecord record) {
        RecordModule recordModule = RecordModule.get(record.getModule());
        if (recordModule == null) {
            throw exception(ErrorCodeConstants.RECORD_AMOUNT_MODULE_IS_NULL);
        }
        // 根据模块设置用户类型
        switch (recordModule){
            case APP_USER:
            case IC_CARD:
                record.setUserType(UserAccountType.APP.getCode());
                break;
            case OPERATOR:
            case SHAREHOLDERS:
                record.setUserType(UserAccountType.WEB.getCode());
                break;
        }
        return baseMapper.insert(record);
    }

    @Override
    public List<SysAmountRecord> selectRecordList(SysAmountRecordReq req) {
        return baseMapper.selectRecordList(req);
    }

    @Override
    public SysAmountRecord selectRecordById(Long id) {
        SysAmountRecordReq req = new SysAmountRecordReq().setId(id);
        List<SysAmountRecord> list = baseMapper.selectRecordList(req);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        SysAmountRecord record = list.get(0);
        record.setRefundAvailable(record.getAmount().subtract(record.getRefundAmount()));
        return record;
    }

    @Override
    public List<AppRechargeRecordVO> appRechargeRecordPage(AppRechargeRecordDTO req) {
        return baseMapper.selectAppRechargeRecordList(req);
    }
}
