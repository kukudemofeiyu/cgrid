package com.things.cgomp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.core.enums.SysUserType;
import com.things.cgomp.common.core.utils.SpringUtils;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.system.api.domain.SysUser;
import com.things.cgomp.system.convert.ShareholdersConvert;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.mapper.ShareholdersMapper;
import com.things.cgomp.system.service.ICommissionRuleService;
import com.things.cgomp.system.service.IShareholdersService;
import com.things.cgomp.system.service.ISysUserService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * @author things
 * @date 2025/2/28
 */
@Data
@Service
public class ShareholdersServiceImpl implements IShareholdersService {

    @Resource
    private ISysUserService userService;
    @Resource
    private ICommissionRuleService commissionRuleService;
    @Resource
    private ShareholdersMapper shareholdersMapper;

    @Override
    @DataScope(orgAlias = "o")
    public PageInfo<OrderShareholders> selectPage(OrderShareholders shareholders) {
        startPage();
        List<OrderShareholders> shareholdersList = shareholdersMapper.selectShareholdersList(shareholders);
        return new PageInfo<>(shareholdersList);
    }

    @Override
    @DataScope(orgAlias = "o")
    public List<OrderShareholders> selectList(OrderShareholders shareholders) {
        shareholders.setStatus(CommonStatus.OK.getCode());
        return shareholdersMapper.selectShareholdersList(shareholders);
    }

    @Override
    public List<OrderShareholders> selectListByOperatorId(Long operatorId) {
        OrderShareholders req = new OrderShareholders()
                .setStatus(CommonStatus.OK.getCode())
                .setOperatorId(operatorId);
        return shareholdersMapper.selectShareholdersList(req);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertShareholders(OrderShareholders shareholders) {
        SysUser user = ShareholdersConvert.INSTANCE.convertUser(shareholders);
        // 校验用户唯一性
        if (!userService.checkUserNameUnique(user)) {
            throw exception(ErrorCodeConstants.USER_USERNAME_IS_EXIST, user.getUsername());
        } else if (StringUtils.isNotEmpty(user.getMobile()) && !userService.checkPhoneUnique(user)) {
            throw exception(ErrorCodeConstants.USER_MOBILE_IS_EXIST, user.getMobile());
        }
        shareholders.setCreateBy(SecurityUtils.getUserId());
        shareholders.setOperatorId(SecurityUtils.getOperatorId());
        // 新增用户相关逻辑
        SpringUtils.getAopProxy(this).insertUser(user);
        shareholders.setUserId(user.getUserId());
        // 新增分成者分成规则
        OrderCommissionRule rule = ShareholdersConvert.INSTANCE.convertRule(shareholders);
        SpringUtils.getAopProxy(this).updateCommissionRule(rule);
        // 新增分成者
        return shareholdersMapper.insertShareholders(shareholders);
    }

    @Override
    public OrderShareholders selectShareholdersById(Long shareholdersId) {
        return shareholdersMapper.selectShareholdersById(shareholdersId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteShareholdersById(Long shareholdersId) {
        OrderShareholders shareholders = shareholdersMapper.selectById(shareholdersId);
        if (shareholders == null) {
            return 0;
        }
        // 先删除用户
        SpringUtils.getAopProxy(this).deleteUser(shareholders.getUserId());
        // 删除分成规则
        SpringUtils.getAopProxy(this).deleteCommissionRule(shareholders.getUserId());
        return shareholdersMapper.deleteShareholdersById(shareholdersId);
    }


    @Override
    public int updateStatus(OrderShareholders updateReq) {
        OrderShareholders update = new OrderShareholders()
                .setId(updateReq.getId())
                .setStatus(updateReq.getStatus());
        return shareholdersMapper.updateById(update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShareholders(OrderShareholders shareholders) {
        SysUser user = new SysUser()
                .setUserId(shareholders.getUserId())
                .setMobile(shareholders.getMobile())
                .setRealName(shareholders.getRealName());
        if (StringUtils.isNotEmpty(user.getMobile()) && !userService.checkPhoneUnique(user)) {
            throw exception(ErrorCodeConstants.USER_MOBILE_IS_EXIST, user.getMobile());
        }
        // 修改用户信息
        user.setUpdateBy(SecurityUtils.getUserId());
        SpringUtils.getAopProxy(this).updateUser(user);
        // 修改分成者规则
        OrderCommissionRule rule = ShareholdersConvert.INSTANCE.convertRule(shareholders);
        SpringUtils.getAopProxy(this).updateCommissionRule(rule);
        return shareholdersMapper.updateById(shareholders);
    }

    public void insertUser(SysUser user){
        user.setUserType(SysUserType.OPERATOR_USER.getType());
        this.userService.insertUser(user);
    }

    public void updateUser(SysUser user){
        this.userService.updateUser(user);
    }

    public void deleteUser(Long userId){
        this.userService.deleteUserById(userId);
    }

    public void updateCommissionRule(OrderCommissionRule rule){
        this.commissionRuleService.updateCommissionRule(rule);
    }

    public void deleteCommissionRule(Long userId){
        this.commissionRuleService.deleteByUserId(userId);
    }
}
