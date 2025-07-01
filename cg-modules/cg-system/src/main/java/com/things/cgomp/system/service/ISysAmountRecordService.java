package com.things.cgomp.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.dto.AppRechargeRecordDTO;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.domain.dto.SysAmountRecordReq;

import java.util.List;

/**
 * 系统金额记录服务类
 * @author things
 * @date 2025/3/7
 */
public interface ISysAmountRecordService extends IService<SysAmountRecord> {

    /**
     * 新增金额记录
     * @param record 记录提示
     * @return int
     */
    int insertAmountRecord(SysAmountRecord record);

    /**
     * 查询记录列表
     * @param req 请求对象
     * @return List
     */
    List<SysAmountRecord> selectRecordList(SysAmountRecordReq req);

    /**
     * 根据记录ID查询单条记录
     * @param id ID
     * @return SysAmountRecord
     */
    SysAmountRecord selectRecordById(Long id);

    List<AppRechargeRecordVO> appRechargeRecordPage(AppRechargeRecordDTO req);



}
