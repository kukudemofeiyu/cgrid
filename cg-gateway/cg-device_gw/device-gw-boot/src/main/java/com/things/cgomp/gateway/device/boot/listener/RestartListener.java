package com.things.cgomp.gateway.device.boot.listener;

import com.things.cgomp.common.device.constants.RedisKeyConstant;
import com.things.cgomp.common.gw.device.context.broker.config.ServiceNodeInfo;
import com.things.cgomp.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RestartListener implements DisposableBean {
    @Resource
    private RedisService redisService;
    @Resource
    private ServiceNodeInfo serviceNodeInfo;

    @Override
    public void destroy() {
        try{
            // 服务器重启，保存标志位
            String key = RedisKeyConstant.DEVICE_GATEWAY_RESTART_PREFIX + serviceNodeInfo.getNodeId();
            redisService.setCacheObject(key, "1", 5L, TimeUnit.MINUTES);
        }catch (Exception e){
            log.error("保存标志位服务器重启失败");
        }

    }
}
