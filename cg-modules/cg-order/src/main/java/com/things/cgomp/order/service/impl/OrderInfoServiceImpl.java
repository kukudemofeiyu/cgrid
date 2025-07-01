package com.things.cgomp.order.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.CommonStatus;
import com.things.cgomp.common.core.enums.EnableEnum;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DrawGunReqMsg;
import com.things.cgomp.common.mq.message.OrderPaySuccessReqMsg;
import com.things.cgomp.common.mq.message.TradingRecordConfirmReqMsg;
import com.things.cgomp.common.mq.message.app.AppEndChargingReqMsg;
import com.things.cgomp.common.mq.message.order.EndChargingReqMsg;
import com.things.cgomp.common.mq.message.order.TradingRecordConfirmRespMsg;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.common.mybatisplus.query.LambdaQueryWrapperX;
import com.things.cgomp.common.record.enums.*;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.device.api.RemoteSiteService;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.order.api.domain.*;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.dto.AppOrderDiscountDTO;
import com.things.cgomp.order.api.dto.OrderDiscountDTO;
import com.things.cgomp.order.api.dto.PayWalletMoneyDTO;
import com.things.cgomp.order.api.enums.*;
import com.things.cgomp.order.api.vo.*;
import com.things.cgomp.order.api.domain.OrderDiscount;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.dto.OrderAppPageDTO;
import com.things.cgomp.order.dto.OrderPageDTO;
import com.things.cgomp.order.dto.OrderStatisticsQueryDTO;
import com.things.cgomp.order.enums.*;
import com.things.cgomp.order.mapper.OrderInfoMapper;
import com.things.cgomp.order.service.*;
import com.things.cgomp.order.service.impl.order.OrderContext;
import com.things.cgomp.order.service.impl.order.OrderState;
import com.things.cgomp.order.step.OrderPaidAfterHandler;
import com.things.cgomp.order.vo.OrderVo;
import com.things.cgomp.pay.api.RemoteCouponActivityService;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.device.api.dto.RuleFeeDTO;
import com.things.cgomp.device.api.dto.RuleTimeDTO;
import com.things.cgomp.system.api.RemoteAppUserService;
import com.things.cgomp.system.api.domain.SysAmountRecord;
import com.things.cgomp.system.api.domain.SysUserAccount;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * 充电订单表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    @Resource
    private RemoteSiteService remoteSiteService;
    @Resource
    private RemoteDeviceService remoteDeviceService;
    @Resource
    private RemoteAppUserService remoteAppUserService;
    @Resource
    private IOrderLogService orderLogService;
    @Resource
    private OrderSerialCodeService orderSerialCodeService;
    @Resource
    private OrderContext orderContext;
    @Resource
    private IProducer producer;
    @Resource
    private RemoteRuleService remoteRuleService;
    @Resource
    private IAmountRecordService amountRecordService;
    @Resource
    private IUserAccountService userAccountService;
    @Resource
    private IOrderDiscountService orderDiscountService;
    @Resource
    private OrderPaidAfterHandler orderPaidAfterHandler;
    @Resource
    private RemoteCouponActivityService remoteCouponActivityService;

    @Override
    public Long selectLatestUserId(String vin) {
        if(StringUtils.isBlank(vin)){
            return null;
        }

        return baseMapper.selectLatestUserId(vin);
    }

    @Override
    public String generateSn(
            Long portId,
            Long insertTime
    ) {
        DeviceInfo portDevice = getDeviceInfo(portId);
        if(portDevice == null){
            throw new ServiceException(ErrorCodeConstants.DEVICE_DOES_NOT_EXIST);
        }

        DeviceInfo pileDevice = getDeviceInfo(portDevice.getParentId());
        if(pileDevice == null){
            throw new ServiceException(ErrorCodeConstants.DEVICE_DOES_NOT_EXIST);
        }

        return orderSerialCodeService.genSerialCode(
                pileDevice.getSn(),
                portDevice.getAliasSn()
        );
    }

    @Override
    public String selectOrderSn(Long portId) {
        return generateSn(portId,null);
    }

    @Override
    public List<OrderInfo> selectOrdersBySns(List<String> sns) {
        if(CollectionUtils.isEmpty(sns)){
            return new ArrayList<>();
        }

        return baseMapper.selectOrdersBySns(sns);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public PageInfo<OrderVo> selectPage(OrderPageDTO pageDTO) {
        pageDTO.setTime();
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<OrderVo> orders = baseMapper.selectOrders(pageDTO);

            Map<Long, OrderInfo> parentOrderMap = getParentOrderMap(orders);
            Map<Long, List<OrderInfo>> childOrdersMap = getChildOrdersMap(
                    orders
            );

            Map<Long, List<OrderDiscount>> discountsMap = getDiscountsMap(orders);

            orders.forEach(order -> fillData(
                            childOrdersMap,
                            parentOrderMap,
                            discountsMap,
                            order
                    )
            );

            return new PageInfo<>(orders);
        }
    }

    private Map<Long, List<OrderDiscount>> getDiscountsMap(List<OrderVo> orders) {
        List<Long> orderIds = getOrderIds(orders);
        return orderDiscountService.selectDiscountsMap(orderIds);
    }

    private Map<Long, OrderInfo> getParentOrderMap(List<OrderVo> orders) {
        List<OrderInfo> parentOrders = getParentOrders(orders);

        return parentOrders.stream()
                .collect(Collectors.toMap(
                        OrderInfo::getId,
                        a -> a,
                        (a, b) -> a
                ));
    }

    private List<OrderInfo> getParentOrders(List<OrderVo> orders) {
        List<Long> parentIds = getParentId(orders);
        if(parentIds.isEmpty()){
            return new ArrayList<>();
        }

        return baseMapper.selectBatchIds(parentIds);
    }

    private List<Long> getParentId(List<OrderVo> orders) {
        return orders.stream()
                .map(OrderVo::getParentId)
                .filter(Objects::nonNull)
                .filter(parentId -> !parentId.equals(0L))
                .distinct()
                .collect(Collectors.toList());
    }

    private void fillData(
            Map<Long, List<OrderInfo>> childOrdersMap,
            Map<Long, OrderInfo> parentOrderMap,
            Map<Long, List<OrderDiscount>> discountsMap,
            OrderVo order
    ) {
        List<OrderDiscount> discounts = discountsMap.get(order.getId());
        List<OrderInfo> childOrders = childOrdersMap.get(order.getId());
        OrderInfo parentOrder = parentOrderMap.get(order.getParentId());
        order.setChildOrders(childOrders)
                .setParentOrder(parentOrder)
                .setDiscounts(discounts)
                .setDiscountAmount()
                .setPayStatus()
                .setOrderState()
                .setOwnOccupy();
    }

    private Map<Long, List<OrderInfo>> getChildOrdersMap(
            List<OrderVo> orders
    ) {
        List<OrderInfo> childOrders = getChildOrders(orders);

        Map<Long, List<OrderInfo>> childOrdersMap = new HashMap<>();
        for (OrderInfo childOrder : childOrders) {
            List<OrderInfo> subChildOrders = childOrdersMap.computeIfAbsent(childOrder.getParentId(), k -> new ArrayList<>());
            subChildOrders.add(childOrder);
        }

        return childOrdersMap;
    }

    private List<OrderInfo> getChildOrders(
            List<OrderVo> orders
    ) {
        List<Long> parentIds = getOrderIds(orders);
        if(parentIds.isEmpty()){
            return new ArrayList<>();
        }

        return baseMapper.selectChildOrders(parentIds);
    }

    private List<Long> getOrderIds(
            List<OrderVo> orders
    ) {
        return orders.stream()
                .map(OrderVo::getId)
                .collect(Collectors.toList());
    }

    @Override
    public PageInfo<OrderAppVO> selectAppPage(OrderAppPageDTO pageDTO) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {

            List<OrderAppVO> orders = baseMapper.selectAppOrders(pageDTO);
            //填充优惠金额和占位时间
            if (CollectionUtil.isNotEmpty(orders)){
                for (OrderAppVO orderAppVO : orders) {
                    //如果订单状态为已完成，则优惠金额等于订单金额减去支付金额
                    if (2 == orderAppVO.getStatus()){
                        //优惠金额等于订单金额减去支付金额
                        BigDecimal orderAmount = orderAppVO.getOrderAmount();
                        if (orderAmount!=null){
                            orderAppVO.setDiscountAmount(orderAmount.subtract(orderAppVO.getPayAmount()));
                        }
                    }
                             if (orderAppVO.getChargingTime()==null && StringUtils.isNotEmpty(orderAppVO.getStartTime())){
                        //用现在的时间算出和充电开始时间相距多少小时
                        orderAppVO.setChargingTime(
                                new BigDecimal(
                                        DateUtil.between(
                                                DateUtil.parse(orderAppVO.getStartTime()),
                                                new Date(),
                                                DateUnit.HOUR
                                        )
                                )
                        );
                    }
                }
            }
            return new PageInfo<>(orders);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void endCharging(EndChargingReqMsg reqMsg) {
        OrderInfo orderInfo = baseMapper.selectOrderBySn(OrderSnPrefixEnum.CHARGING.getPrefix() + reqMsg.getOrderNo());
        if (orderInfo == null) {
            return;
        }

        OrderState orderState = orderContext.getState(OrderStateEnum.CHARGING.getType());
        if (orderState != null) {
            orderState.endCharging(
                    orderInfo,
                    reqMsg
            );
        }
    }

    @Override
    public void confirmTradingRecord(TradingRecordConfirmReqMsg reqMsg) {
        OrderInfo orderInfo = baseMapper.selectOrderBySn(OrderSnPrefixEnum.CHARGING.getPrefix() + reqMsg.getOrderNo());
        if (orderInfo != null
                && OrderStateEnum.TRADING_RECORD_CONFIRM.getType().equals(orderInfo.getOrderState())
        ) {
            responseTradingRecord(reqMsg);
            return;
        }

        OrderState orderState = orderContext.getState(OrderStateEnum.END_CHARGING.getType());
        OrderInfo updatedOrder = orderState.confirmTradingRecord(
                orderInfo,
                reqMsg
        );

        tellAppEndCharging(
                updatedOrder
        );

        responseTradingRecord(reqMsg);
    }

    private void tellAppEndCharging(
            OrderInfo orderInfo
    ) {
        AppEndChargingReqMsg appEndChargingReqMsg = new AppEndChargingReqMsg()
                .setId(orderInfo.getId())
                .setOrderNo(orderInfo.getSn())
                .setOrderType(orderInfo.getOrderType());

        QueueMsg<AbstractBody> queueMsg = QueueMsg.builder()
                .body(appEndChargingReqMsg)
                .build();
        producer.syncSend(
                MQTopics.APP,
                MQTopics.Tag.APP_END_CHARGE,
                queueMsg
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void drawGun(DrawGunReqMsg reqMsg) {
        OrderInfo orderInfo = baseMapper.selectOrderBySn(OrderSnPrefixEnum.CHARGING.getPrefix() + reqMsg.getOrderNo());

        OrderState orderState = orderContext.getState(OrderStateEnum.TRADING_RECORD_CONFIRM.getType());
        if (orderState != null) {
            orderState.drawGun(
                    orderInfo,
                    reqMsg
            );
        }

    }

    private void responseTradingRecord(TradingRecordConfirmReqMsg reqMsg) {
        TradingRecordConfirmRespMsg recordConfirmRespMsg = new TradingRecordConfirmRespMsg()
                .setSuccess(R.SUCCESS)
                .setTransactionId(reqMsg.getTransactionId())
                .setOrderNo(reqMsg.getOrderNo());

        QueueMsg<AbstractBody> queueMsg = QueueMsg.builder()
                .body(recordConfirmRespMsg)
                .metadata(Metadata.builder().deviceId(reqMsg.getPileId()).portId(reqMsg.getPortId()).build())
                .build();
        producer.syncSend(
                MQTopics.ORDER,
                MQTopics.Tag.TRADING_RECORD_CONFIRM_RESP,
                queueMsg
        );
    }

    @Override
    public OrderDetailVo selectOrderDetail(Long id) {
        OrderInfo order = baseMapper.selectById(id);
        if(order == null){
            return null;
        }

        Map<Long, List<OrderDiscount>> discountsMap = orderDiscountService.selectDiscountsMap(Collections.singletonList(id));

        List<OrderPayRuleVo> payRules = getPayRules(order);

        String siteAddress = getSiteAddress(order);
        Map<Long, DeviceInfo> deviceMap = getDeviceMap(order.buildDeviceIds());
        List<OrderLog> orderLogs = orderLogService.selectLogs(order.getId());

        OrderDetailVo orderDetail = new OrderDetailVo()
                .setId(order.getId())
                .setSn(order.getSn())
                .setTradeSn(order.getTradeSn())
                .setOrderType(order.getOrderType())
                .setPayAmount(order.getPayAmount())
                .setChargeFee(order.getChargeFee())
                .setServiceFee(order.getServiceFee())
                .setDiscounts(discountsMap.get(id))
                .setRefundAmount(order.getRefundAmount())
                .setRefundReason(order.getRefundReason())
                .setRefundTime(order.getRefundTime())
                .setSiteAddress(siteAddress)
                .setRealChargingTime(order.getRealChargingTime())
                .setConsumeElectricity(order.getConsumeElectricity())
                .setPayType(order.getPayType())
                .setPayStatus(order.getPayStatus())
                .setRefundStatus(order.getRefundStatus())
                .setPayTime(order.getPayTime())
                .setGroupOrder(order.getGroupOrder())
                .setGroupCardNo(order.getGroupCardNo())
                .setPhone(order.getPhone())
                .setLicensePlateNumber(order.getLicensePlateNumber())
                .setProcessStep(order.getProcessStep())
                .setOrderState(order.getOrderState())
                .setOrderLogs(orderLogs)
                .setPayRuleId(order.getPayRuleId())
                .setPayModelId(order.getPayModelId())
                .setEndReasonCode(order.getEndReasonCode())
                .setEndReasonDesc(order.getEndReasonDesc())
                .setPayRules(payRules)
                .setPayStatus()
                .setOrderState()
                .setDiscountAmount();

        fillUserData(order, orderDetail);

        fillPileData(
                orderDetail,
                deviceMap.get(order.getPileId())
        );

        fillPortData(
                orderDetail,
                deviceMap.get(order.getPortId())
        );
        return orderDetail;
    }

    private List<OrderPayRuleVo> getPayRules(OrderInfo order) {
        RuleDTO rule = getRule(order);
        if(rule == null){
            return new ArrayList<>();
        }

        return getOrderPayRules(rule);
    }

    private RuleDTO getRule(OrderInfo order) {
        R<RuleDTO> ruleR = remoteRuleService.selectRule(
                order.getPayRuleId(),
                order.getPayModelId()
        );

        return ruleR.getData();
    }

    @Override
    public OrderAppDetailVO selectAppOrderDetail(Long id) {
        OrderInfo order = baseMapper.selectById(id);
        if(order == null){
            return null;
        }
        //获取站点信息
        Site site = getSite(order.getSiteId());
        Map<Long, DeviceInfo> deviceMap = getDeviceMap(order.buildDeviceIds());
        OrderAppDetailVO orderAppDetailVO = new OrderAppDetailVO();
        //订单基本信息
        if (site != null){
            orderAppDetailVO.setSiteName(site.getName());
            orderAppDetailVO.setAddress(site.getAddress());
            orderAppDetailVO.setLng(site.getLongitude()==null?0.0f:site.getLongitude().floatValue());
            orderAppDetailVO.setLat(site.getLatitude()==null?0.0f:site.getLatitude().floatValue());
        }
        orderAppDetailVO.setId(order.getId());
        if (order.getRealChargingTime()==null){
            //用现在的时间算出和充电开始时间相距多少小时
            orderAppDetailVO.setRealChargingTime(
                    new BigDecimal(
                            Duration.between(
                                    order.getRealStartTime(),
                                    LocalDateTime.now()
                            ).toHours()
                    )
            );
        }else {
            orderAppDetailVO.setRealChargingTime(order.getRealChargingTime());
        }
        orderAppDetailVO.setOrderType(order.getOrderType());
        orderAppDetailVO.setConsumeElectricity(order.getConsumeElectricity());
        orderAppDetailVO.setSn(order.getSn());
        orderAppDetailVO.setStartTime(order.getRealStartTime());
        orderAppDetailVO.setEndTime(order.getRealEndTime());
        orderAppDetailVO.setGroupOrder(order.getGroupOrder());
        orderAppDetailVO.setStatus(getAppOrderStatus(order));
        orderAppDetailVO.setUserId(order.getUserId());
        orderAppDetailVO.setIsRefund(order.getRefundStatus());
        orderAppDetailVO.setEndReason(order.getEndReasonDesc());
        //设备基本信息
        DeviceInfo pileInfo = deviceMap.get(order.getPileId());
        DeviceInfo portInfo = deviceMap.get(order.getPortId());
        if (portInfo != null){
            orderAppDetailVO.setPortId(portInfo.getDeviceId());
            orderAppDetailVO.setPortName(portInfo.getName());
        }
        if (pileInfo != null){
            orderAppDetailVO.setPileSn(pileInfo.getSn());
            orderAppDetailVO.setChargeType(pileInfo.getChargeType());
            orderAppDetailVO.setElectric(pileInfo.getElectric());
            orderAppDetailVO.setVoltage(pileInfo.getVoltage());
            orderAppDetailVO.setMaxPower(pileInfo.getMaxPower());
        }
       //支付信息
        orderAppDetailVO.setOrderAmount(order.getOrderAmount());
        orderAppDetailVO.setChargeFee(order.getChargeFee());
        orderAppDetailVO.setServiceFee(order.getServiceFee());
        orderAppDetailVO.setPayAmount(order.getPayAmount());
        orderAppDetailVO.setPayType(order.getPayType());
        orderAppDetailVO.setPayTime(order.getPayTime());
       //查询占位费
        List<OrderInfo> orderInfos = baseMapper.selectChildOrders(Collections.singletonList(order.getId()));
        orderAppDetailVO.setIsOccupyFee(0);
        if (CollectionUtil.isNotEmpty(orderInfos)){
            orderAppDetailVO.setIsOccupyFee(1);
            for (OrderInfo orderInfo : orderInfos) {
                if (Objects.equals(orderInfo.getOccupyType(), OccupyTypeEnum.BEFORE_CHARGING.getType())){
                    orderAppDetailVO.setOccupyFee(orderInfo.getPayAmount());
                    orderAppDetailVO.setOccupyFeePayStatus(orderInfo.getPayStatus());
                    orderAppDetailVO.setOccupyFeeId(orderInfo.getId());
                }
                if (Objects.equals(orderInfo.getOccupyType(), OccupyTypeEnum.AFTER_CHARGING.getType())){
                    orderAppDetailVO.setOccupyFeeAfter(orderInfo.getPayAmount());
                    orderAppDetailVO.setOccupyFeeAfterPayStatus(orderInfo.getPayStatus());
                    orderAppDetailVO.setOccupyFeeAfterId(orderInfo.getId());
                }
            }
        }
        //优惠信息
        Map<Long, List<OrderDiscount>> discountsMap = orderDiscountService.selectDiscountsMap(Collections.singletonList(id));
        List<OrderDiscount> discounts = discountsMap.get(id);
        orderAppDetailVO.setDiscounts(discounts);
        BigDecimal discountAmount =BigDecimal.ZERO;
        if(!CollectionUtils.isEmpty(discounts)){
            discountAmount = discounts.stream()
                    .map(OrderDiscount::getDiscountAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        orderAppDetailVO.setDiscountAmount(discountAmount);
        return orderAppDetailVO;
    }

    private Integer getAppOrderStatus(OrderInfo order) {
        if (order.getRealEndTime() ==null){
            return 1;
        }
        if (Objects.equals(order.getPayStatus(), PayStatusEnum.HAVE_PAID.getCode()) ||Objects.equals(order.getPayStatus(), PayStatusEnum.REFUNDED.getCode()) ){
            return 2;
        }
        if (order.getPayAmount() != null && Objects.equals(order.getPayStatus(), PayStatusEnum.NOT_PAID.getCode())){
            return 3;
        }
        return 0 ;
        }



    @Override
    public List<OrderAppLogVO> selectAppOrderLogs(Long orderId) {
        return orderLogService.selectAppLogs(orderId);
    }

    private OrderInfo payMoney(
            OrderInfo orderInfo,
            String remark
    ) {
        OrderInfo  deltOrder = updatePayAmount(orderInfo);

        LocalDateTime payTime = LocalDateTime.now();
        BigDecimal debtAmount = updateUserAccount(
                DateUtils.toDate(payTime),
                deltOrder,
                remark
        );

        return updateOrderPayStatus(
                deltOrder,
                null,
                payTime,
                debtAmount,
                PayTypeEnum.WALLET.getType()
        );
    }

    private OrderInfo updatePayAmount(
            OrderInfo orderInfo
    ) {
        if(!OrderTypeEnum.REAL_TIME.getType().equals(orderInfo.getOrderType())){
            return orderInfo;
        }

        List<OrderDiscount> discounts = orderDiscountService.selectDiscounts(orderInfo.getId());
        if(discounts.isEmpty()){
            return orderInfo;
        }

        BigDecimal payAmount = getPayAmount(
                orderInfo.getPayAmount(),
                discounts
        );

        OrderInfo updateOrder = new OrderInfo()
                .setId(orderInfo.getId())
                .setVersion(orderInfo.getVersion())
                .setPayAmount(payAmount);
        int successNum = baseMapper.updateById(updateOrder);
        if (successNum == 0) {
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }

       return baseMapper.selectById(orderInfo.getId());
    }

    private BigDecimal getPayAmount(
            BigDecimal payAmount,
            List<OrderDiscount> discounts
    ) {
        BigDecimal totalDiscountFee = getTotalDiscountFee(
                discounts
        );

        payAmount = payAmount.subtract(totalDiscountFee);
        if (payAmount.doubleValue() < 0) {
            return new BigDecimal(0);
        }

        return payAmount;
    }

    private BigDecimal getTotalDiscountFee(
            List<OrderDiscount> discounts
    ) {
        return discounts.stream()
                .map(OrderDiscount::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal updateUserAccount(
            Date payTime,
            OrderInfo orderInfo,
            String remark
    ) {
        if(orderInfo.getUserId() == null){
            return null;
        }

        if (orderInfo.getPayAmount().doubleValue() == 0) {
            return new BigDecimal(0);
        }

        SysUserAccount userAccount = userAccountService.selectAccount(
                orderInfo.getUserId(),
                UserAccountType.APP.getCode()
        );
        if(userAccount == null){
            throw new ServiceException(ErrorCodeConstants.WALLET_NOT_FOUND);
        }

        BigDecimal debtAmount = getDebtAmount(
                userAccount.getBalance(),
                orderInfo.getPayAmount()
        );

        if (debtAmount.doubleValue() > 0) {
            throw new ServiceException(ErrorCodeConstants.WALLET_NOT_SUFFICIENT);
        }

        BigDecimal balance = getBalance(orderInfo, userAccount);

        SysUserAccount updateUserAccount = new SysUserAccount()
                .setId(userAccount.getId())
                .setVersion(userAccount.getVersion())
                .setUpdateTime(payTime)
                .setBalance(balance);

        boolean success = userAccountService.updateById(updateUserAccount);
        if(!success){
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }

        saveOrderAccountRecord(
                orderInfo,
                userAccount,
                updateUserAccount,
                payTime,
                remark
        );

        return debtAmount;
    }

    private void saveOrderAccountRecord(
            OrderInfo orderInfo,
            SysUserAccount userAccount,
            SysUserAccount updateUserAccount,
            Date updateTime,
            String remark
    ) {
        SysAmountRecord orderAccountRecord = new SysAmountRecord()
                .setCardNo(orderInfo.getCardNo())
                .setBindUserId(orderInfo.getUserId())
                .setSerialNumber(orderInfo.getSn())
                .setAmount(orderInfo.getPayAmount())
                .setChangeBefore(userAccount.getBalance())
                .setChangeAfter(updateUserAccount.getBalance())
                .setChannel(RecordChannel.ACCOUNT_BALANCE.getChannel())
                .setRecordType(IncomeExpenseType.EXPENSE.getType())
                .setModule(RecordModule.APP_USER.getModule())
                .setType(AmountRecordType.ORDER_PAYMENT.getType())
                .setStatus(RecordStatus.SUCCESS.getStatus())
                .setUserType(UserAccountType.APP.getCode())
                .setRemark(remark)
                .setEventTime(updateTime);
        amountRecordService.save(orderAccountRecord);
    }

    private BigDecimal getBalance(
            OrderInfo updateOrder,
            SysUserAccount userAccount
    ) {
        BigDecimal payAmount = updateOrder.getPayAmount();
        return userAccount.getBalance()
                .subtract(payAmount);
    }

    private BigDecimal getDebtAmount(
            BigDecimal balance,
            BigDecimal payAmount
    ) {
        if(balance.compareTo(payAmount) >=0){
            return new BigDecimal(0);
        }

        if(balance.doubleValue() <= 0){
            return payAmount;
        }

        return payAmount
                .subtract(balance);
    }

    private OrderInfo updateOrderPayStatus(
            OrderInfo orderInfo,
            String payOrderId,
            LocalDateTime payTime,
            BigDecimal debtAmount,
            Integer payType
    ) {
        if(debtAmount == null){
            return orderInfo;
        }

        OrderInfo updateOrder = new OrderInfo()
                .setId(orderInfo.getId())
                .setVersion(orderInfo.getVersion())
                .setPayStatus(PayStatusEnum.HAVE_PAID.getCode())
                .setDebtAmount(debtAmount)
                .setPayTime(payTime)
                .setPayType(payType)
                .setPayOrderId(payOrderId);
        if(debtAmount.doubleValue() > 0){
            updateOrder.setDebtStatus(EnableEnum.ENABLE.getCode());
        }

        int successNum = baseMapper.updateById(updateOrder);

        if (successNum == 0) {
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }

        orderLogService.saveLog(
                orderInfo.getId(),
                payTime,
                OrderLogEnum.PAYMENT_SUCCESS
        );

        return baseMapper.selectById(orderInfo.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderPayStatus(
            OrderPaySuccessReqMsg reqMsg
    ) {
        List<OrderInfo> orderInfos = baseMapper.selectBatchIds(reqMsg.getOrderIds());
        if(orderInfos.isEmpty()){
            return;
        }

        for (OrderInfo orderInfo : orderInfos) {
            if (PayStatusEnum.HAVE_PAID.getCode().equals(orderInfo.getPayStatus())) {
                return;
            }
        }

        orderDiscountService.saveDiscounts(
                reqMsg.getConfig()
        );

        orderInfos.forEach(orderInfo -> {
            OrderInfo latestOrder = updateOrderPayStatus(
                    reqMsg,
                    orderInfo
            );

            // 订单支付后置步骤
            orderPaidAfterHandler.process(latestOrder);

            remoteAppUserService.updateFirstChargeStatus(latestOrder.getUserId());

            CompletableFuture.runAsync(() -> remoteCouponActivityService.grantCoupons(latestOrder));
        });
    }

    private OrderInfo updateOrderPayStatus(
            OrderPaySuccessReqMsg reqMsg,
            OrderInfo orderInfo
    ) {
        OrderInfo deltOrder = updatePayAmount(orderInfo);
        return updateOrderPayStatus(
                deltOrder,
                reqMsg.getPayOrderId(),
                DateUtils.toLocalDateTime(reqMsg.getPayTime()),
                new BigDecimal(0),
                PayTypeEnum.WE_CHAT.getType()
        );
    }

    @Override
    public void savePayOrderId(
            List<Long> orderIds,
            String payOrderId
    ) {
        List<OrderInfo> orderInfos = baseMapper.selectBatchIds(orderIds);
        orderInfos.forEach(orderInfo -> savePayOrderId(
                payOrderId,
                orderInfo
        ));
    }

    private void savePayOrderId(
            String payOrderId,
            OrderInfo orderInfo
    ) {
        if (PayStatusEnum.HAVE_PAID.getCode().equals(orderInfo.getPayStatus())) {
            throw new ServiceException(ErrorCodeConstants.ORDER_HAVE_PAID);
        }

        OrderInfo updateOrder = new OrderInfo()
                .setId(orderInfo.getId())
                .setVersion(orderInfo.getVersion())
                .setPayOrderId(payOrderId);
        int successNum = baseMapper.updateById(updateOrder);
        if (successNum == 0) {
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payWalletMoney(PayWalletMoneyDTO payWalletMoneyDTO) {
        payWalletMoneyDTO.getIds()
                .forEach(
                        orderNo -> {
                            OrderInfo latestOrder = payWalletMoney(
                                    orderNo,
                                    payWalletMoneyDTO.getSiteActivityIds(),
                                    payWalletMoneyDTO.getCouponIds()
                            );

                            // 订单支付后置步骤
                            orderPaidAfterHandler.process(latestOrder);

                            remoteAppUserService.updateFirstChargeStatus(latestOrder.getUserId());

                            CompletableFuture.runAsync(() -> remoteCouponActivityService.grantCoupons(latestOrder));
                        }
                );

    }

    @Override
    public OrderDiscountVo getOrderDiscount(
            OrderDiscountDTO orderDiscountDTO
    ) {
        List<OrderInfo> orderInfos = baseMapper.selectBatchIds(orderDiscountDTO.getOrderIds());

        List<OrderDiscount> allDiscounts = new ArrayList<>();
        BigDecimal payAmount = BigDecimal.ZERO;

        for (OrderInfo orderInfo : orderInfos) {
            if (orderInfo.getPayAmount() == null) {
                throw new ServiceException(ErrorCodeConstants.ORDER_DATA_ERROR);
            }

            payAmount = payAmount.add(orderInfo.getPayAmount());
            List<OrderDiscount> discounts = orderDiscountService.getDiscounts(
                    orderDiscountDTO.getSiteActivityIds(),
                    orderDiscountDTO.getCouponIds(),
                    orderInfo
            );

            allDiscounts.addAll(discounts);
        }

        payAmount = getPayAmount(
                payAmount,
                allDiscounts
        );

        Object config = getConfig(allDiscounts);
        return new OrderDiscountVo()
                .setPayAmount(payAmount)
                .setConfig(config);
    }

    @Override
    public AppOrderDiscountVo getAppOrderDiscount(AppOrderDiscountDTO orderDiscountDTO) {
        OrderInfo orderInfo = baseMapper.selectById(orderDiscountDTO.getOrderId());
        if (orderInfo == null) {
            return null;
        }

        List<OrderDiscount> discounts = orderDiscountService.getDiscounts(
                orderDiscountDTO.getSiteActivityIds(),
                orderDiscountDTO.getCouponIds(),
                orderInfo
        );

        BigDecimal totalDiscount = discounts.stream()
                .map(OrderDiscount::getDiscountAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new AppOrderDiscountVo()
                .setDiscountAmount(totalDiscount);
    }

    private Object getConfig(List<OrderDiscount> discounts) {
        discounts.forEach(OrderDiscount::setConfigObj);
        return discounts;
    }

    private OrderInfo payWalletMoney(
            Long orderId,
            List<Long> siteActivityIds,
            List<Long> couponIds
    ) {
        OrderInfo orderInfo = checkOrder(orderId);
        String payRemark = getPayRemark(orderInfo);

        orderDiscountService.saveDiscounts(
                siteActivityIds,
                couponIds,
                orderInfo
        );

        return payMoney(
                orderInfo,
                payRemark
        );
    }

    private String getPayRemark(OrderInfo orderInfo) {
        if(OrderTypeEnum.REAL_TIME.getType().equals(orderInfo.getOrderType())){
            return PayRemarkEnum.CHARGING_ORDER.getDesc();
        }

        if(OrderTypeEnum.OCCUPY.getType().equals(orderInfo.getOrderType())){
            return PayRemarkEnum.OCCUPY.getDesc();
        }

        return null;
    }

    private OrderInfo checkOrder(Long orderId) {
        OrderInfo orderInfo = baseMapper.selectById(orderId);
        if(orderInfo == null){
            throw new ServiceException(ErrorCodeConstants.ORDER_NOT_FOUND);
        }

        if (orderInfo.getPayAmount() == null) {
            throw new ServiceException(ErrorCodeConstants.ORDER_DATA_ERROR);
        }

        if (PayStatusEnum.HAVE_PAID.getCode().equals(orderInfo.getPayStatus())) {
            throw new ServiceException(ErrorCodeConstants.ORDER_HAVE_PAID);
        }
        return orderInfo;
    }

    @Override
    public OrderInfo selectUnsettledOrder(Long portId, String orderSn) {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapperX<OrderInfo>()
                .eq(OrderInfo::getPortId, portId)
                .eq(OrderInfo::getSn, orderSn)
                .eq(OrderInfo::getOrderType, OrderTypeEnum.REAL_TIME.getType())
                .lt(OrderInfo::getOrderState, OrderStateEnum.TRADING_RECORD_CONFIRM.getType());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public OrderStatisticsData selectStatisticsTotalData(OrderStatisticsQueryDTO queryDTO) {
        return baseMapper.selectStatisticsTotalData(queryDTO);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<OrderStatisticsData> selectStatisticsDeviceData(OrderStatisticsQueryDTO queryDTO) {
        return baseMapper.selectStatisticsDeviceData(queryDTO);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<OrderTrendDateData> selectDateTrendData(TrendQueryDTO queryDTO) {
        return baseMapper.selectDateTrendData(queryDTO);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<OrderTrendHourData> selectHourTrendData(TrendQueryDTO queryDTO) {
        return baseMapper.selectHourTrendData(queryDTO);
    }

    @Override
    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<OrderTrendUserData> selectUserTrendData(TrendQueryDTO queryDTO) {
        return baseMapper.selectUserTrendData(queryDTO);
    }

    @DataScope(orgAlias = "op", userSiteAlias = "us", userOperatorAlias = "uop")
    @Override
    public List<DeviceOrderStatisticsData> selectDeviceOrderStatistics(OrderStatisticsQueryDTO queryDTO) {
        return baseMapper.selectDeviceOrderStatistics(queryDTO);
    }

    @Override
    public List<OrderInfo> selectSubOrder(Long orderId) {
        return baseMapper.selectChildOrders(Collections.singletonList(orderId));
    }

    @Override
    public List<OrderInfo> selectProcessLossOrders() {
        LambdaQueryWrapper<OrderInfo> wrapper = new LambdaQueryWrapperX<OrderInfo>()
                .eq(OrderInfo::getOrderType, OrderTypeEnum.REAL_TIME.getType())
                .eq(OrderInfo::getPayStatus, CommonStatus.OK.getCode())
                .eq(OrderInfo::getProcessLoss, 1)
                .ne(OrderInfo::getProcessStep, ProcessStepEnum.FIGURED_OUT.getType());
        return baseMapper.selectList(wrapper);
    }

    @Override
    public OrderInfo selectChargeOrderByTradeNo(String tradeNo) {
        return baseMapper.selectOrderByTradeNo(tradeNo);
    }

    @Override
    public OrderInfo selectChargeOrderByOrderNo(String orderNo) {
        LambdaQueryWrapperX<OrderInfo> wrapper = new LambdaQueryWrapperX<OrderInfo>()
                .eq(OrderInfo::getSn, orderNo)
                .eq(OrderInfo::getOrderType, OrderTypeEnum.REAL_TIME.getType());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Long addOrder(AddOrderDTO order) {
        OrderState orderState = orderContext.getState(OrderStateEnum.INSERT_GUN.getType());
        return orderState.addOrder(
                order
        );
    }

    private DeviceInfo getDeviceInfo(Long deviceId) {
        R<DeviceInfo> deviceInfoR = remoteDeviceService.selectDevice(deviceId);
        return deviceInfoR.getData();
    }

    @Override
    public OrderInfo selectOrderBySn(String sn) {
        return baseMapper.selectOrderBySn(sn);
    }

    @Override
    public Boolean isChargingOrderByUserId(Long userId) {
        List<OrderInfo> orderInfoList = baseMapper.selectChargingOrderByUserId(userId);
        return CollectionUtil.isNotEmpty(orderInfoList);
    }
    @Override
    public Boolean isPayingOrderByUserId(Long userId) {
        List<OrderInfo> orderInfoList = baseMapper.selectPayingOrderByUserId(userId);
        return CollectionUtil.isNotEmpty(orderInfoList);
    }

    private List<OrderPayRuleVo> getOrderPayRules(RuleDTO ruleDTO) {
        Map<Integer, List<RuleTimeDTO>> timesMap = ruleDTO.buildTimesMap();
        return ruleDTO.getFees()
                .stream()
                .map(fee -> getOrderPayRules(
                        timesMap,
                        fee
                ))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<OrderPayRuleVo> getOrderPayRules(
            Map<Integer, List<RuleTimeDTO>> timesMap,
            RuleFeeDTO fee
    ) {
        List<RuleTimeDTO> times = timesMap.getOrDefault(fee.getType(), new ArrayList<>());
        return times.stream()
                .map(time -> new OrderPayRuleVo()
                        .setType(fee.getType())
                        .setServiceCharge(fee.getServiceCharge())
                        .setElectrovalence(fee.getElectrovalence())
                        .setTotalCost()
                        .setStartTime(time.getStartTime())
                        .setEndTime(time.getEndTime())
                )
                .collect(Collectors.toList());
    }

    private void fillUserData(
            OrderInfo order,
            OrderDetailVo orderDetail
    ) {
        AppUser appUser = getAppUser(order.getUserId());
        if(appUser == null){
            return;
        }

        orderDetail.setUserId(order.getUserId())
                .setUserName(appUser.getNickName());
    }

    private AppUser getAppUser(Long userId) {
        if(userId == null){
            return null;
        }

        R<AppUser> appUserR = remoteAppUserService.getUserInfo(userId);
        return appUserR.getData();
    }

    private void fillPortData(
            OrderDetailVo orderDetail,
            DeviceInfo port
    ) {
        if(port == null){
            return;
        }

        orderDetail.setPortId(port.getDeviceId())
                .setPortName(port.getName());
    }

    private void fillPileData(
            OrderDetailVo orderDetail,
            DeviceInfo pile
    ) {
        if(pile == null){
          return;
        }

        orderDetail.setPileId(pile.getDeviceId())
                .setPileSn(pile.getSn())
                .setPileName(pile.getName())
                .setChargeType(pile.getChargeType())
                .setElectric(pile.getElectric())
                .setVoltage(pile.getVoltage())
                .setMaxPower(pile.getMaxPower());
    }

    private Map<Long, DeviceInfo> getDeviceMap(List<Long> deviceIds) {
        R<Map<Long, DeviceInfo>> deviceMapR = remoteDeviceService.selectDeviceMap(deviceIds);
        return deviceMapR.getData();
    }

    private String getSiteAddress(OrderInfo order) {
        Site site = getSite(order.getSiteId());
        if(site == null){
            return null;
        }
        return site.getAddress();
    }

    private Site getSite(Long siteId) {
        R<Site> siteR = remoteSiteService.selectSite(siteId);
        return siteR.getData();
    }
}
