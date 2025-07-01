package com.things.cgomp.system.mapper;

import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.system.api.domain.SysOperator;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author things
 */
@Mapper
public interface SysOperatorMapper extends BaseMapperX<SysOperator> {

    List<SysOperator> selectOperatorList(SysOperator operator);

    SysOperator selectOperatorById(Long operatorId);

    SysOperator selectSimpleOperatorById(Long operatorId);

    default int deleteOperatorById(Long operatorId) {
        SysOperator operator = new SysOperator().setOperatorId(operatorId).setDelFlag(CommonStatus.DELETED.getCode());
        return updateById(operator);
    }

    default SysOperator selectOperatorByUserId(Long userId){
        LambdaQueryWrapperX<SysOperator> wrapperX = new LambdaQueryWrapperX<SysOperator>()
                .eq(SysOperator::getUserId, userId)
                .eq(SysOperator::getStatus, CommonStatus.OK.getCode())
                .eq(SysOperator::getDelFlag, CommonStatus.NOT_DELETED.getCode());
        return selectOne(wrapperX);
    }
}
