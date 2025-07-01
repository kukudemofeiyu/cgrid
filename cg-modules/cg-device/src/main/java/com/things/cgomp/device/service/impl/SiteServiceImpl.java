package com.things.cgomp.device.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.enums.DelFlagEnum;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.enums.ComponentEnum;
import com.things.cgomp.common.device.enums.PortStatusEnum;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.device.api.model.vo.ChargingPile;
import com.things.cgomp.device.api.model.vo.ChargingPort;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import com.things.cgomp.device.dto.site.SiteListDTO;
import com.things.cgomp.device.dto.site.SitePageDTO;
import com.things.cgomp.device.enums.ChargeTypeEnum;
import com.things.cgomp.device.mapper.SiteMapper;
import com.things.cgomp.device.service.IDeviceInfoService;
import com.things.cgomp.device.service.ISiteAreaCodeService;
import com.things.cgomp.device.service.ISiteService;
import com.things.cgomp.device.vo.SiteVo;
import com.things.cgomp.device.vo.site.SimpleSiteVo;
import com.things.cgomp.pay.api.RemoteRuleService;
import com.things.cgomp.device.api.dto.RuleDTO;
import com.things.cgomp.device.api.dto.RuleFeeDTO;
import com.things.cgomp.device.api.dto.RuleTimeDTO;
import com.things.cgomp.system.api.RemoteOperatorService;
import com.things.cgomp.system.api.RemoteSiteEfService;
import com.things.cgomp.system.api.domain.SysOperator;
import com.things.cgomp.system.api.domain.SysOrgSite;
import com.things.cgomp.system.api.domain.SysSiteEf;
import com.things.cgomp.system.api.domain.SysUserSite;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 站点表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Slf4j
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements ISiteService {

    @Resource
    private IDeviceInfoService deviceInfoService;
    @Resource
    private RemoteRuleService remoteRuleService;
    @Resource
    private ISiteAreaCodeService siteAreaCodeService;
    @Resource
    private RemoteOperatorService remoteOperatorService;
    @Resource
    private RemoteSiteEfService remoteSiteEfService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addSite(Site site) {
        if (site.getOperatorId() == null) {
            site.setOperatorId(SecurityUtils.getOperatorId());
        }
        site.setCreateBy(SecurityUtils.getUserId());
        // 创建站点
        baseMapper.insert(site);
        // 创建站点关联
        addSiteEf(site);
        return site.getId();
    }

    private void addSiteEf(Site addSite) {
        List<Long> orgEf = new ArrayList<>();
        Long operatorOrgId = getOperatorOrg(addSite.getOperatorId());
        if (operatorOrgId != null) {
            // 添加运营商组织关联
            orgEf.add(operatorOrgId);
        }
        SysSiteEf siteEf = new SysSiteEf();
        if (CollectionUtil.isNotEmpty(orgEf)) {
            siteEf.setOrgSites(orgEf.stream().map(o-> SysOrgSite.builder()
                    .orgId(o).siteId(addSite.getId()).build()).collect(Collectors.toList()));
        }
        if (SecurityUtils.isOperator()) {
            // 添加当前用户站点关联
            SysUserSite userSite = SysUserSite.builder().userId(SecurityUtils.getUserId()).siteId(addSite.getId()).build();
            siteEf.setUserSites(Collections.singletonList(userSite));
        }
        remoteSiteEfService.addSiteEf(siteEf, SecurityConstants.INNER);
    }

    @Override
    public void editSite(Site site) {
        site.setUpdateBy(SecurityUtils.getUserId())
                .setUpdateTime(LocalDateTime.now());
        // 修改站点关联
        updateSiteEf(site);
        // 修改站点
        baseMapper.updateById(site);
    }

    private void updateSiteEf(Site updateSite) {
        Site site = baseMapper.selectById(updateSite.getId());
        if (site == null || site.getOperatorId() == null) {
            return;
        }
        SysSiteEf siteEf = new SysSiteEf();
        if (!site.getOperatorId().equals(updateSite.getOperatorId())) {
            // 修改了运营商，需要修改组织关联关系
            Long oldOperatorOrgId = getOperatorOrg(site.getOperatorId());
            Long operatorOrgId = getOperatorOrg(updateSite.getOperatorId());
            if (operatorOrgId != null) {
                // 添加运营商组织关联
                SysOrgSite orgSite = SysOrgSite.builder().siteId(updateSite.getId()).orgId(operatorOrgId).build();
                siteEf.setOrgSites(Collections.singletonList(orgSite));
            }
            // 添加删除标识
            siteEf.setRemoveOrgSite(SysOrgSite.builder().siteId(updateSite.getId()).orgId(oldOperatorOrgId).build());
        }
        // 用户站点关联无需修改
        remoteSiteEfService.updateSiteEf(siteEf, SecurityConstants.INNER);
    }

    private Long getOperatorOrg(Long operatorId){
        R<SysOperator> operatorR = remoteOperatorService.getOperatorInfoById(operatorId, SecurityConstants.INNER);
        if (operatorR != null && operatorR.getData() != null) {
            return operatorR.getData().getOrgId();
        }
        return null;
    }

    @Override
    public void deleteSite(Long id) {
        Site site = new Site()
                .setId(id)
                .setDelFlag(DelFlagEnum.DELETE.getType());
        // 删除站点关联
        deleteSiteEf(id);
        // 修改站点为删除状态
        baseMapper.updateById(site);
    }

    private void deleteSiteEf(Long siteId) {
        remoteSiteEfService.deleteSiteEf(siteId, SecurityConstants.INNER);
    }

    @Override
    public Site selectSite(Long id) {
        Site site = baseMapper.selectById(id);
        if(site == null){
            return null;
        }

        Map<Long, String> areaCodeNameMap = siteAreaCodeService.getNameMap(
                site.buildAreaCodes()
        );

        String countryName = areaCodeNameMap.get(site.getCountryCode());
        String provinceName = areaCodeNameMap.get(site.getProvinceCode());
        String cityName = areaCodeNameMap.get(site.getCityCode());
        String districtName = areaCodeNameMap.get(site.getDistrictCode());
        String operatorName = getOperatorName(site);
        site.setCountryName(countryName)
                .setProvinceName(provinceName)
                .setCityName(cityName)
                .setDistrictName(districtName)
                .setOperatorName(operatorName);

        return site;
    }

    private String getOperatorName(Site site) {
        R<SysOperator> operatorR = remoteOperatorService.getOperatorInfoById(site.getOperatorId(), SecurityConstants.INNER);
        if (R.SUCCESS != operatorR.getCode() || operatorR.getData() == null) {
            return null;
        }
        return operatorR.getData().getName();
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public PageInfo<SiteVo> selectPage(SitePageDTO pageDTO) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<SiteVo> sites = baseMapper.selectSites(pageDTO);

            fillData(sites);

            return new PageInfo<>(sites);
        }
    }

    @Override
    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    public List<SimpleSiteVo> selectSimpleSites(SiteListDTO siteDTO) {
        return baseMapper.selectSimpleSites(siteDTO);
    }

    @Override
    public Map<Long, Site> selectSiteMap(List<Long> ids) {
        List<Site> sites = baseMapper.selectBatchIds(ids);

        return sites.stream()
                .collect(Collectors.toMap(
                        Site::getId,
                        a -> a,
                        (a, b) -> a
                ));
    }

    @Override
    public Long getOperatorId(Long id) {
        return baseMapper.getOperatorId(id);
    }

    @Override
    public List<SiteAppVO> selectSiteAppList(SiteListDTO siteListDTO) {
        //查询站点
        List<SiteAppVO> siteAppList = baseMapper.selectSiteAppList(siteListDTO);
        if (CollUtil.isEmpty(siteAppList)) {
            log.info("查询无充电桩数据!");
            return Collections.EMPTY_LIST;
        }
        //获取设备相关信息
        List<Long> siteIds = siteAppList.stream()
                .map(SiteAppVO::getId)
                .collect(Collectors.toList());
        Map<Long, List<DeviceInfo>> deviceInfoMap = deviceInfoService.getDeviceInfoMap(siteIds);
        //填充设备相关数据
        siteAppList.forEach(site -> fillAppData(
                deviceInfoMap,
                site,
                siteListDTO.getLat(),
                siteListDTO.getLng()
        ));
        return siteAppList;
    }

    @Override
    public SiteAppInfoVO selectSiteAppInfo(Long siteId, Float lat, Float lng) {
        SiteAppInfoVO siteInfoVO = baseMapper.selectSiteAppInfo(siteId);
        if (siteInfoVO == null){
            log.info("该站点不存在站点ID：{}",siteId);
            return null;
        }
        //计算距离
        siteInfoVO.setDistance(calculateDistance(
                siteInfoVO.getLat(),
                siteInfoVO.getLng(),
                lat,
                lng
        ));
        //获取设备信息
        List<DeviceInfo> deviceInfos = deviceInfoService.selectDevices(Collections.singletonList(siteId));
        if (CollectionUtil.isEmpty(deviceInfos)){
            log.info("该站点下无设备,站点ID：{}",siteId);
            return siteInfoVO;
        }
        List<DeviceInfo> pileDevices = getPILEDevices(deviceInfos);
        if (CollectionUtil.isEmpty(pileDevices)){
            log.info("该站点下无桩设备,站点ID：{}",siteId);
            return siteInfoVO;
        }
        //填充价格信息
        getPriceInfo(pileDevices, siteInfoVO);
        //填充设备信息
        List<DeviceInfo> portDevices = getPortDevices(deviceInfos);
        if (CollectionUtil.isEmpty(portDevices)){
            log.info("该站点下无枪设备,站点ID：{}",siteId);
            return siteInfoVO;
        }
        //填充枪总数
        fillDeviceNum(siteInfoVO, portDevices);
        //将枪设备按照prentId分组
        Map<Long, List<DeviceInfo>> portDevicesMap = portDevices.stream()
                .collect(Collectors.groupingBy(DeviceInfo::getParentId));


        List<ChargingPile> chargingPiles = new ArrayList<>();
        for (DeviceInfo deviceInfo : pileDevices) {
            ChargingPile chargingPile = new ChargingPile();
            List<ChargingPort> chargingPortList = new ArrayList<>();
            chargingPile.setName(deviceInfo.getName());
            chargingPile.setRuleId(deviceInfo.getPayRuleId());
            chargingPile.setPileType(deviceInfo.getChargeType());
            List<DeviceInfo> subDeviceInfos = portDevicesMap.get(deviceInfo.getDeviceId());
            if (CollectionUtil.isEmpty(subDeviceInfos)){
                log.info("该桩下无枪设备,桩ID：{}",deviceInfo.getDeviceId());
                continue;
            }
            for (DeviceInfo subDeviceInfo : subDeviceInfos) {
                ChargingPort chargingPort = new ChargingPort();
                chargingPort.setName(subDeviceInfo.getName());
                chargingPort.setPortStatus(subDeviceInfo.getPortStatus());
                chargingPort.setMaxPower(deviceInfo.getMaxPower());
                chargingPortList.add(chargingPort);
            }
            chargingPile.setPorts(chargingPortList);
            chargingPiles.add(chargingPile);
        }
        siteInfoVO.setList(chargingPiles);
        return siteInfoVO;
    }

    @Override
    public Long getSiteOperatorId(Long siteId) {
        Site site = baseMapper.selectById(siteId);
        if (site == null) {
            return null;
        }
        return site.getOperatorId();
    }

    private void fillAppData(Map<Long, List<DeviceInfo>> deviceInfoMap, SiteAppVO site, Float lat, Float lng) {
        //计算距离
        site.setDistance(calculateDistance(
                site.getLat(),
                site.getLng(),
                lat,
                lng
        ));

        List<DeviceInfo> deviceInfos = deviceInfoMap.get(site.getId());
        if (CollectionUtil.isEmpty(deviceInfos)){
            log.info("该站点下无设备站点ID：{}",site.getId());
            return;
        }
        List<DeviceInfo> pileDevices = getPILEDevices(deviceInfos);
        //填充价格
        getPriceInfo(pileDevices,site);
        List<DeviceInfo> portDevices = getPortDevices(deviceInfos);
        fillDeviceNum(site, portDevices);
    }

    private  void fillDeviceNum(SiteAppVO site, List<DeviceInfo> portDevices) {
        //填充枪总数和快充慢充总数
        site.setTotalNum(portDevices.size());

        // 计算慢充和快充总数及闲时数
        int totalSlowNum = 0;
        int totalFastNum = 0;
        int idleSlowNum = 0;
        int idleFastNum = 0;

        for (DeviceInfo deviceInfo : portDevices) {
            if (PortStatusEnum.FREE.getType().equals(deviceInfo.getPortStatus())) {
                if (ChargeTypeEnum.SLOW.getType().equals(deviceInfo.getChargeType())) {
                    idleSlowNum++;
                } else if (ChargeTypeEnum.FAST.getType().equals(deviceInfo.getChargeType())) {
                    idleFastNum++;
                }
            }

            if (ChargeTypeEnum.SLOW.getType().equals(deviceInfo.getChargeType())) {
                totalSlowNum++;
            } else if (ChargeTypeEnum.FAST.getType().equals(deviceInfo.getChargeType())) {
                totalFastNum++;
            }
        }

        site.setTotalSlowNum(totalSlowNum);
        site.setTotalFastNum(totalFastNum);
        site.setIdleSlowNum(idleSlowNum);
        site.setIdleFastNum(idleFastNum);
    }

    private void getPriceInfo(List<DeviceInfo> pileDevices, SiteAppVO site) {
        if (CollectionUtil.isEmpty(pileDevices)) {
            return;
        }
        //从设备中获取到所有规则ID
        List<Long> ruleIds = pileDevices.stream()
                .map(DeviceInfo::getPayRuleId)
                .distinct()
                .collect(Collectors.toList());
        //远程获取到规则表
        R<List<RuleDTO>> listR = remoteRuleService.selectRules(ruleIds);
        if (listR == null || !R.isSuccess(listR)) {
            log.info("远程获取规则失败");
            return;
        }
        List<RuleDTO> ruleDTOS = listR.getData();
        //获取当前时间
        Date date = new Date();
        String currentTime = DateUtil.format(date, "HH:mm");
        log.info("当前时间为：{}",currentTime);
        if (currentTime == null) {
            log.info("当前时间格式化失败");
            return;
        }
        //循环规则 匹配当前时间的时间段 再从价格表中寻找出最低价进行比价
        for (RuleDTO ruleDTO : ruleDTOS) {
            //找到当前时间段
            List<RuleTimeDTO> times = ruleDTO.getTimes();
            if (times == null) {
                continue;
            }
            for (RuleTimeDTO ruleTimeDTO : times) {
                //找到当前时间段的价格
                if (ruleTimeDTO.getStartTime().compareTo(currentTime) <= 0 &&
                        ruleTimeDTO.getEndTime().compareTo(currentTime) >= 0) {
                    Integer type = ruleTimeDTO.getType();
                    List<RuleFeeDTO> fees = ruleDTO.getFees();
                    if (fees == null) {
                        continue;
                    }
                    for (RuleFeeDTO ruleFeeDTO : fees) {
                        //根据类型获取到价格
                        if (type.equals(ruleFeeDTO.getType())) {
                            BigDecimal electrovalence = ruleFeeDTO.getElectrovalence();
                            electrovalence = electrovalence.setScale(2, RoundingMode.HALF_UP);
                            BigDecimal serviceCharge = ruleFeeDTO.getServiceCharge();
                            serviceCharge = serviceCharge.setScale(2, RoundingMode.HALF_UP);

                            BigDecimal sitePrice = BigDecimal.valueOf(site.getPrice());
                            if (sitePrice.compareTo(BigDecimal.ZERO) == 0) {
                                site.setChargeTime(ruleTimeDTO.getStartTime()+"-"+ruleTimeDTO.getEndTime());
                                site.setPrice(electrovalence.floatValue());
                                site.setServicePrice(serviceCharge.floatValue());
                                break;
                            }
                            if (electrovalence != null) {
                                if (electrovalence.compareTo(sitePrice) < 0) {
                                    site.setChargeTime(ruleTimeDTO.getStartTime()+"-"+ruleTimeDTO.getEndTime());
                                    site.setPrice(electrovalence.floatValue());
                                    site.setServicePrice(serviceCharge.floatValue());
                                }
                            }
                            break; // 找到匹配的 type 后直接跳出内层循环
                        }
                    }
                }
            }
        }
        // 使用 BigDecimal 处理浮点数运算并保留两位小数
        BigDecimal chargeCost = new BigDecimal(Float.toString(site.getPrice()))
                .add(new BigDecimal(Float.toString(site.getServicePrice())))
                .setScale(2, RoundingMode.HALF_UP); // 保留两位小数，四舍五入

// 设置到 site 对象
        site.setChargeCost(chargeCost.floatValue());
    }

    /**
         * 计算两个经纬度点之间的距离（单位：千米）
         *
         * @param lat1 点1的纬度
         * @param lon1 点1的经度
         * @param lat2 点2的纬度
         * @param lon2 点2的经度
         * @return 两点之间的距离，单位千米
         */
        public double calculateDistance(Float lat1, Float lon1, Float lat2, Float lon2) {
            if (lat1 == null || lon1 == null || lat2 == null || lon2 == null) {
                return -1.00;
            }
            // 地球半径，单位：千米
            final double EARTH_RADIUS = 6371;

            // 将经纬度转换为弧度
            double lat1Rad = Math.toRadians(lat1);
            double lon1Rad = Math.toRadians(lon1);
            double lat2Rad = Math.toRadians(lat2);
            double lon2Rad = Math.toRadians(lon2);

            // 经纬度差值
            double latDiff = lat2Rad - lat1Rad;
            double lonDiff = lon2Rad - lon1Rad;

            // Haversine 公式
            double a = Math.pow(Math.sin(latDiff / 2), 2) +
                    Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                            Math.pow(Math.sin(lonDiff / 2), 2);

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

            // 计算距离
            double distance = EARTH_RADIUS * c/1000;
            // 保留两位小数
            BigDecimal bd = new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }



    private void fillData(List<SiteVo> sites) {
        Map<Long, List<DeviceInfo>> deviceInfoMap = getDeviceInfoMap(sites);

        sites.forEach(site -> fillData(
                deviceInfoMap,
                site
        ));
    }

    private void fillData(
            Map<Long, List<DeviceInfo>> deviceInfoMap,
            SiteVo site
    ) {
        List<DeviceInfo> deviceInfos = deviceInfoMap.getOrDefault(
                site.getId(),
                new ArrayList<>()
        );

        Integer pileNum = getPileNum(deviceInfos);
        List<DeviceInfo> portDevices = getPortDevices(deviceInfos);

        site.setPileNum(pileNum)
                .setPortTotalNum(portDevices.size())
                .setPortUsingNum(getPortNum(portDevices, PortStatusEnum.CHARGE))
                .setPortFreeNum(getPortNum(portDevices, PortStatusEnum.FREE))
                .setPortFaultNum(getPortNum(portDevices, PortStatusEnum.FAULT));
    }

    @NotNull
    private List<DeviceInfo> getPortDevices(List<DeviceInfo> deviceInfos) {
        return deviceInfos.stream()
                .filter(deviceInfo -> ComponentEnum.PORT.getType().equals(deviceInfo.getComponent()))
                .collect(Collectors.toList());
    }
    @NotNull
    private List<DeviceInfo> getPILEDevices(List<DeviceInfo> deviceInfos) {
        return deviceInfos.stream()
                .filter(deviceInfo -> ComponentEnum.PILE.getType().equals(deviceInfo.getComponent()))
                .collect(Collectors.toList());
    }

    private int getPortNum(
            List<DeviceInfo> portDevices,
            PortStatusEnum portStatusEnum
    ) {
        List<DeviceInfo> subDeviceInfos = portDevices.stream()
                .filter(deviceInfo -> portStatusEnum.getType().equals(deviceInfo.getPortStatus()))
                .collect(Collectors.toList());
        return subDeviceInfos.size();
    }

    @NotNull
    private Integer getPileNum(List<DeviceInfo> deviceInfos) {
        List<DeviceInfo> pileDevices = deviceInfos.stream()
                .filter(deviceInfo -> ComponentEnum.PILE.getType().equals(deviceInfo.getComponent()))
                .collect(Collectors.toList());

        return pileDevices.size();
    }

    private Map<Long, List<DeviceInfo>> getDeviceInfoMap(List<SiteVo> sites) {
        List<Long> siteIds = getSiteIds(sites);
        return deviceInfoService.getDeviceInfoMap(siteIds);
    }

    @NotNull
    private List<Long> getSiteIds(List<SiteVo> sites) {
        return sites.stream()
                .map(SiteVo::getId)
                .collect(Collectors.toList());
    }
}
