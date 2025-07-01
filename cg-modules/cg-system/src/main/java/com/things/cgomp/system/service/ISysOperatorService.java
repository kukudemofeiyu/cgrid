package com.things.cgomp.system.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.system.api.domain.SysOperator;
import com.things.cgomp.system.api.dto.SysOperatorAccountUpdateDTO;

import java.util.List;

/**
 * 运营商 服务层
 *
 * @author things
 */
public interface ISysOperatorService {

    PageInfo<SysOperator> selectOperatorPage(SysOperator operator);

    List<SysOperator> selectOperatorList(SysOperator operator);

    int insertOperator(SysOperator operator);

    /**
     * 通过运营商ID查询运营商
     *
     * @param operatorId 运营商ID
     * @return 运营商对象信息
     */
    SysOperator selectOperatorById(Long operatorId);

    /**
     * 通过用户ID查询运营商
     *
     * @param userId 用户ID
     * @return 运营商对象信息
     */
    SysOperator selectOperatorByUserId(Long userId);

    /**
     * 删除运营商
     *
     * @param operatorId 运营商ID
     * @return 结果
     */
    int deleteOperatorById(Long operatorId);
    /**
     * 修改运营商
     * @param operator 运营商对象
     * @return 结果
     */
    int updateOperator(SysOperator operator);

    /**
     * 修改运营商状态
     * @param operator 运营商对象
     * @return 结果
     */
    int updateStatus(SysOperator operator);

    /**
     * 检查运营商是否可以删除
     * @param operatorId 运营商ID
     */
    void checkOperatorDelete(Long operatorId);

    /**
     * 修改运营商账户
     * @param updateDTO   修改对象
     * @return true/false
     */
    boolean updateAccount(SysOperatorAccountUpdateDTO updateDTO);
}
