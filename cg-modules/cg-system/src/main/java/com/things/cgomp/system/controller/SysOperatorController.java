package com.things.cgomp.system.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.core.enums.OrgTypeEnum;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.log.enums.BusinessType;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.order.api.RemoteCommissionRuleService;
import com.things.cgomp.order.api.dto.CommissionRuleQueryDTO;
import com.things.cgomp.order.api.dto.CommissionRuleUpdateDTO;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.api.domain.OrderShareholders;
import com.things.cgomp.system.api.domain.SysOperator;
import com.things.cgomp.system.api.domain.SysOrg;
import com.things.cgomp.system.api.domain.SysRole;
import com.things.cgomp.system.api.dto.SysOperatorAccountUpdateDTO;
import com.things.cgomp.system.convert.RoleConvert;
import com.things.cgomp.system.convert.SysOperatorConvert;
import com.things.cgomp.system.domain.vo.SysOperatorChildVO;
import com.things.cgomp.system.domain.vo.SysOperatorSimpleVO;
import com.things.cgomp.system.domain.vo.SysRoleSimpleVO;
import com.things.cgomp.system.domain.vo.TreeSelect;
import com.things.cgomp.system.enums.ErrorCodeConstants;
import com.things.cgomp.system.service.IShareholdersService;
import com.things.cgomp.system.service.ISysOperatorService;
import com.things.cgomp.system.service.ISysOrgService;
import com.things.cgomp.system.service.ISysRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 运营商管理
 *
 * @author things
 */
@Log(title = "运营商管理")
@RestController
@RequestMapping("/operator")
public class SysOperatorController {

    @Resource
    private ISysOperatorService operatorService;
    @Resource
    private IShareholdersService shareholdersService;
    @Resource
    private RemoteCommissionRuleService remoteCommissionRuleService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysOrgService orgService;

    /**
     * 获取运营商分页列表
     */
    @RequiresPermissions("system:operator:list")
    @GetMapping("/page")
    public R<PageInfo<SysOperator>> page(SysOperator operator) {
        PageInfo<SysOperator> pageInfo = operatorService.selectOperatorPage(operator);
        return R.ok(pageInfo);
    }

    /**
     * 获取运营商简约列表
     * 用于下拉框
     */
    @GetMapping("/simple-list")
    public R<List<SysOperatorSimpleVO>> list(SysOperator operator) {
        List<SysOperator> list = operatorService.selectOperatorList(operator);
        return R.ok(SysOperatorConvert.INSTANCE.convetList(list));
    }

    /**
     * 新增运营商
     */
    @RequiresPermissions("system:operator:add")
    @Log(method = "新增运营商", businessType = BusinessType.INSERT)
    @PostMapping
    public R<Integer> add(@Validated @RequestBody SysOperator operator) {
        if (ObjectUtil.isEmpty(operator.getUsername())) {
            throw exception(ErrorCodeConstants.USER_USERNAME_NOT_NULL);
        }
        if (ObjectUtil.isEmpty(operator.getPassword())) {
            throw exception(ErrorCodeConstants.OPERATOR_PASSWORD_NOT_NULL);
        }
        if (ArrayUtil.isEmpty(operator.getRoleIds())) {
            throw exception(ErrorCodeConstants.OPERATOR_ROLE_IS_NULL);
        }
        return R.ok(operatorService.insertOperator(operator));
    }

    /**
     * 根据运营商编号获取详细信息
     */
    @RequiresPermissions({
            "system:operator:query",
            "system:operator:config"
    })
    @GetMapping(value = {""})
    public R<SysOperator> getInfo(SysOperator req) {
        SysOperator operator = operatorService.selectOperatorById(req.getOperatorId());
        if (operator == null) {
            return R.ok();
        }
        List<Long> roleIds = roleService.selectRoleListByUserId(operator.getUserId());
        operator.setRoleIds(roleIds.toArray(new Long[0]));
        // 统计累计收益
        operator.setTotalIncome(BigDecimal.ZERO);
        return R.ok(operator);
    }

    /**
     * 根据运营商编号获取分成者信息
     */
    @RequiresPermissions("system:operator:config:shareholders")
    @GetMapping(value = {"/child"})
    public R<SysOperatorChildVO> getChild(SysOperator req) {
        // 获取运营商基本信息
        SysOperator operator = operatorService.selectOperatorById(req.getOperatorId());
        if (operator == null) {
            return R.ok();
        }
        SysOperatorChildVO respVO = SysOperatorConvert.INSTANCE.convert(operator);
        // 获取分成者信息
        List<OrderShareholders> shareholders = shareholdersService.selectListByOperatorId(req.getOperatorId());
        respVO.setShareholders(shareholders);
        return R.ok(respVO);
    }

    /**
     * 删除运营商
     */
    @RequiresPermissions("system:operator:remove")
    @Log(method = "删除运营商", businessType = BusinessType.DELETE)
    @DeleteMapping("")
    public R<Integer> remove(@RequestBody SysOperator req) {
        if (req.getOperatorId() == null) {
            throw exception(ErrorCodeConstants.OPERATOR_ID_IS_NOT_NULL);
        }
        // 校验是否可以删除运营商
        operatorService.checkOperatorDelete(req.getOperatorId());
        return R.ok(operatorService.deleteOperatorById(req.getOperatorId()));
    }

    /**
     * 修改运营商信息
     */
    @RequiresPermissions("system:operator:edit")
    @Log(method = "修改运营商信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public R<Integer> edit(@Validated @RequestBody SysOperator operator) {
        if (ObjectUtil.isEmpty(operator.getOperatorId())) {
            throw exception(ErrorCodeConstants.OPERATOR_ID_IS_NOT_NULL);
        }
        return R.ok(operatorService.updateOperator(operator));
    }

    /**
     * 修改运营商状态
     */
    @RequiresPermissions("system:operator:switch")
    @Log(method = "修改运营商状态", businessType = BusinessType.UPDATE)
    @PutMapping("/switch")
    public R<Integer> switchStatus(@RequestBody SysOperator operator) {
        if (operator.getOperatorId() == null) {
            throw exception(ErrorCodeConstants.OPERATOR_ID_IS_NOT_NULL);
        }
        return R.ok(operatorService.updateStatus(operator));
    }

    /**
     * 查看分成设置
     */
    @RequiresPermissions("system:operator:config:commission")
    @GetMapping("/commission")
    public R<OrderCommissionRule> getCommission(CommissionRuleQueryDTO ruleQuery) {
        ruleQuery.setLevel(CommissionLevel.OPERATOR.getLevel());
        R<OrderCommissionRule> ruleResp = remoteCommissionRuleService.selectOne(ruleQuery, SecurityConstants.INNER);
        if (R.SUCCESS != ruleResp.getCode()) {
            return R.fail();
        }
        return R.ok(ruleResp.getData());
    }

    /**
     * 设置分成比例
     */
    @RequiresPermissions("system:operator:config:commission")
    @Log(method = "设置运营商分成比例", businessType = BusinessType.UPDATE)
    @PutMapping("/commission")
    public R<Integer> editCommission(@Validated @RequestBody CommissionRuleUpdateDTO ruleUpdate) {
        if (ruleUpdate.getOperatorId() == null) {
            throw exception(ErrorCodeConstants.OPERATOR_ID_IS_NOT_NULL);
        }
        ruleUpdate.setLevel(CommissionLevel.OPERATOR.getLevel());
        R<Integer> respR = remoteCommissionRuleService.updateRule(ruleUpdate, SecurityConstants.INNER);
        if (R.SUCCESS != respR.getCode()) {
            throw exception(ErrorCodeConstants.OPERATOR_COMMISSION_SET_FAIL);
        }
        return R.ok(respR.getData());
    }

    /**
     * 远程接口
     * 根据用户编码获取运营商信息
     */
    @InnerAuth
    @GetMapping(value = {"/getByUser/{userId}"})
    public R<SysOperator> getInfoByUserId(@PathVariable Long userId) {
        SysOperator operator = operatorService.selectOperatorByUserId(userId);
        return R.ok(operator);
    }

    /**
     * 远程接口 忽略权限校验
     * 根据运营商ID获取运营商信息
     */
    @InnerAuth
    @GetMapping(value = {"/getById/{operatorId}"})
    public R<SysOperator> getInfoById(@PathVariable Long operatorId) {
        SysOperator operator = operatorService.selectOperatorById(operatorId);
        return R.ok(operator);
    }

    /**
     * 获取运营商内置角色
     * @return List<SysRoleSimpleVO>
     */
    @GetMapping("/role")
    public R<List<SysRoleSimpleVO>> getOperatorRole(){
        SysRole orgReq = new SysRole().setType(0).setOrgType(OrgTypeEnum.OPERATOR.getType());
        List<SysRole> roleList = roleService.selectBuiltInRoleByOrgType(orgReq);
        return R.ok(RoleConvert.INSTANCE.convertList(roleList));
    }

    /**
     * 获取运营商所有组织
     * @return TreeSelect
     */
    @GetMapping("/orgTree")
    public R<List<TreeSelect>> getOperatorOrg(){
        SysOrg org = new SysOrg().setType(OrgTypeEnum.OPERATOR.getType());
        return R.ok(orgService.selectOrgTreeList(org));
    }

    @InnerAuth
    @PutMapping("/updateAccount")
    public R<Boolean> updateOperatorAccount(@Validated @RequestBody SysOperatorAccountUpdateDTO updateDTO){
        return R.ok(operatorService.updateAccount(updateDTO));
    }
}
