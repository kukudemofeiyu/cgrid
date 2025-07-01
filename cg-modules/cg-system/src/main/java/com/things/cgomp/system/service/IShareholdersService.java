package com.things.cgomp.system.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.order.api.domain.OrderShareholders;

import java.util.List;

/**
 * @author things
 * @date 2025/2/28
 */
public interface IShareholdersService {

    /**
     * 分页获取分成者列表
     * @param shareholders
     * @return
     */
    PageInfo<OrderShareholders> selectPage(OrderShareholders shareholders);

    /**
     * 获取分成者列表
     * @return
     */
    List<OrderShareholders> selectList(OrderShareholders shareholders);

    /**
     * 新增分成者
     * @param shareholders
     * @return
     */
    int insertShareholders(OrderShareholders shareholders);

    /**
     * 通过分成者ID查询运营商
     *
     * @param shareholdersId 分成者ID
     * @return 运营商象信息
     */
    OrderShareholders selectShareholdersById(Long shareholdersId);

    /**
     * 删除分成者
     *
     * @param shareholdersId 分成者ID
     * @return 结果
     */
    int deleteShareholdersById(Long shareholdersId);

    /**
     * 根据运营商ID获取分成者列表
     * @param operatorId 运营商ID
     * @return
     */
    List<OrderShareholders> selectListByOperatorId(Long operatorId);

    /**
     * 修改分成者状态
     * @param updateReq 修改对象
     * @return int
     */
    int updateStatus(OrderShareholders updateReq);

    /**
     * 修改分成者信息
     * @param orderShareholders 修改对象
     * @return int
     */
    int updateShareholders(OrderShareholders orderShareholders);
}
