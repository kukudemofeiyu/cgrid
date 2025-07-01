package com.things.cgomp.alarm.service.impl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.alarm.domain.DeviceAlarmInfo;
import com.things.cgomp.alarm.dto.DeviceAlarmDTO;
import com.things.cgomp.alarm.mapper.DeviceAlarmMapper;
import com.things.cgomp.alarm.service.IDeviceAlarmService;
import com.things.cgomp.alarm.vo.DeviceAlarmVO;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.device.dao.device.mapper.DeviceInfoMapper;
import com.things.cgomp.common.device.enums.DeviceAlarmTypeEnum;
import com.things.cgomp.common.device.pojo.device.DeviceGridVo;
import com.things.cgomp.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;

@Service
public class DeviceAlarmServiceImpl extends ServiceImpl<DeviceAlarmMapper, DeviceAlarmInfo> implements IDeviceAlarmService {

    @Autowired
    private DeviceAlarmMapper deviceAlarmMapper;
    @Autowired
    private DeviceInfoMapper deviceInfoMapper;


    @DataScope(orgAlias = "os", userSiteAlias = "us", userOperatorAlias = "uop")
    @Override
    public PageInfo<DeviceAlarmVO> selectAlarmPage(DeviceAlarmDTO deviceAlarmDTO) {
        startPage();

        List<DeviceAlarmInfo> deviceAlarmInfos = deviceAlarmMapper.selectAlarmPage(deviceAlarmDTO);
        if (!CollectionUtils.isEmpty(deviceAlarmInfos)) {

            List<Long> portIds = deviceAlarmInfos.stream().map(DeviceAlarmInfo::getPortId).collect(Collectors.toList());
            Map<Long, DeviceGridVo> collect = new HashMap<>();
            List<DeviceGridVo> deviceGridVos = deviceInfoMapper.selectDeviceByIds(portIds);
            if(!CollectionUtils.isEmpty(deviceGridVos)){
                Map<Long, DeviceGridVo> collect2 = deviceGridVos.stream().collect(Collectors.toMap(DeviceGridVo::getDeviceId, Function.identity()));

                collect.putAll(collect2);
            }

            List<DeviceAlarmVO> deviceAlarmVOS = deviceAlarmInfos.stream()
                    .map(deviceAlarmInfo -> {

                        DeviceAlarmVO deviceAlarmVO = convertAlarmInfoToVo(deviceAlarmInfo);
                        deviceAlarmVO.setPortSn(collect.get(deviceAlarmInfo.getPortId()).getAliasSn());
                        return deviceAlarmVO;
                    }).collect(Collectors.toList());


            return PageInfo.of(deviceAlarmVOS);
        }
        return PageInfo.emptyPageInfo();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean dealAlarm(Long alarmId) {
        DeviceAlarmInfo deviceAlarmInfo = new DeviceAlarmInfo();
        deviceAlarmInfo.setId(alarmId);
        deviceAlarmInfo.setRecoveryTime(new Date());
        deviceAlarmInfo.setRecoveryType(1);
        deviceAlarmInfo.setDealStatus(1);
        deviceAlarmInfo.setDealBy(SecurityUtils.getUserId());

        int i = deviceAlarmMapper.updateById(deviceAlarmInfo);
        if(i>0) {
            return true;
        }
        return false;
    }

    private DeviceAlarmVO convertAlarmInfoToVo(DeviceAlarmInfo deviceAlarmInfo) {
        return DeviceAlarmVO.builder()
                .alarmId(deviceAlarmInfo.getId())
                .sn(deviceAlarmInfo.getSn())
                .ts(deviceAlarmInfo.getAlarmTime())
                .alarmStatus(deviceAlarmInfo.getDealStatus())
                .alarmType(DeviceAlarmTypeEnum.getTypeDesc( deviceAlarmInfo.getType()))
                .content(deviceAlarmInfo.getReason())
                .build();
    }
}
