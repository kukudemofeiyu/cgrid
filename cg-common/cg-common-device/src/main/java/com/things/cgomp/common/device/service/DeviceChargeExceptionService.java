package com.things.cgomp.common.device.service;

import com.things.cgomp.common.device.dao.device.domain.DeviceChargeRecordException;
import com.things.cgomp.common.device.dao.device.mapper.DeviceChargeExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeviceChargeExceptionService {

    @Autowired
    private DeviceChargeExceptionMapper deviceChargeExceptionMapper;

    @Transactional
    public void save(DeviceChargeRecordException deviceChargeRecordException){
        deviceChargeExceptionMapper.insert(deviceChargeRecordException);
    }

    @Transactional
    public void deleteRecord(String orderNo){
        deviceChargeExceptionMapper.deleteByOrderNo(orderNo);
    }

    public List<DeviceChargeRecordException> queryAll() {
        return deviceChargeExceptionMapper.queryAll();
    }

    public Long findIdByOrderNo(String orderNo) {
        return deviceChargeExceptionMapper.findIdByOrderNo(orderNo);
    }

}
