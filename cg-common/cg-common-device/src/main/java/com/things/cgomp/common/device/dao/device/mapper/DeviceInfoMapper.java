package com.things.cgomp.common.device.dao.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.common.device.pojo.device.*;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 充电桩设备表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

    DeviceInfo findDeviceInfo(DeviceInfo deviceInfoDTO);

    List<DeviceGridVo> selectCgPage(DeviceGridDTO deviceListDTO);

    List<DevicePortVo> selectDevicePorts(DevicePortDTO deviceListDTO);

    List<SimpleDeviceVo> selectSimpleDeviceGrids(DeviceGridDTO deviceListDTO);

    List<DeviceInfo> selectDevices(@Param("siteIds") List<Long> siteIds);

    Integer countDevices(
            @Param("parentId") Long parentId
    );

    List<DeviceInfo> selectDevicesByParentId(
            @Param("parentId") Long parentId
    );

    List<DevicePortVo> selectDevicePortsByParentId(@Param("parentId") Long parentId);

    List<DeviceInfo> selectDevicesByPayRuleId(
            @Param("payRuleId") Long payRuleId
    );

    /**
     * 查询当前桩启动的枪
     *
     * @param parentId
     * @return
     */
    List<DeviceInfo> selectChild(
            @Param("parentId") Long parentId
    );

    /**
     * 获取设备网络状态信息
     *
     * @param deviceId
     * @return
     */
    DeviceConnectDO selectDeviceConnnectInfo(@Param("deviceId") Long deviceId);

    DeviceInfo selectPortDevice(@Param("deviceId") Long deviceId);

    Boolean updateDeviceCurrentPayRule(@Param("deviceId") Long deviceId,
                                       @Param("currentModelId") Integer currentModelId,
                                       @Param("currentPayRuleId") Long currentPayRuleId);

    DeviceInfo selectBySn(String sn);

    DeviceDTO selectDeviceById(@Param("deviceId") Long deviceId);

    List<DeviceGridVo> selectDeviceByIds(@Param("ids") List<Long> deviceIds);

    DeviceCount selectDeviceCount(DeviceCountReqDTO reqDTO);

    List<DeviceDTO> selectDeviceList(ChargeGridTreeReqDTO req);
}