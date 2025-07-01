package com.things.cgomp.system.mapper;

import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.system.api.domain.SysUserOperator;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author things
 */
@Mapper
public interface SysUserOperatorMapper extends BaseMapperX<SysUserOperator> {

    /**
     * 通过用户ID删除用户和运营商关联
     *
     * @param userIds 用户ID集合
     * @return 结果
     */
    int deleteUserOperator(Long[] userIds);

    default int deleteByOperatorId(Long operatorId){
        LambdaQueryWrapperX<SysUserOperator> wrapper = new LambdaQueryWrapperX<SysUserOperator>()
                .eq(SysUserOperator::getOperatorId, operatorId);
        return delete(wrapper);
    }
}
