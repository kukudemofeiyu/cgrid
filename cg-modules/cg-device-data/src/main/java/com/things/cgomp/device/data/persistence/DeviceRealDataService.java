package com.things.cgomp.device.data.persistence;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.things.cgomp.common.device.dao.td.domain.BasePersistData;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.device.data.api.domain.RealDataQueryReq;
import com.things.cgomp.device.data.constants.CacheConstant;
import com.things.cgomp.device.data.constants.RealDataType;
import com.things.cgomp.device.data.utils.RealDataUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 设备实时数据服务
 *
 * @author thigns
 */
@Service
@RefreshScope
public class DeviceRealDataService {

    /**
     * 实时数据过期时间 默认120分钟
     */
    @Value("${data.real.expire:120}")
    private Long expireTime;

    @Resource
    private RedisService redisService;

    public void saveBatch(List<? extends BasePersistData> list, RealDataType dataType) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Map<String, Map<String, Object>> hashData = RealDataUtil.getRealDataHash(list, dataType.getTag());
        saveHashData(hashData);
    }

    private void saveHashData(Map<String, Map<String, Object>> hashData) {
        if (!CollectionUtils.isEmpty(hashData)) {
            redisService.hMSetForMap(hashData);
            // 设置过期时间
            hashData.keySet().forEach(k -> redisService.expire(k, expireTime, TimeUnit.MINUTES));
        }
    }

    public Map<String, BasePersistData> selectByDeviceIdAndOrderNo(RealDataQueryReq req) {
        String dataKey = buildKey(req.getDeviceId(), req.getOrderSn());
        Map<String, Object> objMap = redisService.hAllGet(dataKey);
        if (CollectionUtils.isEmpty(objMap)) {
            return new HashMap<>();
        }
        Map<String, BasePersistData> dataMap = new HashMap<>(objMap.size());
        objMap.forEach((k, v) -> {
            if (k == null) {
                return;
            }
            RealDataType dataType = RealDataType.getByTag(k);
            if (dataType == null) {
                return;
            }
            dataMap.put(k, parseObj(v, dataType.getClassT()));
        });
        return dataMap;
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePersistData> T selectByDeviceIdAndOrderNo(RealDataQueryReq req, RealDataType dataType) {
        String dataKey = buildKey(req.getDeviceId(), req.getOrderSn());
        Object obj = redisService.hget(dataKey, dataType.getTag());
        return (T) parseObj(obj, dataType.getClassT());
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePersistData> List<T> selectByDeviceIdsAndOrderNos(List<RealDataQueryReq> reqList, RealDataType dataType) {
        List<String> keys = new ArrayList<>();
        reqList.forEach(req -> {
            if (req.getDeviceId() == null || !StringUtils.hasLength(req.getOrderSn())) {
                return;
            }
            keys.add(buildKey(req.getDeviceId(), req.getOrderSn()));
        });
        if (CollectionUtils.isEmpty(keys)) {
            return new ArrayList<>();
        }
        return keys.stream()
                .map(key -> {
                    Object obj = redisService.hget(key, dataType.getTag());
                    if (obj == null) {
                        return null;
                    }
                    return (T) parseObj(obj, dataType.getClassT());
                })
                .filter(ObjectUtil::isNotNull)
                .collect(Collectors.toList());
    }

    private <T extends BasePersistData> T parseObj(Object obj, Class<T> pClass) {
        if (obj == null || pClass == null) {
            return null;
        }
        if (obj instanceof String) {
            return JSON.parseObject((String) obj, pClass);
        } else if (obj instanceof JSONObject) {
            return JSON.parseObject(JSON.toJSONString(obj), pClass);
        }
        return null;
    }

    private String buildKey(Long deviceId, String orderSn) {
        return String.format(CacheConstant.REDIS_DEVICE_REAL_DATA, deviceId, orderSn);
    }
}
