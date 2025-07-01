package com.things.cgomp.common.record.service;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.system.api.RemoteRecordService;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 系统明细记录服务
 *
 * @author things
 */
@Service
public class SystemRecordService {

    @Resource
    private RemoteRecordService remoteRecordService;

    /**
     * 保存金额记录
     * @param amountRecord 金额对象服务
     */
    public void saveAmountRecord(SysAmountRecord amountRecord) throws Exception {
        remoteRecordService.saveAmountRecord(amountRecord, SecurityConstants.INNER);
    }

    /**
     * 保存提现记录
     * @param withdrawRecord 提现记录对象
     */
    public void saveWithdrawRecord(SysWithdrawRecord withdrawRecord) throws Exception {
        remoteRecordService.saveWithdrawRecord(withdrawRecord, SecurityConstants.INNER);
    }
}
