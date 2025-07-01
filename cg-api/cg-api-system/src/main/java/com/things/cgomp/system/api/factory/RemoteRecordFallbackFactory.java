package com.things.cgomp.system.api.factory;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.system.api.RemoteRecordService;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 明细记录服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteRecordFallbackFactory implements FallbackFactory<RemoteRecordService> {

    @Override
    public RemoteRecordService create(Throwable throwable) {
        log.error("明细记录服务调用失败:{}", throwable.getMessage());
        return new RemoteRecordService() {
            @Override
            public R<Boolean> saveAmountRecord(SysAmountRecord amountRecord, String source) {
                return R.fail("保存金额记录失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> saveWithdrawRecord(SysWithdrawRecord withdrawRecord, String source) {
                return R.fail("保存提现记录失败:" + throwable.getMessage());
            }

            @Override
            public R<PageInfo<AppRechargeRecordVO>> appRechargeRecordPage(Long userId, String beginTime, String endTime, Integer pageNum, Integer pageSize, String source) {
                return R.fail("保存提现记录失败:" + throwable.getMessage());
            }

            @Override
            public R<PageInfo<AppRefundRecordVO>> refundPage(Long userId, Integer status, String beginTime, String endTime, Integer pageNum, Integer pageSize, String source) {
                return R.fail("保存提现记录失败:" + throwable.getMessage());
            }
        };
    }
}
