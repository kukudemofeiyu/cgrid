package com.things.cgomp.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.order.mapper.AmountRecordMapper;
import com.things.cgomp.order.service.IAmountRecordService;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 金额记录表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-17
 */
@Service
public class AmountRecordServiceImpl extends ServiceImpl<AmountRecordMapper, SysAmountRecord> implements IAmountRecordService {

}
