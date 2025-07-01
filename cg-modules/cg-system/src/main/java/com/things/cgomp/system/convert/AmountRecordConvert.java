package com.things.cgomp.system.convert;

import com.things.cgomp.common.record.enums.IncomeExpenseType;
import com.things.cgomp.common.record.enums.RecordChannel;
import com.things.cgomp.common.record.enums.RecordModule;
import com.things.cgomp.common.record.enums.RecordStatus;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.domain.SysUserAccount;
import com.things.cgomp.system.api.dto.SysOperatorAccountUpdateDTO;
import com.things.cgomp.system.domain.vo.AppUserRechargeRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author things
 * @date 2025/3/13
 */
@Mapper
public interface AmountRecordConvert {

    AmountRecordConvert INSTANCE = Mappers.getMapper(AmountRecordConvert.class);

    List<AppUserRechargeRecordVO> convertList(List<SysAmountRecord> list);

    @Mappings({
            @Mapping(source = "bindUser", target = "username"),
            @Mapping(source = "changeAfter", target = "balance"),
            @Mapping(source = "bindMobile", target = "beRechargeMobile"),
            @Mapping(source = "operateMobile", target = "rechargeMobile")
    })
    AppUserRechargeRecordVO convert(SysAmountRecord bean);

    default SysAmountRecord convertOperatorRecord(SysUserAccount account, SysOperatorAccountUpdateDTO bean){
        return SysAmountRecord.builder()
                .serialNumber(bean.getSerialNum())
                .bindUserId(bean.getUserId())
                .amount(bean.getAmount())
                .changeBefore(account.getBalance())
                .changeAfter(account.getNewBalance())
                .channel(RecordChannel.ACCOUNT_BALANCE.getChannel())
                .module(RecordModule.OPERATOR.getModule())
                .type(bean.getBusinessType())
                .recordType(bean.getAmount().compareTo(BigDecimal.ZERO) >=0 ? IncomeExpenseType.INCOME.getType() : IncomeExpenseType.EXPENSE.getType())
                .status(RecordStatus.SUCCESS.getStatus())
                .remark(bean.getRemark())
                .eventTime(account.getUpdateTime())
                .updateTime(account.getUpdateTime())
                .build();
    }
}
