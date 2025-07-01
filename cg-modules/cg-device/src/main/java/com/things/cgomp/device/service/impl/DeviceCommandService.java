package com.things.cgomp.device.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.pojo.device.DeviceCommandEnum;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;
import com.things.cgomp.common.device.pojo.device.StartChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.StopChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.device.enums.PortStatusEnum;
import com.things.cgomp.device.data.api.RemoteDeviceStatusService;
import com.things.cgomp.device.service.IDeviceCommandService;
import com.things.cgomp.device.service.IDeviceInfoService;
import com.things.cgomp.device.service.IDevicePushService;
import com.things.cgomp.device.api.model.vo.OrderInfoVO;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.dto.AddOrderDTO;
import com.things.cgomp.order.api.enums.PayTypeEnum;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.device.api.dto.RuleFeeDTO;
import com.things.cgomp.device.api.dto.RuleTimeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.things.cgomp.device.enums.ErrorCodeConstants.*;

@Slf4j
@Service
public class DeviceCommandService implements IDeviceCommandService {

    @Autowired
    private IDevicePushService devicePushService;
    @Autowired
    private IDeviceInfoService deviceInfoService;
    @Autowired(required = false)
    private RemoteRuleService remoteRuleService;
    @Autowired(required = false)
    private RemoteOrderService remoteOrderService;
    @Autowired(required = false)
    private RemoteDeviceStatusService remoteDeviceStatusService;


    @Override
    public DeviceCommandVO startCharging(StartChargingConfigDTO config ) {
        PushResult pushResult;
        Long portId = config.getPortId();
        //获取支付方式
        if (checkBalance(config)){
            return DeviceCommandVO.builder().code(INSUFFICIENT_BALANCE.getCode()).succeed(false).errorMsg(INSUFFICIENT_BALANCE.getMsg()).build();
        }
//        Long chargePeriod = config.getChargePeriod();
//        if (0!=chargePeriod){
//            //校验余额
//            BigDecimal accountBalance = new BigDecimal(config.getAccountBalance());
//            DeviceCommandVO validationResult = validateChargingCost(portId, chargePeriod, accountBalance);
//            if (!validationResult.isSucceed()) {
//                return validationResult;
//            }
//        }
        //判断逻辑 先判断是否存在充电订单，在判断是否插枪
        //判断当前用户订单
        DeviceCommandVO commandVO = checkUserOrder(config);
        if (!commandVO.isSucceed()){
            return commandVO;
        }
        //判断当前枪状态
        DeviceInfo deviceInfo = deviceInfoService.selectPortDevice(portId);
        Integer portStatus = deviceInfo.getPortStatus();
        if (!PortStatusEnum.LOADED.getType().equals(portStatus) & !PortStatusEnum.OCCUPY.getType().equals(portStatus)){
            PortStatusEnum portStatusEnum = PortStatusEnum.getPortStatusEnum(portStatus);
            log.error("当前枪不属于插枪状态,当前枪状态:{}", portStatusEnum.getDescription());
            return DeviceCommandVO.builder().code(NO_GUN_DETECTED.getCode()).succeed(false).errorMsg(NO_GUN_DETECTED.getMsg()).build();
        }
        //获取订单号
        String orderSn = deviceInfo.getOrderSn();
        //判断订单号是否为空或者为32位0
        String zero = StringUtils.leftPad("", 32, '0');
        if (StringUtils.isEmpty(orderSn) || zero.equals(orderSn))  {
            R<String> orderSnR = remoteOrderService.generateOrderSn(portId, System.currentTimeMillis());
            if (orderSnR == null || orderSnR.getCode() != 200 || StringUtils.isEmpty(orderSnR.getData())) {
                log.error("生成订单号失败:{}", orderSnR);
                return DeviceCommandVO.builder().code(GlobalErrorCodeConstants.NETWORK_ERROR.getCode())
                        .succeed(false).errorMsg(GlobalErrorCodeConstants.NETWORK_ERROR.getMsg()).build();
            }
            orderSn =  orderSnR.getData();
        }
        //判断该订单号是否已存在过
        R<OrderInfo> chargeOrderByTradeNoR = remoteOrderService.getChargeOrderByTradeNo(orderSn, SecurityConstants.INNER);
        if (chargeOrderByTradeNoR  == null || chargeOrderByTradeNoR.getCode() != 200 ) {
            log.error("判断该订单号是否已存在过,查询失败:{}", chargeOrderByTradeNoR ==null?null:chargeOrderByTradeNoR.getMsg());
            return DeviceCommandVO.builder().code(GlobalErrorCodeConstants.NETWORK_ERROR.getCode())
                    .succeed(false).errorMsg(GlobalErrorCodeConstants.NETWORK_ERROR.getMsg()).build();
        }
        if (chargeOrderByTradeNoR.getData() != null){
            log.error("该订单号已存在过,请勿重复充电:{}", orderSn);
            return DeviceCommandVO.builder().code(PLEASE_REINSERT_THE_GUN.getCode()).succeed(false).errorMsg(PLEASE_REINSERT_THE_GUN.getMsg()).build();
        }
        config.setOrderNo(orderSn);
        //开始下发充电
        try{

            pushResult = devicePushService.push(null,
                    config.getPortId(),
                    DeviceCommandEnum.startCharge,
                    config);
        if (pushResult != null && pushResult.getSucceed()){
            //创建订单
            if (!creatChargeOrder(config, portId,pushResult)){
                log.error("创建订单失败:系统停止充电");
                StopChargingConfigDTO stopChargingConfigDTO = new StopChargingConfigDTO();
                stopChargingConfigDTO.setPortId(portId);
                this.stopCharging(stopChargingConfigDTO);
                return DeviceCommandVO.builder().code(FAILED_CREATE_ORDER.getCode()).succeed(false).errorMsg(FAILED_CREATE_ORDER.getMsg()).build();

            }
        }
        }catch (Exception e){

            if(e instanceof ServiceException){
                return DeviceCommandVO.builder().succeed(false).errorMsg(e.getMessage()).build();
            }else{
                return DeviceCommandVO.builder().succeed(false).code(CHARGE_FAIL.getCode()).errorMsg(CHARGE_FAIL.getMsg()).build();
            }

        }

       return getCommandVO(pushResult);
    }

    private static boolean checkBalance(StartChargingConfigDTO config) {
        Integer paymentType = config.getPaymentType();
        //判断当前余额
        if (PayTypeEnum.WALLET.getType().equals(paymentType)){
            if (config.getAccountBalance() == null || config.getAccountBalance().compareTo(new Double("1.00")) < 0) {
                log.error("用户余额不足，userId：{}，balance:{}", config.getUserId(), config.getAccountBalance());
                return true;
            }
        }
        return false;
    }

    private DeviceCommandVO checkUserOrder(StartChargingConfigDTO config) {
        R<Boolean> isChargingOrderByUserId = remoteOrderService.isChargingOrderByUserId(config.getUserId(),SecurityConstants.INNER);
        if (isChargingOrderByUserId == null || isChargingOrderByUserId.getCode() != 200) {
            log.error("判断当前用户是否存在其他正在充电订单失败:{}", isChargingOrderByUserId != null ? isChargingOrderByUserId.getMsg() : null);
            return DeviceCommandVO.builder().code(GlobalErrorCodeConstants.NETWORK_ERROR.getCode())
                    .succeed(false).errorMsg(GlobalErrorCodeConstants.NETWORK_ERROR.getMsg()).build();
        }
        if (isChargingOrderByUserId.getData()){
            log.error("当前用户存在正在充电订单");
            return DeviceCommandVO.builder().code(HAS_CHARGING_ORDER.getCode()).succeed(false).errorMsg(HAS_CHARGING_ORDER.getMsg()).build();
        }
        //判断当前用户是否存在待支付订单
        R<Boolean> payingOrderByUserId = remoteOrderService.isPayingOrderByUserId(config.getUserId(), SecurityConstants.INNER);
        if (payingOrderByUserId == null || payingOrderByUserId.getCode() != 200) {
            log.error("判断当前用户是否存在其他待支付订单失败:{}", payingOrderByUserId != null ? payingOrderByUserId.getMsg() : null);
            return DeviceCommandVO.builder().code(GlobalErrorCodeConstants.NETWORK_ERROR.getCode())
                    .succeed(false).errorMsg(GlobalErrorCodeConstants.NETWORK_ERROR.getMsg()).build();
        }
        if (payingOrderByUserId.getData()){
            log.error("当前用户存在待支付订单");
            return DeviceCommandVO.builder().code(HAS_PAYING_ORDER.getCode()).succeed(false).errorMsg(HAS_PAYING_ORDER.getMsg()).build();
        }
        return DeviceCommandVO.builder().succeed(true).build();
    }

    private boolean creatChargeOrder(StartChargingConfigDTO config, Long portId,PushResult pushResult) {
        //获取插枪时间和订单号
        R<DevicePortStatus> portRealStatus = remoteDeviceStatusService.getPortRealStatus(portId, SecurityConstants.INNER);
        if (portRealStatus == null || portRealStatus.getCode() != 200 || portRealStatus.getData() == null) {
            log.error("获取插枪时间失败:{}", portRealStatus != null ? portRealStatus.getMsg() : null);
            return false;
        }
        DevicePortStatus portRealStatusData = portRealStatus.getData();
        //创建订单
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setSn(config.getOrderNo());
        addOrderDTO.setOrderType(0);
        addOrderDTO.setOrderSource(1);
        addOrderDTO.setPayType(config.getPaymentType());
        addOrderDTO.setBillType(1);
        addOrderDTO.setPortId(config.getPortId());
        addOrderDTO.setStartTime(System.currentTimeMillis());
        addOrderDTO.setDeviceType(4);
        addOrderDTO.setUserId(config.getUserId());
        addOrderDTO.setLicensePlateNumber(config.getLicensePlateNumber());
        addOrderDTO.setVin(portRealStatusData.getVin());
        addOrderDTO.setInsertTime(portRealStatusData.getPortInsertedTime());
        addOrderDTO.setPhone(config.getPhone());
        R<Long> orderR = remoteOrderService.addOrder(addOrderDTO);
        //如果不成功 重试3次创建订单。3次之后不成功后停止充电抛出异常

        if (orderR == null || orderR.getCode() != 200) {
            boolean orderFlag = false;
            for (int i = 0; i < 3; i++) {
                orderR = remoteOrderService.addOrder(addOrderDTO);
                if (orderR != null && orderR.getCode() == 200) {
                    orderFlag=true;
                    break;
                }
            }
            //创建订单不成功,停止充电抛出异常
            if (!orderFlag){
                return false;
            }
        }
        //封装订单信息
        OrderInfoVO orderInfoVO = new OrderInfoVO();
        orderInfoVO.setId(orderR.getData());
        orderInfoVO.setTradeSn(config.getOrderNo());
        pushResult.setData(orderInfoVO);
        return true;
    }

    /**
     * 校验用户余额是否充足
     * @param portId
     * @param chargePeriod
     * @param accountBalance
     * @return
     */
    private DeviceCommandVO validateChargingCost(Long portId, Long chargePeriod, BigDecimal accountBalance) {
        //校验当前余额是否充足
        DeviceInfo deviceInfo = deviceInfoService.selectPortDevice(portId);
        //获取父级充电桩信息
        DeviceInfo pileDeviceInfo = deviceInfoService.getBaseMapper().selectById(deviceInfo.getParentId());
        if (pileDeviceInfo == null || pileDeviceInfo.getMaxPower() == null) {
            log.error("当前设备未查询到父级设备,查询设备端口号为:{}",portId);
            return DeviceCommandVO.builder().code(DEVICE_NOT_EXIST.getCode()).succeed(false).errorMsg(DEVICE_NOT_EXIST.getMsg()).build();
        }
        String maxPower = pileDeviceInfo.getMaxPower();
        //获取计费规则
        if (pileDeviceInfo.getPayRuleId() == null) {
            log.error("当前桩未配置计费规则,查询桩设备号为:{}",pileDeviceInfo.getDeviceId());
            return DeviceCommandVO.builder().code(NOT_SET_BILLING_RULES.getCode()).succeed(false)
                    .errorMsg(NOT_SET_BILLING_RULES.getMsg()).build();
        }
        R<RuleDTO> ruleDTOR;
        try {
            ruleDTOR = remoteRuleService.selectRule(pileDeviceInfo.getPayRuleId());
        } catch (Exception e) {
            log.error("远程服务调用异常：{}", e.getMessage());
            return DeviceCommandVO.builder().code(GlobalErrorCodeConstants.NETWORK_ERROR.getCode())
                    .succeed(false).errorMsg(GlobalErrorCodeConstants.NETWORK_ERROR.getMsg()).build();
        }
        if (ruleDTOR == null || 200 != ruleDTOR.getCode()) {
            return DeviceCommandVO.builder().code(GlobalErrorCodeConstants.NETWORK_ERROR.getCode())
                    .succeed(false).errorMsg(GlobalErrorCodeConstants.NETWORK_ERROR.getMsg()).build();
        }
        RuleDTO ruleDTO = ruleDTOR.getData();
        if (ruleDTO.getFees() == null || ruleDTO.getFees().isEmpty()) {
            log.error("当前计费规则未设置费用规则,查询计费规则为:{}",ruleDTO);
            return DeviceCommandVO.builder().code(NOT_SET_BILLING_RULES.getCode()).succeed(false).errorMsg(NOT_SET_BILLING_RULES.getMsg()).build();
        }
        //计算开始充电时间和结束充电时间
        Date startDate = new Date();
        String currentStartTime = DateUtil.format(startDate, "HH:mm");
        Date endDate = DateUtil.offsetHour(startDate, Math.toIntExact(chargePeriod));
        String currentEndTime = DateUtil.format(endDate, "HH:mm");
        List<RuleTimeDTO> times = ruleDTO.getTimes();
        List<RuleFeeDTO> fees = ruleDTO.getFees();
        //转换成map
        Map<Integer, RuleFeeDTO> feeMap = fees.stream().collect(Collectors.toMap(RuleFeeDTO::getType, Function.identity()));
        //根据开始时间进行排序
        times.sort(Comparator.comparing(RuleTimeDTO::getStartTime));
        if (times.isEmpty()) {
            log.error("当前计费规则未设置时间段,查询计费规则为:{}",ruleDTO);
            return DeviceCommandVO.builder().code(NOT_SET_BILLING_RULES.getCode()).succeed(false).errorMsg(NOT_SET_BILLING_RULES.getMsg()).build();

        }
        //找到符合当前时间段的集合
        List<RuleTimeDTO> overlappingTimeSlots = new ArrayList<>();
        for (RuleTimeDTO ruleTimeDTO : times) {
            if (ruleTimeDTO.getStartTime().compareTo(currentEndTime) <= 0 &&
                    ruleTimeDTO.getEndTime().compareTo(currentStartTime) >= 0) {
                //如果开始时间大于时间段的开始时间 则替换掉
                if (ruleTimeDTO.getStartTime().compareTo(currentStartTime) < 0) {
                    ruleTimeDTO.setStartTime(currentStartTime);
                }
                //如果结束时间小于时间段的结束时间 则替换掉
                if (ruleTimeDTO.getEndTime().compareTo(currentEndTime) > 0) {
                    ruleTimeDTO.setEndTime(currentEndTime);
                }
                overlappingTimeSlots.add(ruleTimeDTO);
            }
        }
        if (overlappingTimeSlots.isEmpty()) {
            log.error("当前计费规则未设置时间段,查询计费规则为:{}",ruleDTO);
            return DeviceCommandVO.builder().code(NOT_SET_BILLING_RULES.getCode()).succeed(false).errorMsg(NOT_SET_BILLING_RULES.getMsg()).build();

        }
        //根据最大功率计算从开始到结束所有时间段需要充电的费用
        BigDecimal total = new BigDecimal(0);
        for (RuleTimeDTO ruleTimeDTO : overlappingTimeSlots) {
            Integer type = ruleTimeDTO.getType();
            RuleFeeDTO ruleFeeDTO = feeMap.get(type);
            //计算当前时间段用时是多少小时
            long time = DateUtil.between(DateUtil.parse(ruleTimeDTO.getStartTime(), "HH:mm"), DateUtil.parse(ruleTimeDTO.getEndTime(), "HH:mm"), DateUnit.HOUR);
            //根据功率计算出用电多少度
            double power = Double.parseDouble(maxPower) * time;
            //得到服务费和电费总和的价格计算当前时间段总费用
            BigDecimal electrovalence = ruleFeeDTO.getElectrovalence();
            BigDecimal serviceCharge = ruleFeeDTO.getServiceCharge();
            BigDecimal totalSub = (serviceCharge.add(electrovalence)).multiply(new BigDecimal(power));
            total = total.add(totalSub).setScale(2, RoundingMode.HALF_UP);
        }
        //判断用户余额是否充足
        if (total.compareTo(accountBalance) > 0) {
            return DeviceCommandVO.builder().code(INSUFFICIENT_BALANCE.getCode()).succeed(false).errorMsg(INSUFFICIENT_BALANCE.getMsg()).build();
        }
        return DeviceCommandVO.builder().succeed(true).build();
    }


    private DeviceCommandVO getCommandVO(PushResult pushResult){
        DeviceCommandVO commandVO = DeviceCommandVO.builder()
                .code(pushResult.getCode())
                .succeed(pushResult.getSucceed())
                .responseData(pushResult.getData())
                .build();
        if(!pushResult.getSucceed()){
            commandVO.setErrorMsg(pushResult.getCodeMsg());
        }

        return commandVO;
    }




    @Override
    public DeviceCommandVO stopCharging(StopChargingConfigDTO config) {
        PushResult pushResult;
        try{

            pushResult = devicePushService.push(null,
                    config.getPortId(),
                    DeviceCommandEnum.stopCharge,
                    config);

        }catch (Exception e){

            if(e instanceof ServiceException){
                return DeviceCommandVO.builder().succeed(false).errorMsg(e.getMessage()).build();
            }else{

                return DeviceCommandVO.builder().succeed(false).errorMsg("停止充电失败").build();
            }

        }

        return getCommandVO(pushResult);
    }
}
