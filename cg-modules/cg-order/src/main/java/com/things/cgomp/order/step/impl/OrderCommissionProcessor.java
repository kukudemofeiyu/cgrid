package com.things.cgomp.order.step.impl;

import cn.hutool.core.util.StrUtil;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommissionLevel;
import com.things.cgomp.common.core.enums.CommissionType;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.device.api.RemoteSiteService;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.order.api.domain.OrderCommissionRecord;
import com.things.cgomp.order.api.domain.OrderCommissionRule;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.enums.ErrorCodeConstants;
import com.things.cgomp.order.enums.ProcessStepEnum;
import com.things.cgomp.order.service.IOrderCommissionRecordService;
import com.things.cgomp.order.service.IOrderCommissionRuleService;
import com.things.cgomp.order.step.OrderPaidAfterProcessor;
import com.things.cgomp.system.api.RemoteOperatorService;
import com.things.cgomp.system.api.dto.SysOperatorAccountUpdateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 订单分账处理器
 *
 * @author things
 */
@Slf4j
@Component
@Order(OrderPaidAfterProcessor.ORDER_COMMISSION)
public class OrderCommissionProcessor implements OrderPaidAfterProcessor {

    @Resource
    private IOrderCommissionRuleService commissionRuleService;
    @Resource
    private IOrderCommissionRecordService commissionRecordService;
    @Resource
    private RemoteSiteService remoteSiteService;
    @Resource
    private RemoteOperatorService remoteOperatorService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void process(OrderInfo order) {
        if (commissionRecordService.checkOrderExist(order.getId())) {
            log.info("OrderCommissionProcessor 当前订单【{}】已存在分成记录，不再进行分成处理", order.getSn());
            return;
        }
        Long siteId = order.getSiteId();
        R<Site> siteR = remoteSiteService.selectSite(siteId);
        if (R.isError(siteR) || siteR.getData() == null) {
            throw new ServiceException("OrderCommissionProcessor 获取站点信息失败, siteId=" + siteId);
        }
        Long operatorId = siteR.getData().getOperatorId();
        List<OrderCommissionRule> rules = commissionRuleService.selectOperatorRuleList(operatorId);
        if (CollectionUtils.isEmpty(rules)) {
            log.info("运营商分成规则为空, operatorId={}", operatorId);
            return;
        }
        Map<Integer, List<OrderCommissionRule>> levelRuleMap = rules.stream()
                .filter(rule -> CommissionLevel.isOperatorLevel(rule.getLevel()))
                .collect(Collectors.groupingBy(OrderCommissionRule::getLevel));
        // 运营商站点级规则
        List<OrderCommissionRule> siteLevelRules = levelRuleMap.get(CommissionLevel.OPERATOR_SITE.getLevel());
        Optional<OrderCommissionRule> siteRule = siteLevelRules.stream().filter(rule -> rule.getSiteId().equals(siteId)).findFirst();
        OrderCommissionRecord record;
        if (siteRule.isPresent()) {
            // 优先按照站点级规则进行分成
            record = calculateCommission(order, siteRule.get());
        } else {
            // 运营商级规则
            List<OrderCommissionRule> operatorLevelRules = levelRuleMap.get(CommissionLevel.OPERATOR.getLevel());
            if (CollectionUtils.isEmpty(operatorLevelRules)) {
                return;
            }
            // 按照运营商级规则进行分成
            record = calculateCommission(order, operatorLevelRules.get(0));
        }
        if (record != null) {
            record.setOperatorId(operatorId);
            // 保存记录
            saveRecord(record, operatorId, order.getSn());
        }
    }

    private OrderCommissionRecord calculateCommission(OrderInfo order, OrderCommissionRule rule) {
        CommissionType commissionType = CommissionType.getByType(rule.getType());
        if (commissionType == null) {
            return null;
        }
        // 将分成比例转换为小数
        BigDecimal ratio = rule.getRatio().divide(new BigDecimal(100), 4, RoundingMode.DOWN);
        BigDecimal platformAmount = null;
        BigDecimal operatorAmount = null;
        // 目前分成费用使用交易订单金额来计算
        switch (commissionType) {
            case ELECTRICITY_BILL:
                // 电费分账
                BigDecimal realChargeFee = order.getChargeFee();
                operatorAmount = format(realChargeFee.multiply(ratio));
                platformAmount = format(realChargeFee.subtract(operatorAmount));
                break;
            case SERVICE_BILL:
                // 服务费分账
                BigDecimal realServiceFee = order.getServiceFee();
                operatorAmount = format(realServiceFee.multiply(ratio));
                platformAmount = format(realServiceFee.subtract(operatorAmount));
                break;
            case ELECTRICITY_AND_SERVICE_BILL:
                // 电费+服务费分账 = 订单金额
                BigDecimal orderAmount = order.getOrderAmount();
                operatorAmount = format(orderAmount.multiply(ratio));
                platformAmount = format(orderAmount.subtract(operatorAmount));
                break;
        }
        return OrderCommissionRecord.builder()
                .orderId(order.getId())
                .ruleId(rule.getId())
                .commissionType(commissionType.getType())
                .commissionTypeDesc(commissionType.getDesc())
                .operatorPercent(rule.getRatio())
                .platformPercent(getPlatformRatio(rule.getRatio()))
                .platformAmount(platformAmount)
                .operatorAmount(operatorAmount)
                .operatorRealAmount(operatorAmount)
                .eventTime(Date.from(order.getPayTime().atZone(ZoneId.systemDefault()).toInstant()))
                .build();
    }

    private BigDecimal format(BigDecimal amount) {
        return amount.setScale(4, RoundingMode.DOWN);
    }

    public void saveRecord(OrderCommissionRecord record, Long operatorId, String orderSn) {
        commissionRecordService.addRecord(record);
        if (record.getOperatorAmount().compareTo(BigDecimal.ZERO) > 0) {
            // 添加运营商账户金额
            SysOperatorAccountUpdateDTO req = SysOperatorAccountUpdateDTO.builder()
                    .operatorId(operatorId)
                    .amount(record.getOperatorAmount())
                    .serialNum(orderSn)
                    .remark(StrUtil.format("充电订单[{}]分成[{}]元",
                            record.getCommissionTypeDesc(),
                            record.getOperatorAmount().stripTrailingZeros().toPlainString()))
                    .build();
            R<Boolean> updateR = remoteOperatorService.updateOperatorAccount(req, SecurityConstants.INNER);
            if (!updateR.success() || updateR.getData() == null || !updateR.getData()) {
                log.error("OrderCommissionProcessor 修改运营商账户金额失败, updateReq={}", req);
                // 抛出异常，回滚当前事务
                throw exception(ErrorCodeConstants.COMMISSION_UPDATE_ACCOUNT_FAIL);
            }
        }
    }

    private BigDecimal getPlatformRatio(BigDecimal ratio) {
        return new BigDecimal(100).subtract(ratio).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public ProcessStepEnum getStep() {
        return ProcessStepEnum.UNDIVIDED_COMMISSION;
    }
}
