package com.things.cgomp.gateway.device.broker.ykc.service;

import com.things.cgomp.device.api.dto.RuleDTO;

import java.time.LocalDateTime;
import java.util.Map;

public interface IRequestDataService {

    /**
     * 请求计费规则
     * @param deviceId
     * @return
     * @throws Exception
     */
    RuleDTO getPayRule(Long deviceId) throws Exception;

    /**
     * 请求设备最新的RSA密钥
     * @param deviceId
     * @return
     */
    Map<String,String> getRSAKey(Long deviceId)  throws Exception;

    /**
     * 请求订单号
     * @param deviceId
     * @param insertGunTime
     * @param vin
     * @return
     */
    String getOrderNo(Long deviceId, LocalDateTime insertGunTime, String vin) throws Exception;

}
