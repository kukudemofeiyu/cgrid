package com.things.cgomp.device.data.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.common.device.pojo.device.DevicePortKeyHistoryData;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.data.api.domain.DevicePortAllHistoryData;
import com.things.cgomp.device.data.service.IDeviceDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备历史数据控制器
 *
 * @author things
 */
@Log(title = "设备历史数据")
@RestController
@RequestMapping("/history")
public class DeviceHistoryDataController {

    @Resource
    private IDeviceDataService deviceDataService;

    /**
     * 对外接口，需要校验权限
     * 根据设备ID和订单号查询设备历史数据
     * @param orderNo 订单号
     * @return List<DevicePortData>
     */
    @GetMapping("/getByOrder")
    @RequiresPermissions("order:info:query")
    public R<List<DevicePortData>> getByOrder(@RequestParam("orderNo") String orderNo,
                                              @RequestParam(value = "deviceId", required = false) Long deviceId) {
        return R.ok(deviceDataService.selectHistoryData(deviceId, orderNo));
    }

    /**
     * 根据KEY查询历史数据
     * @param deviceId   设备ID
     * @param key        属性KEY
     * @param beginTime  开始时间
     * @param endTime    结束时间
     * @return           DevicePortKeyHistoryData
     */
    @GetMapping("/getByKey")
    @RequiresPermissions("device:port:detail")
    public R<DevicePortKeyHistoryData> selectByKey(@RequestParam("deviceId") Long deviceId,
                                                   @RequestParam("key") String key,
                                                   @RequestParam("beginTime") String beginTime,
                                                   @RequestParam("endTime") String endTime){
        return R.ok(deviceDataService.selectHistoryDataByKey(deviceId, key, beginTime, endTime));
    }


    /**
     * 内部接口
     * 根据订单号查询设备历史数据
     * @param orderNo 订单号
     * @return List<DevicePortData>
     */
    @InnerAuth
    @GetMapping("/selectByOrderNo")
    public R<List<DevicePortData>> selectByOrderNo(@RequestParam("deviceId") Long deviceId,
                                                   @RequestParam("orderNo") String orderNo){
        return R.ok(deviceDataService.selectHistoryData(deviceId, orderNo));
    }

    /**
     * 内部接口
     * 根据订单号查询设备历史数据
     * @param tradeSn 订单流水号
     * @return List<DevicePortData>
     */
    @InnerAuth
    @GetMapping("/selectByTradeSn")
    public R<List<DevicePortData>> selectByTradeSn(@RequestParam("deviceId") Long deviceId,
                                                   @RequestParam("tradeSn") String tradeSn){
        return R.ok(deviceDataService.selectHistoryDataByTradeSn(deviceId, tradeSn));
    }

    /**
     * 内部接口
     * 根据订单号查询设备全部历史数据
     * @param tradeSn 订单流水号
     * @return List<DeviceData>
     */
    @InnerAuth
    @GetMapping("/selectAllByTradeSn")
    public R<DevicePortAllHistoryData> selectAllByTradeSn(@RequestParam("deviceId") Long deviceId,
                                                          @RequestParam("tradeSn") String tradeSn) {
        return R.ok(deviceDataService.selectAllHistoryDataByTradeSn(deviceId, tradeSn));
    }
}
