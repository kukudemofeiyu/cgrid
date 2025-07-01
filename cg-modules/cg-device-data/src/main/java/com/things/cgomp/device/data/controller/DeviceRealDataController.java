package com.things.cgomp.device.data.controller;

import com.alibaba.fastjson2.JSON;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.device.data.api.domain.DevicePortAllRealData;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import com.things.cgomp.device.data.service.IDeviceDataService;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备实时数据控制器
 *
 * @author things
 */
@Log(title = "设备实时数据")
@RestController
@RequestMapping("/realData")
public class DeviceRealDataController {

    @Resource
    private IDeviceDataService deviceDataService;

    /**
     * 内部接口
     * 根据设备ID查询实时数据
     * @param req 请求对象
     * @return DeviceData
     */
    @InnerAuth
    @GetMapping(value = "single", name = "单个查询")
    public R<DevicePortData> selectByOrderNo(@SpringQueryMap RealDataQueryReq req){
        return R.ok(deviceDataService.selectRealData(req));
    }

    /**
     * 内部接口
     * 批量根据设备ID查询实时数据
     * @param reqListStr 请求对象序列化后
     * @return DeviceData
     */
    @InnerAuth
    @GetMapping(value = "batch", name = "批量查询")
    public R<List<DevicePortData>> selectByOrderNoBatch(@RequestParam("reqList") String reqListStr){
        List<RealDataQueryReq> reqList = JSON.parseArray(reqListStr.trim(), RealDataQueryReq.class);
        return R.ok(deviceDataService.selectRealDataBatch(reqList));
    }

    /**
     * 内部接口
     * 根据设备ID查询所有实时数据
     * @param req 请求对象
     * @return DevicePortAllData
     */
    @InnerAuth
    @GetMapping(value = "all", name = "查询充电枪所有数据")
    public R<DevicePortAllRealData> selectAllByOrderNo(@SpringQueryMap RealDataQueryReq req){
        return R.ok(deviceDataService.selectAllRealData(req));
    }
}
