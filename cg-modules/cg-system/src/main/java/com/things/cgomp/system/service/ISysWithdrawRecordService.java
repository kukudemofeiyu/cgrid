package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.system.api.domain.SysWithdrawRecord;
import com.things.cgomp.system.api.dto.AppRefundRecordDTO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;

import java.util.List;

/**
 * 提现记录服务类
 * @author things
 * @date 2025/3/7
 */
public interface ISysWithdrawRecordService extends IService<SysWithdrawRecord> {

    /**
     * 新增提现记录
     * @param record 记录提示
     * @return int
     */
    int insertWithdrawRecord(SysWithdrawRecord record);

    /**
     * App提现记录分页查询
     * @param req
     * @return
     */
    List<AppRefundRecordVO> selectRefundRecordList(AppRefundRecordDTO req);
}
