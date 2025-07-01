package com.things.cgomp.app.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.app.api.domain.AppUserCar;
import com.things.cgomp.app.config.ChargingMessageConfig;
import com.things.cgomp.app.domain.AppPaymentType;
import com.things.cgomp.app.domain.dto.ChargingRealDataDTO;
import com.things.cgomp.app.domain.dto.PlotInfoReq;
import com.things.cgomp.app.domain.vo.ChargingRealDataVO;
import com.things.cgomp.app.domain.vo.ChargingRuleVO;
import com.things.cgomp.app.domain.vo.PortAppDetailVO;
import com.things.cgomp.app.enums.ErrorCodeConstants;
import com.things.cgomp.app.enums.SortTypeEnum;
import com.things.cgomp.app.mapper.AppPaymentTypeMapper;
import com.things.cgomp.app.mapper.AppUserCarMapper;
import com.things.cgomp.app.mapper.AppUserMapper;
import com.things.cgomp.app.service.ChargingPileService;
import com.things.cgomp.app.service.OrderService;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.UserAccountType;
import com.things.cgomp.common.core.exception.enums.GlobalErrorCodeConstants;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.utils.bean.BeanUtils;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;
import com.things.cgomp.common.device.pojo.device.StartChargingConfigDTO;
import com.things.cgomp.common.device.pojo.device.StopChargingConfigDTO;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.DeviceChargeDataMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import com.things.cgomp.common.mq.producer.IProducer;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.device.api.RemoteDeviceCommandService;
import com.things.cgomp.device.api.RemoteDeviceService;
import com.things.cgomp.device.api.RemoteSiteService;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.device.api.dto.RuleFeeDTO;
import com.things.cgomp.device.api.dto.RuleTimeDTO;
import com.things.cgomp.device.api.model.vo.ChargingAppPortDetailVO;
import com.things.cgomp.device.api.model.vo.OrderInfoVO;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import com.things.cgomp.device.data.api.RemoteDeviceDataService;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.enums.OccupyTypeEnum;
import com.things.cgomp.order.api.enums.PayStatusEnum;
import com.things.cgomp.order.api.enums.PayTypeEnum;
import com.things.cgomp.order.api.vo.OrderAppDetailVO;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.system.api.RemoteUserAccountService;
import com.things.cgomp.system.api.domain.SysUserAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

@Slf4j
@Service
public class ChargingPileServiceImpl implements ChargingPileService {
    @Resource
    private RemoteSiteService remoteSiteService;
    @Resource
    private RemoteDeviceService remoteDeviceService;
    @Resource
    private RemoteRuleService remoteRuleService;
    @Resource
    private RemoteUserAccountService remoteUserAccountService;
    @Resource
    private RemoteDeviceCommandService remoteDeviceCommandService;
    @Resource
    private RemoteDeviceDataService remoteDeviceDataService;
    @Resource
    private AppUserCarMapper appUserCarMapper;
    @Resource
    private AppUserMapper appUserMapper;
    @Resource
    private IProducer producer;
    @Autowired
    private WxMaService wxMaService;
    @Resource
    private OrderService orderService;
    @Autowired(required = false)
    private ChargingMessageConfig chargingMessageConfig;
    @Resource
    private AppPaymentTypeMapper appPaymentTypeMapper;

    @Override
    public PageInfo<SiteAppVO> getPlotInfo(PlotInfoReq plotInfoReq) {
        R<List<SiteAppVO>> listR = remoteSiteService.selectSiteList(plotInfoReq.getDeviceType(), plotInfoReq.getCity(), plotInfoReq.getSiteName(),
                plotInfoReq.getAddress(), plotInfoReq.getRecommendSitesStatus(), plotInfoReq.getPileType(),
                plotInfoReq.getSupports(), plotInfoReq.getParkCarStatus(), plotInfoReq.getReceiptStatus(), plotInfoReq.getLat(), plotInfoReq.getLng(), SecurityConstants.INNER);
        if (Objects.isNull(listR) || 200 != listR.getCode()) {
            log.error("请求cg-device远程调用remoteSiteService服务，selectSiteList接口出错：{}", listR.getMsg());
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
        List<SiteAppVO> siteAppList = listR.getData();
        //按照要求排序(排序规则 1 距离 2 价格 3 智能排序(先距离,后价格))
        if (CollUtil.isNotEmpty(siteAppList)) {
            if (SortTypeEnum.ONE.getCode().equals(plotInfoReq.getSortType())) {
                siteAppList.sort(Comparator.comparing(SiteAppVO::getDistance));
            } else if (SortTypeEnum.TWO.getCode().equals(plotInfoReq.getSortType())) {
                siteAppList.sort(Comparator.comparing(SiteAppVO::getPrice));
            } else {
                siteAppList.sort(Comparator.comparing(SiteAppVO::getDistance));
                siteAppList.sort(Comparator.comparing(SiteAppVO::getPrice));
            }
        }
        //处理分页
        int currentPage = Math.max(plotInfoReq.getCurrent(), 1);  // 当前页至少为1
        int pageSize = Math.max(plotInfoReq.getPageSize(), 1);  // 每页大小至少为1
        List<SiteAppVO> siteAppListByPage = getSiteAppListByPage(siteAppList, currentPage, pageSize);
        PageInfo<SiteAppVO> pageInfo = new PageInfo<>(siteAppListByPage);
        pageInfo.setTotal(siteAppList.size());
        pageInfo.setPageSize(plotInfoReq.getPageSize());
        pageInfo.setPages((int) Math.ceil((double) pageInfo.getTotal() / plotInfoReq.getPageSize()));
        return pageInfo;
    }

    @Override
    public SiteAppInfoVO getSiteInfoVO(Long siteId, Float lng, Float lat) {
        //远程调用cg-device服务，获取站点详情
        R<SiteAppInfoVO> siteAppInfo = remoteSiteService.selectSiteAppInfo(siteId, lng, lat, SecurityConstants.INNER);
        if (Objects.isNull(siteAppInfo) || 200 != siteAppInfo.getCode()) {
            log.error("请求cg-device远程调用remoteSiteService服务，selectSite接口出错：{}", siteAppInfo.getMsg());
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR, "远程服务调用失败：" + siteAppInfo.getMsg());
        }
        SiteAppInfoVO siteAppInfoVO = siteAppInfo.getData();
        if (Objects.nonNull(siteAppInfoVO)) {
            // 查询用户的车辆列表
            Long userId = SecurityUtils.getUserId();
            if (userId == 0) {
                throw exception(ErrorCodeConstants.USER_NOT_EXIST);
            }
            List<AppUserCar> appUserCarList = appUserCarMapper.findCarListByUserId(userId);
            //判空寻找默认车辆
            if (CollUtil.isNotEmpty(appUserCarList)) {
                for (AppUserCar appUserCar : appUserCarList) {
                    if (appUserCar.getIsDefault().equals(1)) {
                        siteAppInfoVO.setDefaultCar(appUserCar.getLicensePlateNumber());
                        break;
                    }
                }

            }

        }
        return siteAppInfoVO;
    }

    @Override
    public List<ChargingRuleVO> getChargingPileRuleDetail(Long ruleId) {
        List<ChargingRuleVO> chargingRuleVOList = new ArrayList<>();
        //远程调用cg-pay服务，获取规则详情
        R<RuleDTO> ruleDTO = remoteRuleService.selectRule(ruleId);
        if (Objects.isNull(ruleDTO) || 200 != ruleDTO.getCode()) {
            log.error("请求cg-pay远程调用remoteRuleService服务，selectRule接口出错：{}", ruleDTO.getMsg());
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR, "远程服务调用失败：" + ruleDTO.getMsg());
        }
        RuleDTO ruleDTOData = ruleDTO.getData();
        if (Objects.nonNull(ruleDTOData)) {
            List<RuleTimeDTO> times = ruleDTOData.getTimes();
            List<RuleFeeDTO> fees = ruleDTOData.getFees();
            //将价格fees集合转换成map以type为key
            Map<Integer, RuleFeeDTO> feeMap = fees.stream().collect(Collectors.toMap(RuleFeeDTO::getType, Function.identity()));
            if (CollUtil.isNotEmpty(times)) {
                for (RuleTimeDTO ruleTime : times) {
                    ChargingRuleVO chargingRuleVO = new ChargingRuleVO();
                    chargingRuleVO.setStartTime(ruleTime.getStartTime());
                    chargingRuleVO.setEndTime(ruleTime.getEndTime());
                    chargingRuleVO.setType(ruleTime.getType());
                    if (feeMap.containsKey(ruleTime.getType())) {
                        BigDecimal electrovalence = feeMap.get(ruleTime.getType()).getElectrovalence();
                        BigDecimal serviceCharge = feeMap.get(ruleTime.getType()).getServiceCharge();
                        chargingRuleVO.setElectrovalence(electrovalence.setScale(2, RoundingMode.HALF_UP));
                        chargingRuleVO.setServiceCharge(serviceCharge.setScale(2, RoundingMode.HALF_UP));
                        //总价等于服务费加电费
                        chargingRuleVO.setPrice(electrovalence.add(serviceCharge).setScale(2, RoundingMode.HALF_UP));
                    }
                    chargingRuleVOList.add(chargingRuleVO);
                }
            }
        }
        return chargingRuleVOList;
    }

    @Override
    public PortAppDetailVO getChargingPileGunDetail(Long portId) {
        //远程调用cg-device服务，获取充电枪详情
        R<ChargingAppPortDetailVO> appChargePort = remoteDeviceService.getAppChargePort(portId, SecurityConstants.INNER);
        if (Objects.isNull(appChargePort) || 200 != appChargePort.getCode()) {
            log.error("请求cg-device远程调用remoteDeviceService服务，getAppChargePort接口出错：{}", appChargePort.getMsg());
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR, "远程服务调用失败：" + appChargePort.getMsg());
        }
        return getPortAppDetailVO(appChargePort);
    }

    @Override
    public PortAppDetailVO getChargingPileGunDetailBySn(String portSn) {
        //远程调用cg-device服务，获取充电枪详情
        R<ChargingAppPortDetailVO> appChargePort = remoteDeviceService.getAppChargePortBySn(portSn, SecurityConstants.INNER);
        if (Objects.isNull(appChargePort) || 200 != appChargePort.getCode()) {
            log.error("请求cg-device远程调用remoteDeviceService服务，getAppChargePortBySn接口出错：{}", appChargePort.getMsg());
            throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR, "远程服务调用失败：" + appChargePort.getMsg());
        }
        return getPortAppDetailVO(appChargePort);
    }

    private PortAppDetailVO getPortAppDetailVO(R<ChargingAppPortDetailVO> appChargePort) {
        PortAppDetailVO portAppDetailVO = new PortAppDetailVO();
        ChargingAppPortDetailVO data = appChargePort.getData();
        if (Objects.nonNull(data)) {
            BeanUtils.copyBeanProp(portAppDetailVO, data);
            //获取站点信息
            R<Site> siteR = remoteSiteService.selectSite(data.getSiteId());
            if (Objects.isNull(siteR) || 200 != siteR.getCode()) {
                log.error("请求cg-device远程调用remoteSiteService服务，selectSite接口出错：{}", siteR.getMsg());
                throw exception(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR, "远程服务调用失败：" + siteR.getMsg());
            }
            Site site = siteR.getData();
            portAppDetailVO.setAddress(site.getAddress());
            portAppDetailVO.setParkingInfo(site.getParkingInfo());
            portAppDetailVO.setFeeType(site.getFeeType());
            //获取计费信息
            if (data.getRuleId() != null) {
                List<ChargingRuleVO> chargingPileRuleDetail = getChargingPileRuleDetail(data.getRuleId());
                portAppDetailVO.setChargingRule(chargingPileRuleDetail);
            }
        }
        return portAppDetailVO;
    }

    @Override
    public DeviceCommandVO startCharging(Long portId) {
        //获取当前用户Id
        Long userId = SecurityUtils.getUserId();
        if (userId == 0) {
            throw exception(ErrorCodeConstants.USER_NOT_EXIST);
        }
//        Long userId = 2L;
        StartChargingConfigDTO startChargingConfigDTO = new StartChargingConfigDTO();
        //远程获取当前用户余额
        R<SysUserAccount> userInfo = remoteUserAccountService.getUserInfo(userId, UserAccountType.APP.getCode(), SecurityConstants.INNER);
        if (Objects.isNull(userInfo) || 200 != userInfo.getCode()) {
            log.error("请求cg-api远程调用remoteUserAccountService服务，getUserInfo接口出错：{}", userInfo.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR, userInfo.getMsg());
        }
        if (Objects.isNull(userInfo.getData())) {
            //用户余额账户不存在
            throw exception(ErrorCodeConstants.USER_ACCOUNT_NOT_EXIST);
        }
        //获取用户余额保留两位小数
        BigDecimal bigDecimal = userInfo.getData().getBalance().setScale(2, RoundingMode.HALF_UP);
        startChargingConfigDTO.setAccountBalance(bigDecimal.doubleValue());
        //获取当前用户手机号
        AppUser appUser = appUserMapper.selectById(userId);
        startChargingConfigDTO.setPhone(appUser.getMobile());
        //获取当前用户支付方式
        startChargingConfigDTO.setPaymentType(PayTypeEnum.WALLET.getType());
        //判断是否修改默认支付方式
        Long paymentTypeId = appUser.getPaymentTypeId();
        if (Objects.nonNull(paymentTypeId)) {
            //获取用户支付方式(默认为钱包支付不传)
            AppPaymentType appPaymentType = appPaymentTypeMapper.selectById(paymentTypeId);
            if (Objects.nonNull(appPaymentType)) {
                String typeCode = appPaymentType.getTypeCode();
                if (!StringUtils.equals("wallet",typeCode)){
                    startChargingConfigDTO.setPaymentType(PayTypeEnum.WE_CHAT.getType());
                }
            }
        }
        //获取当前用户默认车牌号
        AppUserCar defaultCarByUserId = appUserCarMapper.findDefaultCarByUserId(userId);
        if (Objects.nonNull(defaultCarByUserId)) {
            startChargingConfigDTO.setLicensePlateNumber(defaultCarByUserId.getLicensePlateNumber());
        }
        startChargingConfigDTO.setUserId(userId);
        //开始充电
        startChargingConfigDTO.setPortId(portId);
        R<DeviceCommandVO> deviceCommandVOR = remoteDeviceCommandService.startCharging(startChargingConfigDTO);
        if (Objects.isNull(deviceCommandVOR) || 200 != deviceCommandVOR.getCode()) {
            log.error("请求cg-device远程调用remoteDeviceCommandService服务，startCharging接口出错：{}", deviceCommandVOR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR, deviceCommandVOR.getMsg());
        }
        //异步发送消息通知
        sendStartChargingMessage(userId, portId, deviceCommandVOR.getData());

        return deviceCommandVOR.getData();
    }

    /**
     * 开启充电启动消息通知
     *
     * @param portId
     * @return
     */
    @Async
    public void sendStartChargingMessage(Long userId, Long portId, DeviceCommandVO deviceCommandVO) {
        if (!deviceCommandVO.isSucceed()) {
            return;
        }
        //获取订单信息
        Object responseData = deviceCommandVO.getResponseData();
        if (Objects.isNull(responseData)) {
            log.error("根据端口ID开启充电发送小程序消息失败，根据端口ID查询订单信息为空，端口ID为：{}", portId);
            return;
        }
        if (responseData instanceof OrderInfoVO) {
            OrderInfoVO orderInfoVO = (OrderInfoVO) responseData;
            Long id = orderInfoVO.getId();
            OrderAppDetailVO orderDetail = orderService.selectOrderDetail(id);
            List<WxMaSubscribeMessage.MsgData> data = new ArrayList<>();
            LocalDateTime startTime = orderDetail.getStartTime();
            data.add(new WxMaSubscribeMessage.MsgData("time2", DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"))); // 开始时间
            data.add(new WxMaSubscribeMessage.MsgData("thing5", orderDetail.getSiteName())); // 充电站名称
            data.add(new WxMaSubscribeMessage.MsgData("character_string11", orderDetail.getSn())); // 订单编号
            data.add(new WxMaSubscribeMessage.MsgData("thing14", "充电已开始，如果有疑问，请联系我们")); // 温馨提示
            //获取openID
            AppUser appUser = appUserMapper.selectById(userId);
            if (Objects.isNull(appUser)) {
                log.error("根据用户ID查询用户信息为空，用户ID为：{}", userId);
                return;
            }
            String openId = appUser.getWxOpenId();
            sendWxMessage(data, openId, chargingMessageConfig.getStartChargingTemplateId(),
                    chargingMessageConfig.getStartChargingRedirectUrl());
        }

    }
   @Override
    @Async
    public void sendStopChargingMessage(Long id) {
        //获取订单信息

        OrderAppDetailVO orderDetail = orderService.selectOrderDetail(id);
        List<WxMaSubscribeMessage.MsgData> data = new ArrayList<>();
        LocalDateTime startTime = orderDetail.getEndTime();
        data.add(new WxMaSubscribeMessage.MsgData("time2", DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"))); // 结束时间
        data.add(new WxMaSubscribeMessage.MsgData("thing18", orderDetail.getSiteName())); // 充电站名称
        data.add(new WxMaSubscribeMessage.MsgData("character_string5", orderDetail.getSn())); // 订单编号
        BigDecimal payAmount = orderDetail.getPayAmount();
        data.add(new WxMaSubscribeMessage.MsgData("character_string5",payAmount.toPlainString())); // 订单金额
        data.add(new WxMaSubscribeMessage.MsgData("thing22", orderDetail.getRealChargingTime().toPlainString()+"小时")); // 充电时长
        //获取openID
        AppUser appUser = appUserMapper.selectById(orderDetail.getUserId());
        if (Objects.isNull(appUser)) {
            log.error("根据用户ID查询用户信息为空，用户ID为：{}", orderDetail);
            return;
        }
        String openId = appUser.getWxOpenId();
        sendWxMessage(data, openId, chargingMessageConfig.getEndChargingTemplateId(),
                chargingMessageConfig.getEndChargingRedirectUrl());
    }

    @Override
    public List<SiteAppVO> getSiteInfoList(PlotInfoReq plotInfoReq) {
        R<List<SiteAppVO>> listR = remoteSiteService.selectSiteList(plotInfoReq.getDeviceType(), plotInfoReq.getCity(), plotInfoReq.getSiteName(),
                plotInfoReq.getAddress(), plotInfoReq.getRecommendSitesStatus(), plotInfoReq.getPileType(),
                plotInfoReq.getSupports(), plotInfoReq.getParkCarStatus(), plotInfoReq.getReceiptStatus(), plotInfoReq.getLat(), plotInfoReq.getLng(), SecurityConstants.INNER);
        if (Objects.isNull(listR) || 200 != listR.getCode()) {
            log.error("请求cg-device远程调用remoteSiteService服务，selectSiteList接口出错：{}", listR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return listR.getData();
    }

    @Override
    public DeviceCommandVO stopCharging(Long portId) {
        //调用停止充电接口
        StopChargingConfigDTO stopChargingConfigDTO = new StopChargingConfigDTO();
        stopChargingConfigDTO.setPortId(portId);
        R<DeviceCommandVO> deviceCommandVOR = remoteDeviceCommandService.stopCharging(stopChargingConfigDTO);
        if (Objects.isNull(deviceCommandVOR) || 200 != deviceCommandVOR.getCode()) {
            log.error("请求cg-device远程调用remoteDeviceCommandService服务，stopCharging接口出错：{}", deviceCommandVOR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        return deviceCommandVOR.getData();
    }

    @Override
    public Boolean pushChargingInfo(ChargingRealDataDTO deviceChargeDataMsg) {
        //随机生成一个Metadata测试数据
        Metadata metadata = Metadata.builder()
                .txId(UUID.randomUUID().toString())
                .requestId(UUID.randomUUID().toString())
                .eventTime(System.currentTimeMillis())
                .deviceId(deviceChargeDataMsg.getPortId())
                .build();
        this.notifyApp(deviceChargeDataMsg, metadata);
        return true;
    }
    private void notifyApp(ChargingRealDataDTO reqMsg, Metadata metadata){
        try {
            DeviceChargeDataMsg deviceChargeDataMsg = DeviceChargeDataMsg.builder().build();
            BeanUtils.copyBeanProp(deviceChargeDataMsg, reqMsg);
            QueueMsg<AbstractBody> sendMsg = QueueMsg.builder()
                    .metadata(metadata)
                    .body(deviceChargeDataMsg)
                    .build();
            producer.asyncSend(MQTopics.APP, MQTopics.Tag.CHARGE_INFO, sendMsg, new SimpleServiceCallback() {
                @Override
                public void onSuccess(Object msg) {
                    log.info("notifyApp 消息发送成功, msg={}", msg);
                }

                @Override
                public void onError(Throwable e) {
                    log.info("notifyApp 消息发送失败, ", e);
                    // 发送失败暂时不重新发送
                }
            });
        }catch (Exception e){
            log.error("notifyApp error, reqMsg={}, metadata={}", reqMsg, metadata, e);
            // 处理失败暂时不抛出异常
        }
    }
    @Override
    public ChargingRealDataVO getChargingDetail(Long portId, String tradeSn) {
        ChargingRealDataVO chargingRealDataVO = new ChargingRealDataVO();
        //查找设备中的站点名称和桩编码
        R<ChargingAppPortDetailVO> appChargePort = remoteDeviceService.getAppChargePort(portId, SecurityConstants.INNER);
        if (Objects.isNull(appChargePort) || 200 != appChargePort.getCode()) {
            log.error("请求cg-device远程调用remoteDeviceService服务，getAppChargePort接口出错：{}", appChargePort.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        ChargingAppPortDetailVO deviceInfo = appChargePort.getData();
        String pileSN = deviceInfo.getPileSN();
        chargingRealDataVO.setPileSn(pileSN);
        Long siteId = deviceInfo.getSiteId();
        //查找站点名称
        R<Site> siteR = remoteSiteService.selectSite(siteId);
        if (Objects.isNull(siteR) || 200 != siteR.getCode()) {
            log.error("请求cg-device远程调用remoteSiteService服务，selectSite接口出错：{}", siteR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        Site site = siteR.getData();
        String siteName = site.getName();
        BigDecimal parkingFreeTime = site.getParkingFreeTime();
        chargingRealDataVO.setSiteName(siteName);
        chargingRealDataVO.setParkingFreeTime(parkingFreeTime);
        //查找订单ID
        OrderInfo order = orderService.selectOrderByOrderNo(tradeSn);
        if (Objects.isNull(order)){
            throw exception(ErrorCodeConstants.ORDER_NOT_EXIST);
        }
        chargingRealDataVO.setOrderId(order.getId());
        List<OrderInfo> orderInfos = orderService.selectSubOrders(order.getId());
        if (CollectionUtil.isNotEmpty(orderInfos)) {
            for (OrderInfo orderInfo : orderInfos) {
                if (Objects.equals(orderInfo.getPayStatus(), PayStatusEnum.NOT_PAID.getCode())) {
                    if (Objects.equals(orderInfo.getOccupyType(), OccupyTypeEnum.BEFORE_CHARGING.getType())) {
                        chargingRealDataVO.setOccupyFee(orderInfo.getPayAmount());
                    }
                }
            }
        }
        //查找实时数据
        RealDataQueryReq queryReq = RealDataQueryReq.builder().deviceId(portId).orderSn(tradeSn).build();
        R<DevicePortData> deviceDataR = remoteDeviceDataService.selectRealDataByOrderNo(queryReq, SecurityConstants.INNER);
        if (Objects.isNull(deviceDataR) || 200 != deviceDataR.getCode()) {
            log.error("请求cg-device远程调用remoteDeviceDataService服务，selectRealDataByOrderNo接口出错：{}", deviceDataR == null ? null : deviceDataR.getMsg());
            throw exception(GlobalErrorCodeConstants.NETWORK_ERROR);
        }
        DevicePortData data = deviceDataR.getData();
        if (Objects.nonNull(data)) {
            //转换数据
            chargingRealDataVO.setPortId(data.getDeviceId());
            chargingRealDataVO.setTradeSn(data.getOrderSn());
            chargingRealDataVO.setChargeEnergy(data.getChargeEnergy());
            chargingRealDataVO.setRealPower(data.getPower());
            chargingRealDataVO.setRealVoltage(data.getVoltage());
            chargingRealDataVO.setRealCurrent(data.getCurrent());
            chargingRealDataVO.setTotalCost(data.getAmount());
            chargingRealDataVO.setSoc(data.getSoc());
            chargingRealDataVO.setTimeCharge(data.getTimeCharge());
            chargingRealDataVO.setTimeLeft(data.getTimeLeft());
            chargingRealDataVO.setTemperature(data.getTemperature());
        }
        return chargingRealDataVO;
    }


    private List<SiteAppVO> getSiteAppListByPage(List<SiteAppVO> allSiteApps, int page, int pageSize) {

        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allSiteApps.size());

        if (start >= allSiteApps.size()) {
            return Collections.emptyList(); // 如果起始位置超出范围，返回空列表
        }

        return allSiteApps.subList(start, end);
    }

    /**
     * 发送小程序消息通知
     *
     * @param openId     用户openId
     * @param templateId 消息模板ID
     * @param page       点击消息跳转的小程序页面路径
     * @return 是否发送成功
     */
    public boolean sendWxMessage(List<WxMaSubscribeMessage.MsgData> data, String openId, String templateId, String page) {
        try {
            // 构建消息对象
            WxMaSubscribeMessage message = WxMaSubscribeMessage.builder()
                    .toUser(openId) // 接收者openId
                    .templateId(templateId) // 消息模板ID
                    .page(page) // 跳转页面
                    .data(data) // 消息内容
                    .build();

            // 发送消息
            wxMaService.getMsgService().sendSubscribeMsg(message);
            log.info("小程序消息通知发送成功，openId: {}, templateId: {}", openId, templateId);
            return true;
        } catch (Exception e) {
            log.error("小程序消息通知发送失败，openId: {}, templateId: {}", openId, templateId, e);
            return false;
        }
    }
}
