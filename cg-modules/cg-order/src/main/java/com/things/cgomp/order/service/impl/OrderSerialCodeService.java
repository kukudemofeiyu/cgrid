package com.things.cgomp.order.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.redis.service.RedisLock;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.order.constants.OrderConstant;
import com.things.cgomp.order.enums.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 订单号生成服务类
 *
 * @author things
 */
@Slf4j
@Service
public class OrderSerialCodeService {

    @Resource
    private RedisService redisService;
    @Resource
    private RedisLock redisLock;

    /**
     * 生成订单号
     * 订单号规则32位：桩号14位 + 枪号2位 + 年月日时分秒12位 + 自增序号2位
     *
     * @param deviceSn  充电桩SN
     * @param portSn    枪号
     * @return          订单号
     */
    public String genSerialCode(String deviceSn, String portSn) {
        // 获取当前日期
        String currentDateTime = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT);
        // 获取锁
        String lockKey = getLockKey(currentDateTime);
        boolean hasLock = redisLock.tryLock(lockKey, TimeUnit.SECONDS, 5, 5);
        if (!hasLock) {
            throw exception(ErrorCodeConstants.ORDER_SERIAL_CODE_GEN_FAIL);
        }
        try {
            // 获取自增序号
            String serialIndex = genSerialNumber(currentDateTime);
            StringBuilder serialCode = new StringBuilder()
                    .append(StringUtils.leftPad(deviceSn, 14, '0'))           // 桩号，14位，不够前面补0
                    .append(StringUtils.leftPad(portSn, 2, '0'))              // 枪号  2位
                    .append(currentDateTime)                                  // 年月日时分秒14位
                    .append(serialIndex)                                      // 自增序号2位
                    ;
            return serialCode.toString();
        } catch (Exception e) {
            log.error("genSerialCode error, ", e);
            throw exception(ErrorCodeConstants.ORDER_SERIAL_CODE_GEN_FAIL);
        } finally {
            // 释放锁
            redisLock.unlock(lockKey);
        }
    }

    private String genSerialNumber(String date) {
        int index = 1;
        String key = getIndexKey(date);
        Object serialIndex = redisService.getCacheObject(key);
        if (ObjectUtil.isNotEmpty(serialIndex)) {
            index = Integer.parseInt(String.valueOf(serialIndex)) + 1;
        }
        // 记录自增序号，设置5秒后过期
        redisService.setCacheObject(key, index, 5L, TimeUnit.SECONDS);
        // 自增序号长度设置为2位
        return String.format("%02d", index);
    }

    private String getLockKey(String dataTime) {
        return OrderConstant.ORDER_CODE_LOCK_KEY + ":" + dataTime;
    }

    private String getIndexKey(String date) {
        return OrderConstant.ORDER_CODE_INDEX_KEY + ":" + date;
    }
}