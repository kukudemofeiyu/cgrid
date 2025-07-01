package com.things.cgomp.device.data.utils;

import com.alibaba.fastjson2.JSON;
import com.things.cgomp.common.device.dao.td.domain.BasePersistData;
import com.things.cgomp.common.device.dao.td.domain.DevicePortData;
import com.things.cgomp.device.data.constants.CacheConstant;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author things
 */
public class RealDataUtil {

    public static Map<String, DevicePortData> getRealDataMap(List<DevicePortData> list) {
        return list.stream()
                .filter(e -> e.getDeviceId() != null && e.getOrderSn() != null && e.getTs() != null)
                .collect(Collectors.toMap(
                        d -> String.format(CacheConstant.REDIS_DEVICE_REAL_DATA, d.getDeviceId(), d.getOrderSn()),
                        d -> {
                            d.setEventTime(new Date(d.getTs()));
                            return d;
                        }, (v1, v2) -> {
                            // 同一个设备ID只保留最新的一条数据
                            if (v2.getTs() >= v1.getTs()) {
                                return v2;
                            }
                            return v1;
                        }));
    }

    public static <T extends BasePersistData> Map<String, Map<String, Object>> getRealDataHash(List<T> list, String hashKey) {
        return list.stream()
                .filter(e -> e.getDeviceId() != null && e.getOrderSn() != null && e.getTs() != null)
                .collect(Collectors.toMap(
                        d -> String.format(CacheConstant.REDIS_DEVICE_REAL_DATA, d.getDeviceId(), d.getOrderSn()),
                        d -> {
                            if (d instanceof DevicePortData) {
                                DevicePortData dd = (DevicePortData) d;
                                dd.setEventTime(new Date(d.getTs()));
                            }
                            Map<String, Object> hashItem = new HashMap<>();
                            hashItem.put(hashKey, JSON.toJSONString(d));
                            return hashItem;
                        },
                        (v1, v2) -> v2
                ));
    }

    public static <T extends BasePersistData> Map<String, T> getRealDataJson(List<T> list) {
        return list.stream()
                .filter(e -> e.getDeviceId() != null && e.getOrderSn() != null && e.getTs() != null)
                .collect(Collectors.toMap(
                        d -> String.format(CacheConstant.REDIS_DEVICE_REAL_DATA, d.getDeviceId(), d.getOrderSn()),
                        d -> {
                            if (d instanceof DevicePortData) {
                                DevicePortData dd = (DevicePortData) d;
                                dd.setEventTime(new Date(d.getTs()));
                            }
                            return d;
                        },
                        (v1, v2) -> {
                            // 同一个设备ID只保留最新的一条数据
                            if (v2.getTs() >= v1.getTs()) {
                                return v2;
                            }
                            return v1;
                        }));
    }
}
