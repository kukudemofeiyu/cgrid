package com.things.cgomp.device.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.CustomThreadFactory;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.device.dao.td.domain.DeviceBmsDemandData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.enums.ComponentEnum;
import com.things.cgomp.common.device.enums.PortStatusEnum;
import com.things.cgomp.common.device.enums.PortStatusOperate;
import com.things.cgomp.common.device.pojo.device.*;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.device.api.dto.UpdateChargeGridRulesDTO;
import com.things.cgomp.device.api.model.vo.ChargingAppPortDetailVO;
import com.things.cgomp.device.api.model.vo.PortInfo;
import com.things.cgomp.device.convert.DeviceConvert;
import com.things.cgomp.device.data.api.RemoteDeviceDataService;
import com.things.cgomp.device.data.api.RemoteDeviceStatusService;
import com.things.cgomp.device.data.api.domain.DevicePortAllHistoryData;
import com.things.cgomp.device.data.api.domain.DevicePortAllRealData;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import com.things.cgomp.device.dto.device.*;
import com.things.cgomp.device.enums.DeviceStatusEnum;
import com.things.cgomp.device.enums.ErrorCodeConstants;
import com.things.cgomp.device.mapper.SiteMapper;
import com.things.cgomp.device.service.IDeviceInfoService;
import com.things.cgomp.device.service.IDevicePortStatusDbService;
import com.things.cgomp.device.service.IDevicePushService;
import com.things.cgomp.device.vo.device.*;
import com.things.cgomp.device.vo.device.trend.TrendData;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.api.RemoteRuleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;
import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * <p>
 * 充电桩设备表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Slf4j
@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements IDeviceInfoService {

    private static final int MAX_PORT_NUM = 99;

    @Resource
    private DeviceInfoMapper deviceInfoMapper;
    @Resource
    private RemoteRuleService remoteRuleService;
    @Resource
    private RemoteOrderService remoteOrderService;
    @Resource
    private RemoteDeviceStatusService remoteDeviceStatusService;
    @Resource
    private SiteMapper siteService;
    @Autowired
    private IDevicePushService devicePushService;
    @Resource
    private IDevicePortStatusDbService devicePortStatusDbService;
    @Resource
    private RemoteDeviceDataService remoteDeviceDataService;

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            10,
            50,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(5000),
            CustomThreadFactory.forName("push-command_"));

    @Override
    public String insertGun(
            Long portId,
            Long insertTime,
            String vin
    ) {
        String orderSn = generateOrderSn(
                portId,
                insertTime
        );
        if(StringUtils.isBlank(orderSn)){
            throw new ServiceException(ErrorCodeConstants.FAILED_TO_GENERATE_ORDER_SN);
        }

        // 修改充电枪状态
        DevicePortStatusDTO statusReq = DevicePortStatusDTO.builder()
                .portId(portId)
                .vin(vin)
                .orderSn(orderSn)
                .eventTime(insertTime).build();
        remoteDeviceStatusService.checkAndModifyPortStatus(statusReq, PortStatusOperate.inserted.name(), SecurityConstants.INNER);
        return orderSn;
    }

    private String generateOrderSn(
            Long portId,
            Long insertTime
    ) {
        R<String> orderSnR = remoteOrderService.generateOrderSn(
                portId,
                insertTime
        );

        return orderSnR.getData();
    }

    @Override
    public Map<Long, DeviceInfo> selectDeviceMap(List<Long> deviceIds) {
        if(CollectionUtils.isEmpty(deviceIds)){
            return new HashMap<>();
        }

        List<DeviceInfo> devices = baseMapper.selectBatchIds(deviceIds);
        return devices.stream()
                .collect(Collectors.toMap(
                        DeviceInfo::getDeviceId,
                        a -> a,
                        (a, b) -> a
                ));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addChargeGrid(ChargeGridDTO chargeGridDTO) {
        checkPortNum(chargeGridDTO.getPortNum());

        DeviceInfo checkDevice = CheckSN(chargeGridDTO.getSn(), null);
        if (null != checkDevice) {
            throw exception(ErrorCodeConstants.SN_REPEAT);
        }

        Site site = siteService.selectById(chargeGridDTO.getSiteId());

        RuleDTO defaultRule = getDefaultRule(site.getOperatorId());

        DeviceInfo deviceInfo = new DeviceInfo().setChargeType(chargeGridDTO.getChargeType())
                .setComponent(0).setOperatorId(site.getOperatorId()).setCreateTime(LocalDateTime.now())
                .setCreateBy(SecurityUtils.getUserId()).setElectric(chargeGridDTO.getElectric())
                .setStatus(chargeGridDTO.getStatus()).setIsFree(chargeGridDTO.getIsFree())
                .setMaxPower(chargeGridDTO.getMaxPower()).setName(chargeGridDTO.getName())
                .setPortNum(chargeGridDTO.getPortNum()).setSiteId(chargeGridDTO.getSiteId())
                .setSn(chargeGridDTO.getSn()).setVoltage(chargeGridDTO.getVoltage())
                .setSim(chargeGridDTO.getSim())
                .setSimExpire(chargeGridDTO.getSimExpire());

        if(defaultRule != null){
            deviceInfo.setPayRuleId(defaultRule.getId())
                    .setPayModelId(defaultRule.getModelId());
        }

        deviceInfoMapper.insert(deviceInfo);

        //更新通信id
        deviceInfo.setConnectDeviceId(deviceInfo.getDeviceId());
        deviceInfoMapper.updateById(deviceInfo);

        addChargePorts(
                chargeGridDTO.getPortNum(),
                deviceInfo
        );

        return deviceInfo.getDeviceId();
    }

    private void checkPortNum(Integer portNum) {
        if(portNum != null && portNum > MAX_PORT_NUM){
            throw new ServiceException(ErrorCodeConstants.PORT_NUM_TOO_LARGE);
        }
    }

    private RuleDTO getDefaultRule(Long operatorId) {
        R<RuleDTO> defaultRuleR = remoteRuleService.selectDefaultRule(operatorId);
        if(!defaultRuleR.success()){
            throw new ServiceException(ErrorCodeConstants.DEFAULT_OPERATOR_RULE_FAILS_QUERY);
        }

        return defaultRuleR.getData();
    }

    /**
     * 新增枪口
     * @param portNum
     * @param deviceInfo
     */
    private void addChargePorts(
            Integer portNum,
            DeviceInfo deviceInfo
    ) {
        if (portNum == null || portNum <= 0) {
            return;
        }

        Stream.iterate(1, n -> n + 1)
                .limit(portNum)
                .map(aliasSn -> getChargePortDTO(deviceInfo, aliasSn))
                .forEach(this::addChargePort);
    }

    private ChargePortDTO getChargePortDTO(
            DeviceInfo deviceInfo,
            Integer aliasSn
    ) {
        String name = getPortName(aliasSn);
        return new ChargePortDTO()
                .setName(name)
                .setStatus(1)
                .setPileId(deviceInfo.getDeviceId())
                .setChargeType(deviceInfo.getChargeType())
                .setAliasSn(String.format("%02d", aliasSn));
    }

    @NotNull
    private String getPortName(Integer aliasSn) {
        char index = (char) ('A' + (aliasSn -1));
        return index + "枪";
    }

    public DeviceInfo CheckSN(String sn ,Long productId) {
        DeviceInfo deviceInfoDTO = new DeviceInfo();
        deviceInfoDTO.setSn(sn);
        DeviceInfo deviceInfo = deviceInfoMapper.findDeviceInfo(deviceInfoDTO);
        return deviceInfo;
    }

    @Override
    public Boolean updateChargeGrid(UpdateChargeGridDTO chargeGridDTO) {
        DeviceInfo deviceInfo = new DeviceInfo()
                .setDeviceId(chargeGridDTO.getDeviceId())
                .setName(chargeGridDTO.getName())
                .setChargeType(chargeGridDTO.getChargeType())
                .setElectric(chargeGridDTO.getElectric())
                .setVoltage(chargeGridDTO.getVoltage())
                .setMaxPower(chargeGridDTO.getMaxPower())
                .setStatus(chargeGridDTO.getStatus())
                .setIsFree(chargeGridDTO.getIsFree())
                .setProductId(chargeGridDTO.getProductId());
        baseMapper.updateById(deviceInfo);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteChargeGrid(Long deviceId) {
        deleteDevice(deviceId);

        deleteChildDevices(deviceId);
    }

    private void deleteChildDevices(Long deviceId) {
        List<DeviceInfo> childDevices = deviceInfoMapper.selectDevicesByParentId(deviceId);
        childDevices.forEach(childDevice-> deleteDevice(childDevice.getDeviceId()));
    }

    private void deleteDevice(Long deviceId) {
        DeviceInfo deviceInfo = new DeviceInfo()
                .setDeviceId(deviceId)
                .setStatus(DeviceStatusEnum.DELETED.getType());
        deviceInfoMapper.updateById(deviceInfo);
    }

    @Override
    public void updateRule(UpdateChargeGridRuleDTO chargeGridDTO) {
        RuleDTO ruleDTO = getRule(chargeGridDTO.getPayRuleId());
        if(ruleDTO == null){
            throw new ServiceException(ErrorCodeConstants.PAY_RULE_NOT_FOUND);
        }
        DeviceInfo deviceInfo = new DeviceInfo()
                .setDeviceId(chargeGridDTO.getDeviceId())
                .setPayRuleId(chargeGridDTO.getPayRuleId())
                .setPayModelId(ruleDTO.getModelId());
        baseMapper.updateById(deviceInfo);

        sendSetPayRuleCommand(chargeGridDTO.getDeviceId(), chargeGridDTO.getPayRuleId(), ruleDTO.getModelId(), ruleDTO);

    }

    /**
     * 下发设置计费模型指令到充电桩设备
     * @param deviceId
     * @param payRuleId
     * @param modelId
     * @param ruleDTO
     */
    private void sendSetPayRuleCommand(Long deviceId, Long payRuleId, Integer modelId,  RuleDTO ruleDTO) {
        try {
            PushResult push = devicePushService.push(deviceId, null, DeviceCommandEnum.setChargeFeeModel, ruleDTO);
            log.info("下发计费模型回复, success={}, deviceId={}, error={}",push.getSucceed(), deviceId, push.getCodeMsg());
            if(push.getSucceed()){
                // 更新当前计费模型规则id
                DeviceInfo deviceInfo = new DeviceInfo()
                        .setDeviceId(deviceId)
                        .setCurrentPayRuleId(payRuleId)
                        .setCurrentPayModelId(modelId);
                baseMapper.updateById(deviceInfo);
            }

        } catch (Exception e) {
            log.error("下发计费模型失败, deviceId={}", deviceId, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRules(
            UpdateChargeGridRulesDTO chargeGridDTO
    ) {
        List<DeviceInfo> deviceInfos = baseMapper
                .selectDevicesByPayRuleId(chargeGridDTO.getOldPayRuleId());

        deviceInfos.stream()
                .map(DeviceInfo::getDeviceId)
                .forEach(deviceId -> {
                    DeviceInfo deviceInfo = new DeviceInfo()
                            .setDeviceId(deviceId)
                            .setPayRuleId(chargeGridDTO.getNewPayRuleId())
                            .setPayModelId(chargeGridDTO.getNewPayModelId());
                    baseMapper.updateById(deviceInfo);
                });

        deviceInfos.stream()
                .map(DeviceInfo::getDeviceId)
                .forEach(deviceId -> {
                    try {
                        executor.submit(() -> {
                            sendSetPayRuleCommand(deviceId, chargeGridDTO.getNewPayRuleId(),
                                    chargeGridDTO.getNewPayModelId(), chargeGridDTO.getRuleDTO());
                        });
                    } catch (RejectedExecutionException e) {
                        log.error("sendSetPayRuleCommand rejected, deviceId={} , ", deviceId ,e);
                    }
                });
    }

    private RuleDTO getRule(Long payRuleIdO) {
        R<RuleDTO> ruleR = remoteRuleService.selectRule(payRuleIdO);
        return ruleR.getData();
    }

    @Override
    public ChargeGridVo getChargeGridDetail(Long deviceId) {
        DeviceInfo deviceInfo = baseMapper.selectById(deviceId);
        if(deviceInfo == null){
            return null;
        }

        Integer portNum = baseMapper.countDevices(deviceId);

        return new ChargeGridVo()
                .setDeviceId(deviceInfo.getDeviceId())
                .setChargeType(deviceInfo.getChargeType())
                .setElectric(deviceInfo.getElectric())
                .setOperatorId(deviceInfo.getOperatorId())
                .setProductId(deviceInfo.getProductId())
                .setSiteId(deviceInfo.getSiteId())
                .setStatus(deviceInfo.getStatus())
                .setMaxPower(deviceInfo.getMaxPower())
                .setVoltage(deviceInfo.getVoltage())
                .setName(deviceInfo.getName())
                .setIsFree(deviceInfo.getIsFree())
                .setPortNum(portNum)
                .setSn(deviceInfo.getSn());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long addChargePort(ChargePortDTO addChargePort) {

        DeviceInfo queryExistDevice = new DeviceInfo();
        queryExistDevice.setParentId(addChargePort.getPileId());
        queryExistDevice.setAliasSn(addChargePort.getAliasSn());
        DeviceInfo existDevice = deviceInfoMapper.findDeviceInfo(queryExistDevice);
        if(existDevice != null){
            throw new ServiceException(ErrorCodeConstants.HAS_PORT_SN);
        }

        DeviceInfo gridDevice = deviceInfoMapper.selectById(addChargePort.getPileId());

        DeviceInfo deviceInfo = new DeviceInfo()
                .setAliasSn(String.format("%02d", Integer.valueOf(addChargePort.getAliasSn())))
                .setChargeType(addChargePort.getChargeType())
                .setName(addChargePort.getName())
                .setConnectDeviceId(addChargePort.getPileId())
                .setParentId(addChargePort.getPileId())
                .setAncestorIds(String.valueOf(addChargePort.getPileId()))
                .setSiteId(gridDevice.getSiteId())
                .setCreateBy(SecurityUtils.getUserId())
                .setCreateTime(LocalDateTime.now())
                .setStatus(DeviceStatusEnum.AVAILABLE.getType())
                .setComponent(ComponentEnum.PORT.getType())
                .setOperatorId(gridDevice.getOperatorId()).setStatus(addChargePort.getStatus());

        deviceInfoMapper.insert(deviceInfo);
        return deviceInfo.getDeviceId();
    }

    @Override
    public void editChargePort(ChargePortDTO chargePortDTO) {
        DeviceInfo deviceInfo = new DeviceInfo()
                .setDeviceId(chargePortDTO.getDeviceId())
                .setStatus(chargePortDTO.getStatus())
                .setName(chargePortDTO.getName());
        baseMapper.updateById(deviceInfo);
    }

    @Override
    public void deleteChargePort(Long deviceId) {
        DeviceInfo deviceInfo = new DeviceInfo()
                .setDeviceId(deviceId)
                .setStatus(DeviceStatusEnum.DELETED.getType());
        baseMapper.updateById(deviceInfo);
    }

    @Override
    public ChargePortDTO getChargePort(Long deviceId) {
        DeviceInfo deviceInfo = baseMapper.selectById(deviceId);
        if(deviceInfo == null){
            return null;
        }

        return new ChargePortDTO()
                .setDeviceId(deviceInfo.getDeviceId())
                .setName(deviceInfo.getName())
                .setChargeType(deviceInfo.getChargeType())
                .setAliasSn(deviceInfo.getAliasSn())
                .setPileId(deviceInfo.getParentId())
                .setStatus(deviceInfo.getStatus());
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public PageInfo<DeviceGridVo> selectCgPage(DeviceGridDTO deviceListDTO) {
        startPage();
        List<DeviceGridVo> list = deviceInfoMapper.selectCgPage(deviceListDTO);
        return new PageInfo<>(list);
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public PageInfo<DevicePortVo> selectPortPage(DevicePortDTO deviceListDTO) {
        startPage();
        List<DevicePortVo> list = deviceInfoMapper.selectDevicePorts(deviceListDTO);
        return new PageInfo<>(list);
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<SimpleDeviceVo> selectDevices(DeviceGridDTO deviceListDTO) {
        return deviceInfoMapper.selectSimpleDeviceGrids(deviceListDTO);
    }

    @Override
    public List<DeviceInfo> selectDevices(List<Long> siteIds) {
        if(CollectionUtils.isEmpty(siteIds)){
            return new ArrayList<>();
        }
        return deviceInfoMapper.selectDevices(siteIds);
    }

    @Override
    public ChargingAppPortDetailVO getAppChargePort(Long portId) {
        //查找桩端口基本信息
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(portId);
        //查找父级信息
        return getChargingAppPortDetailVO(deviceInfo, true);
    }
    @Override
    public ChargingAppPortDetailVO getAppChargePortBySn(String sn) {
        //查找桩端口基本信息
        DeviceInfo deviceInfo = deviceInfoMapper.selectBySn(sn);
        return getChargingAppPortDetailVO(deviceInfo,false);
    }

    @Override
    public List<DeviceGridVo> selectDeviceByIds(List<Long> deviceIds) {
        return deviceInfoMapper.selectDeviceByIds(deviceIds);
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public DeviceCount selectDeviceCount(DeviceCountReqDTO reqDTO) {
        return deviceInfoMapper.selectDeviceCount(reqDTO);
    }

    @Override
    public DeviceGridDetailVO selectDeviceGridSpecificDetail(Long deviceId) {
        // 设备详情信息
        DeviceDTO deviceDTO = deviceInfoMapper.selectDeviceById(deviceId);
        DeviceGridDetailVO deviceGridDetail = DeviceConvert.INSTANCE.convertGridDetail(deviceDTO);
        // 查询枪口信息
        List<DevicePortVo> childPorts = deviceInfoMapper.selectDevicePortsByParentId(deviceId);
        if (childPorts == null) {
            childPorts = new ArrayList<>();
        }
        deviceGridDetail.setPorts(childPorts);
        return deviceGridDetail;
    }

    @Override
    public DeviceDTO selectDevicePortSpecificDetail(Long deviceId) {
        return deviceInfoMapper.selectDeviceById(deviceId);
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<DeviceSimpleTreeVO> selectDeviceTreeList(ChargeGridTreeReqDTO req) {
        List<DeviceDTO> deivceList = deviceInfoMapper.selectDeviceList(req);
        return buildDeviceTree(deivceList, 0);
    }

    @Override
    public DevicePortRealDataVO selectDevicePortRealData(Long deviceId) {
        // 查询充电枪状态
        DevicePortStatus portStatus = devicePortStatusDbService.selectPortStatus(deviceId);
        DevicePortRealDataVO realData = DeviceConvert.INSTANCE.convertPortRealData(portStatus);
        if (PortStatusEnum.CHARGE.getType().equals(portStatus.getPortStatus())) {
            // 充电中查询实时订单数据
            RealDataQueryReq queryReq = RealDataQueryReq.builder()
                    .deviceId(portStatus.getPortId())
                    .orderSn(portStatus.getOrderSn())
                    .build();
            R<DevicePortData> portDataR = remoteDeviceDataService.selectRealDataByOrderNo(queryReq, SecurityConstants.INNER);
            if (R.isSuccess(portDataR) && portDataR.getData() != null) {
                // 填充实时数据
                DeviceConvert.INSTANCE.fillPortRealData(realData, portDataR.getData());
            }
            R<OrderInfo> orderInfoR = remoteOrderService.getChargeOrderByTradeNo(portStatus.getOrderSn(), SecurityConstants.INNER);
            if (R.isSuccess(orderInfoR) && orderInfoR.getData() != null) {
                // 设置订单车牌号
                OrderInfo order = orderInfoR.getData();
                realData.setPlatNumber(order.getLicensePlateNumber());
            }
        }
        return realData;
    }

    @Override
    public DevicePortEnergyDataVO selectDevicePortEnergyData(Long deviceId) {
        DevicePortEnergyDataVO energyData = DevicePortEnergyDataVO.builder().deviceId(deviceId).build();
        // 查询充电枪状态
        DevicePortStatus portStatus = devicePortStatusDbService.selectPortStatus(deviceId);
        if (PortStatusEnum.CHARGE.getType().equals(portStatus.getPortStatus())) {
            // 充电中查询电能数据
            RealDataQueryReq queryReq = RealDataQueryReq.builder()
                    .deviceId(portStatus.getPortId())
                    .orderSn(portStatus.getOrderSn())
                    .build();
            R<DevicePortAllRealData> allDataR = remoteDeviceDataService.selectAllRealDataByOrderNo(queryReq, SecurityConstants.INNER);
            if (R.isSuccess(allDataR) && allDataR.getData() != null) {
                DevicePortAllRealData allData = allDataR.getData();
                // 填充电能数据
                DeviceConvert.INSTANCE.fillPortEnergyData(energyData, allData);
            }
        }
        return energyData;
    }

    @Override
    public DevicePortTrendDataVO selectDevicePortTrendData(Long deviceId) {
        DevicePortTrendDataVO trendData = new DevicePortTrendDataVO(deviceId);
        // 查询充电枪状态
        DevicePortStatus portStatus = devicePortStatusDbService.selectPortStatus(deviceId);
        if (PortStatusEnum.CHARGE.getType().equals(portStatus.getPortStatus())) {
            // 充电中查询历史数据
            R<DevicePortAllHistoryData> allHistoryDataR = remoteDeviceDataService.selectAllHistoryByTradeSn(portStatus.getPortId(), portStatus.getOrderSn(), SecurityConstants.INNER);
            if (R.isSuccess(allHistoryDataR) && allHistoryDataR.getData() != null) {
                // 填充历史数据
                DevicePortAllHistoryData historyData = allHistoryDataR.getData();
                fillHistoryData(trendData, historyData);
            }
        }
        return trendData;
    }

    private void fillHistoryData(DevicePortTrendDataVO trend, DevicePortAllHistoryData historyData){
        List<DevicePortData> monitorData = historyData.getMonitorData();
        if (!CollectionUtils.isEmpty(monitorData)) {
            // 填充监测数据
            monitorData.forEach(data -> fillMonitorData(trend, data));
        }
        List<DeviceBmsDemandData> bmsDemandData = historyData.getBmsDemandData();
        if (!CollectionUtils.isEmpty(bmsDemandData)) {
            // 填充BMS电能数据
            bmsDemandData.forEach(data-> fillBmsData(trend, data));
        }
    }

    private void fillMonitorData(DevicePortTrendDataVO trend, DevicePortData data) {
        String key = DateUtil.formatDateTime(data.getEventTime());
        // 输出电压
        addTrendValue(trend.getVoltageTrend().getOutput(), key, data.getVoltage());
        // 输出电流
        addTrendValue(trend.getCurrentTrend().getOutput(), key, data.getCurrent());
        // 电池组最高温度
        addTrendValue(trend.getTemperatureTrend().getBatteryMax(), key, data.getTemperature());
        // 枪线温度
        addTrendValue(trend.getTemperatureTrend().getGunLine(), key, data.getGunLineTemperature());
        // 桩体温度
        addTrendValue(trend.getTemperatureTrend().getPileBody(), key, data.getPileBodyTemperature());
        // 充电量
        addTrendValue(trend.getElectricityTrend().getElectricity(), key, data.getChargeEnergy());
    }

    private void fillBmsData(DevicePortTrendDataVO trend, DeviceBmsDemandData data){
        String key = DateUtil.formatDateTime(data.getEventTime());
        // 需求电压
        addTrendValue(trend.getVoltageTrend().getDemand(), key, data.getBmsDemandVoltage());
        // 需求电流
        addTrendValue(trend.getCurrentTrend().getDemand(), key, data.getBmsDemandCurrent());
        // 检测电压
        addTrendValue(trend.getVoltageTrend().getCheck(), key, data.getBmsCheckVoltage());
        // 检测电流
        addTrendValue(trend.getCurrentTrend().getCheck(), key, data.getBmsCheckCurrent());
    }

    private void addTrendValue(List<TrendData<Double>> trends, String key, Double value){
        if (trends == null || value == null) {
            return;
        }
        trends.add(new TrendData<>(key, value));
    }

    public List<DeviceSimpleTreeVO> buildDeviceTree(List<DeviceDTO> list, int parentId) {
        List<DeviceDTO> returnList = new ArrayList<>();
        for (DeviceDTO t : list) {
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return DeviceConvert.INSTANCE.convertSimpleTreeList(returnList);
    }

    private void recursionFn(List<DeviceDTO> list, DeviceDTO t) {
        // 得到子节点列表
        List<DeviceDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (DeviceDTO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            } else {
                tChild.setChildren(new ArrayList<>());
            }
        }
    }

    private boolean hasChild(List<DeviceDTO> list, DeviceDTO t) {
        return !getChildList(list, t).isEmpty();
    }

    private List<DeviceDTO> getChildList(List<DeviceDTO> list, DeviceDTO t) {
        List<DeviceDTO> tlist = new ArrayList<>();
        for (DeviceDTO n : list) {
            if (n.getParentId().longValue() == t.getDeviceId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    @NotNull
    private ChargingAppPortDetailVO getChargingAppPortDetailVO(DeviceInfo deviceInfo,Boolean isPort) {
        DeviceInfo pileDeviceInfo;
        if (isPort){
            //查找父级信息
            pileDeviceInfo= deviceInfoMapper.selectById(deviceInfo.getParentId());
        }else {
            pileDeviceInfo = deviceInfo;
        }
        //根据父级ID找到所有枪口
        List<DeviceInfo> portInfoList = deviceInfoMapper.selectDevicesByParentId(pileDeviceInfo.getDeviceId());
        //封装返回体
        ChargingAppPortDetailVO portDetailDTO = new ChargingAppPortDetailVO();
        portDetailDTO.setSiteId(deviceInfo.getSiteId());
        portDetailDTO.setPileId(pileDeviceInfo.getDeviceId());
        portDetailDTO.setPileSN(pileDeviceInfo.getSn());
        portDetailDTO.setPileType(pileDeviceInfo.getChargeType());
        portDetailDTO .setRuleId(pileDeviceInfo.getPayRuleId());
        //填充子级设备
        List<PortInfo> portInfos = new ArrayList<>();
        for (DeviceInfo d:portInfoList) {
            PortInfo chargePortDTO = new PortInfo();
            chargePortDTO.setPortId(d.getDeviceId());
            chargePortDTO.setPortName(d.getName());
            chargePortDTO.setPortSN(d.getSn());
            chargePortDTO.setPortStatus(d.getPortStatus());
            portInfos.add(chargePortDTO);
        }
        portDetailDTO.setPortInfoList(portInfos);
        return portDetailDTO;
    }

    @Override
    public DeviceConnectDO selectDeviceConnnectInfo(Long deviceId) {
        return deviceInfoMapper.selectDeviceConnnectInfo(deviceId);
    }

    @Override
    public DeviceInfo selectPortDevice(Long deviceId) {
        return deviceInfoMapper.selectPortDevice(deviceId);
    }

    @Override
    public DeviceInfo findDeviceInfo(DeviceInfo deviceInfoDTO) {
        return deviceInfoMapper.findDeviceInfo(deviceInfoDTO);
    }

    @Override
    public Map<Long, List<DeviceInfo>> getDeviceInfoMap(List<Long> siteIds) {
        if(CollectionUtils.isEmpty(siteIds)){
            return new HashMap<>();
        }

        List<DeviceInfo> deviceInfos = deviceInfoMapper.selectDevices(siteIds);

        Map<Long,List<DeviceInfo>> deviceInfoMap = new HashMap<>();
        for (DeviceInfo deviceInfo : deviceInfos) {
            List<DeviceInfo> subDeviceInfos = deviceInfoMap.computeIfAbsent(
                    deviceInfo.getSiteId(),
                    k -> new ArrayList<>()
            );
            subDeviceInfos.add(deviceInfo);
        }
        return deviceInfoMap;
    }
}
