package com.things.cgomp.device.data.api;

import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.device.data.api.domain.DevicePortAllHistoryData;
import com.things.cgomp.device.data.api.domain.DevicePortAllRealData;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import com.things.cgomp.device.data.api.factory.RemoteDeviceDataFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(contextId = "remoteDeviceDataService",
        value = ServiceNameConstants.SYSTEM_DEVICE_DATA,
//        url = "http://localhost:9017",
        fallbackFactory = RemoteDeviceDataFallbackFactory.class)
public interface RemoteDeviceDataService {

    /**
     * 根据设备ID和订单号查询设备实时数据
     *
     * @param req 请求对象
     * @param source   来源
     * @return List<DeviceData>
     */
    @GetMapping(value = "/realData/single", name = "根据设备和订单号查询实时数据")
    R<DevicePortData> selectRealDataByOrderNo(@SpringQueryMap RealDataQueryReq req,
                                              @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据设备ID和订单号批量查询设备实时数据
     *
     * @param reqListStr 请求对象序列化后字符串
     * @param source   来源
     * @return List<DeviceData>
     */
    @GetMapping(value = "/realData/batch", name = "根据设备和订单号批量查询实时数据")
    R<List<DevicePortData>> selectRealDataByOrderNo(@RequestParam("reqList") String reqListStr,
                                                    @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据设备ID和订单号批量查询设备实时数据
     *
     * @param req 请求对象
     * @param source   来源
     * @return DevicePortAllData
     */
    @GetMapping(value = "/realData/all", name = "根据设备和订单号批量查询所有实时数据")
    R<DevicePortAllRealData> selectAllRealDataByOrderNo(@SpringQueryMap RealDataQueryReq req,
                                                        @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据设备ID和订单号查询设备历史数据
     *
     * @param orderNo 订单号
     * @param source  来源
     * @return List<DeviceData>
     */
    @GetMapping(value = "/history/selectByOrderNo", name = "根据订单号查询设备历史数据")
    R<List<DevicePortData>> selectHistoryByOrderNo(@RequestParam(value = "deviceId") Long deviceId,
                                                   @RequestParam(value = "orderNo") String orderNo,
                                                   @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据设备ID和流水号查询设备历史数据
     *
     * @param tradeSn 流水号
     * @param source  来源
     * @return List<DeviceData>
     */
    @GetMapping(value = "/history/selectByTradeSn", name = "根据订单号查询设备历史数据")
    R<List<DevicePortData>> selectHistoryByTradeSn(@RequestParam(value = "deviceId") Long deviceId,
                                                   @RequestParam(value = "tradeSn") String tradeSn,
                                                   @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据设备ID和流水号查询设备全部历史数据
     *
     * @param tradeSn 流水号
     * @param source  来源
     * @return List<DevicePortAllHistoryData>
     */
    @GetMapping(value = "/history/selectAllByTradeSn", name = "根据订单号查询设备全部历史数据")
    R<DevicePortAllHistoryData> selectAllHistoryByTradeSn(@RequestParam(value = "deviceId") Long deviceId,
                                                          @RequestParam(value = "tradeSn") String tradeSn,
                                                          @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
