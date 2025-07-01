package com.things.cgomp.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.pojo.device.*;
import com.things.cgomp.device.api.dto.UpdateChargeGridRulesDTO;
import com.things.cgomp.device.api.model.vo.ChargingAppPortDetailVO;
import com.things.cgomp.device.dto.device.*;
import com.things.cgomp.device.vo.device.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 充电桩设备表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
public interface IDeviceInfoService extends IService<DeviceInfo> {

    String insertGun(
            Long portId,
            Long insertTime,
            String vin
    );

    Map<Long, DeviceInfo> selectDeviceMap(
            List<Long> deviceIds
    );

    Long addChargeGrid(ChargeGridDTO chargeGridDTO);

    Boolean updateChargeGrid(UpdateChargeGridDTO chargeGridDTO);

    void deleteChargeGrid(
            Long deviceId
    );

    void updateRule(
            UpdateChargeGridRuleDTO chargeGridDTO
    );

    void updateRules(
            UpdateChargeGridRulesDTO chargeGridDTO
    );

    ChargeGridVo getChargeGridDetail(Long deviceId);


    Long addChargePort(ChargePortDTO addChargePort);

    void editChargePort(
            ChargePortDTO chargePortDTO
    );

    void deleteChargePort(
          Long deviceId
    );

    ChargePortDTO getChargePort(Long deviceId);

    PageInfo<DeviceGridVo> selectCgPage(DeviceGridDTO deviceListDTO);

    PageInfo<DevicePortVo> selectPortPage(DevicePortDTO deviceListDTO);

    List<SimpleDeviceVo> selectDevices(
            DeviceGridDTO deviceListDTO
    );

    Map<Long, List<DeviceInfo>> getDeviceInfoMap(List<Long> siteIds);

    List<DeviceInfo> selectDevices(List<Long> siteIds);

    ChargingAppPortDetailVO getAppChargePort(Long portId);

    DeviceConnectDO selectDeviceConnnectInfo(Long deviceId);

    DeviceInfo selectPortDevice(Long deviceId);

    DeviceInfo findDeviceInfo(DeviceInfo deviceInfoDTO);

    ChargingAppPortDetailVO getAppChargePortBySn(String portSn);

    List<DeviceGridVo> selectDeviceByIds(List<Long> deviceIds);

    DeviceCount selectDeviceCount(DeviceCountReqDTO reqDTO);

    DeviceGridDetailVO selectDeviceGridSpecificDetail(Long deviceId);

    DeviceDTO selectDevicePortSpecificDetail(Long deviceId);

    List<DeviceSimpleTreeVO> selectDeviceTreeList(ChargeGridTreeReqDTO req);

    DevicePortRealDataVO selectDevicePortRealData(Long deviceId);

    DevicePortEnergyDataVO selectDevicePortEnergyData(Long deviceId);

    DevicePortTrendDataVO selectDevicePortTrendData(Long deviceId);
}
