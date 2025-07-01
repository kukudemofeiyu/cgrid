package com.things.cgomp.common.device.service;

import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.device.constants.RedisKeyConstant;
import com.things.cgomp.common.device.dao.device.domain.DevicePortStatus;
import com.things.cgomp.common.device.dao.device.domain.PortChangeValue;
import com.things.cgomp.common.device.dao.device.mapper.DevicePortStatusMapper;
import com.things.cgomp.common.device.enums.PortStatusChangeFlag;
import com.things.cgomp.common.device.enums.PortStatusEnum;
import com.things.cgomp.common.device.enums.PortStatusOperate;
import com.things.cgomp.common.device.enums.YkcDeviceGunHomeStatusEnum;
import com.things.cgomp.common.device.pojo.device.DevicePortStatusDTO;
import com.things.cgomp.common.redis.service.RedisLock;
import com.things.cgomp.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 设备状态通用服务类
 *
 * @author things
 */
@Slf4j
@Service
public class DevicePortStatusService {

    @Resource
    private RedisService redisService;
    @Resource
    private RedisLock redisLock;
    @Resource
    private DevicePortStatusMapper devicePortStatusMapper;
    private static final String EMPTY_ORDER_SN = "00000000000000000000000000000000";

    public DevicePortStatus getPortStatus(Long portId) {
        return devicePortStatusMapper.selectDevicePortStatus(portId);
    }

    public static boolean isEmptyOrder(String orderSn){
        return StringUtils.isEmpty(orderSn) || EMPTY_ORDER_SN.equals(orderSn);
    }

    /**
     * 通用检查并修改充电枪状态入口
     *
     * @param checkReq 检查请求对象
     * @param operate  具体操作
     * @return true/false
     * @see PortStatusOperate
     */
    @Transactional(rollbackFor = Exception.class)
    public DevicePortStatus checkAndModifyPortStatus(DevicePortStatusDTO checkReq, PortStatusOperate operate) {
        if (checkReq == null || operate == null) {
            throw new RuntimeException("checkAndModifyPortStatus 枪口操作状态参数不能为空");
        }
        Long portId = checkReq.getPortId();
        // 获取锁
        String lockKey = getLockKey(portId);
        boolean hasLock = redisLock.tryLock(lockKey, TimeUnit.SECONDS, 5, 10);
        if (!hasLock) {
            log.error("checkAndModifyPortStatus tryLock fail, lockKey={}, checkReq={}", lockKey, checkReq);
            throw new RuntimeException("checkAndModifyPortStatus tryLock fail");
        }
        try {
            // 获取当前枪口状态
            String key = getStatusKey(portId);
            DevicePortStatus portCacheStatus = getPortCacheStatus(key, portId);
            // 检查是否需要修改
            DevicePortStatus modifyStatus = checkModify(checkReq, portCacheStatus, operate);
            if (modifyStatus != null) {
                // 修改状态
                processUpdate(key, portId, modifyStatus);
                log.info("checkAndModifyPortStatus 充电枪状态修改，checkReq={}, modifyStatus={}, operate={}", checkReq, modifyStatus, operate);
            }
            return modifyStatus;
        } catch (Exception e) {
            throw new RuntimeException("checkAndModifyPortStatus error", e);
        } finally {
            // 释放锁
            redisLock.unlock(lockKey);
        }
    }

    /**
     * 获取充电枪缓存状态
     * 1.先从redis中获取
     * 2.若redis中数据为空（上次写入redis中异常），则从mysql中读取
     *
     * @param cacheKey
     * @param portId
     * @return
     */
    public DevicePortStatus getPortCacheStatus(String cacheKey, Long portId) {
        DevicePortStatus portCacheStatus = redisService.getCacheObject(cacheKey);
        if (portCacheStatus == null) {
            // 若缓存为空，则从mysql中读取
            portCacheStatus = devicePortStatusMapper.selectById(portId);
        }
        return portCacheStatus;
    }

    /**
     * 修改状态数据，mysql、redis
     * 优先以mysql数据为准，由于状态检查对枪口进行加锁，采用先删缓存再需要策略，保证数据弱一致性
     *
     * @param key          redis缓存key
     * @param portId       充电枪ID
     * @param modifyStatus 修改对象
     */
    private void processUpdate(String key, Long portId, DevicePortStatus modifyStatus) {
        // 1.先删除缓存
        removeCache(key);
        // 2.更新数据库
        modifyDb(portId, modifyStatus);
        try {
            // 3.更新缓存，优先以数据库的数据为准
            // 若此处更新缓存失败时不抛出异常，等待下一次消息读取缓存为空时会从mysql中获取最新数据
            modifyCache(key, modifyStatus);
        } catch (Exception e) {
            log.warn("checkAndModifyPortStatus processUpdate modifyCache fail, key={}, modifyStatus={}", key, modifyStatus, e);
        }
    }

    private DevicePortStatus checkModify(DevicePortStatusDTO checkReq, DevicePortStatus portCacheStatus, PortStatusOperate operate) {
        DevicePortStatus modifyStatus = null;
        if (portCacheStatus == null) {
            modifyStatus = buildModifyStatus(modifyStatus, checkReq, operate);
        } else {
            if (checkReq.getEventTime() != null) {
                modifyStatus = buildModifyStatus(portCacheStatus, checkReq, operate);
            }
        }
        return modifyStatus;
    }

    private DevicePortStatus buildModifyStatus(DevicePortStatus portCacheStatus, DevicePortStatusDTO checkReq, PortStatusOperate operate) {
        if (portCacheStatus == null) {
            portCacheStatus = new DevicePortStatus();
        }
        switch (operate) {
            case inserted:
                // 插枪时设置VIN号和订单号
                log.debug("插枪修改状态, portCacheStatus={}, checkReqStatus={}", portCacheStatus, checkReq);
                portCacheStatus.setRemark(buildPortInserted(portCacheStatus, checkReq));
                break;
            case report:
                // 状态上报
                log.debug("修改充电枪上报状态, cacheStatus={}, checkReqStatus={}", portCacheStatus, checkReq);
                // 1.当前时间比缓存时间大时才更新
                // 2.枪口状态发生改变时才更新
                Long eventTime = checkReq.getEventTime();
                Long cacheStatusTime = portCacheStatus.getStatusTime();
                if (cacheStatusTime != null && eventTime < cacheStatusTime) {
                    log.warn("充电枪状态数据时间异常，状态不处理，请求时间：{}，缓存时间：{}", eventTime, cacheStatusTime);
                    // 时间异常不进行更新
                    return null;
                }
                // 构建状态上报变化
                String remark = buildReportModify(checkReq, portCacheStatus);
                if (remark == null) {
                    return null;
                }
                portCacheStatus.setRemark(StringUtils.isEmpty(remark) ? remark : remark.substring(1));
                // 枪状态空闲时清空订单号
                //portCacheStatus.setOrderSn(PortStatusEnum.FREE.getType().equals(checkReq.getStatus()) ? EMPTY_ORDER_SN : portCacheStatus.getOrderSn());
                break;
            case order_stop:
                // 订单结束，暂时不做任何处理
                log.debug("订单结束, checkReq={}", checkReq);
                break;
        }
        return portCacheStatus;
    }

    private String buildReportModify(DevicePortStatusDTO reqStatus, DevicePortStatus cacheStatus) {
        boolean isStatusModify = isStatusModify(reqStatus.getStatus(), cacheStatus.getStatus());
        boolean isPortInsertedModify = isPortInsertedModify(reqStatus.getPortInserted(), cacheStatus.getPortInserted());
        boolean isHomingStatusModify = isHomingStatusModify(reqStatus.getPortHoming(), cacheStatus.getHomeStatus());
        boolean isOrderSnModify = isOrderSnModify(reqStatus.getOrderSn(), cacheStatus.getOrderSn());
        if (!isStatusModify && !isPortInsertedModify && !isHomingStatusModify && !isOrderSnModify) {
            // 状态、插枪、归位状态均无发生变化，不进行更新
            return null;
        }
        StringBuilder remarkBuilder = new StringBuilder();
        if (isStatusModify) {
            // 枪状态变化
            String remark = buildStatusModify(cacheStatus, reqStatus);
            remarkBuilder.append(",").append(remark);
        }
        if (isOrderSnModify) {
            // 订单号变化
            String remark = buildOrderChange(cacheStatus, reqStatus);
            remarkBuilder.append(",").append(remark);
        }
        if (isPortInsertedModify) {
            // 是否插枪状态变化
            String remark = buildPortInsertedModify(cacheStatus, reqStatus);
            remarkBuilder.append(",").append(remark);
        }
        if (isHomingStatusModify) {
            // 归位状态变化
            String remark = buildPortHoming(cacheStatus, reqStatus);
            remarkBuilder.append(",").append(remark);
        }
        return remarkBuilder.toString();
    }

    private String buildStatusModify(DevicePortStatus portCacheStatus, DevicePortStatusDTO checkReq) {
        PortStatusEnum cacheStatus = PortStatusEnum.getPortStatusEnum(portCacheStatus.getStatus());
        PortStatusEnum reqStatus = PortStatusEnum.getPortStatusEnum(checkReq.getStatus());
        portCacheStatus.setStatus(checkReq.getStatus());
        portCacheStatus.setStatusTime(checkReq.getEventTime());
        if (cacheStatus != null && reqStatus != null) {
            switch (reqStatus) {
                case FREE:
                    portCacheStatus.setChangeFlag(PortStatusChangeFlag.free);
                    break;
                case CHARGE:
                    portCacheStatus.setChangeFlag(PortStatusChangeFlag.charging);
                    break;
            }
            return StringUtils.format("枪口状态从【{}】变成【{}】", cacheStatus.getDescription(), reqStatus.getDescription());
        }
        return StringUtils.format("枪口状态从【{}】变成【{}】", portCacheStatus.getStatus(), checkReq.getStatus());
    }

    private String buildPortInserted(DevicePortStatus portCacheStatus, DevicePortStatusDTO checkReq) {
        if (checkReq.getVin() != null && !checkReq.getVin().equals(portCacheStatus.getVin())) {
            // VIN号不相同时才更新插枪时间，保证同一VIN号只记录第一次插枪时间
            portCacheStatus.setPortInsertedTime(checkReq.getEventTime());
        }
        portCacheStatus.setChangeFlag(PortStatusChangeFlag.inserted);
        portCacheStatus.setVin(checkReq.getVin());
        portCacheStatus.setOrderSn(checkReq.getOrderSn());
        portCacheStatus.setPortInserted(true);
        portCacheStatus.setHomeStatus(YkcDeviceGunHomeStatusEnum.NOT_HOME.getStatus());
        return "插枪";
    }

    private String buildPortInsertedModify(DevicePortStatus portCacheStatus, DevicePortStatusDTO checkReq) {
        portCacheStatus.setPortInserted(checkReq.getPortInserted());
        if (portCacheStatus.getPortInserted()) {
            // 插枪动作，添加插枪时间
            portCacheStatus.setChangeFlag(PortStatusChangeFlag.inserted);
            portCacheStatus.setOrderSn(EMPTY_ORDER_SN);
            portCacheStatus.setVin(null);
            portCacheStatus.setPortInsertedTime(checkReq.getEventTime());
            return "插枪";
        } else {
            // 拔枪动作，拔枪则通知下游结算占位费并清空订单号、VIN等信息
            PortChangeValue changeValue = PortChangeValue.builder()
                    .orderSn(portCacheStatus.getOrderSn())
                    .vin(portCacheStatus.getVin())
                    .insertTime(portCacheStatus.getPortInsertedTime())
                    .build();
            portCacheStatus.setChangeFlag(PortStatusChangeFlag.drawn);
            portCacheStatus.setChangeValue(changeValue);
            // 清除缓存消息
            portCacheStatus.setOrderSn(EMPTY_ORDER_SN);
            portCacheStatus.setVin(null);
            portCacheStatus.setPortInsertedTime(null);
            return "拔枪";
        }
    }

    private String buildPortHoming(DevicePortStatus portCacheStatus, DevicePortStatusDTO checkReq) {
        portCacheStatus.setChangeFlag(PortStatusChangeFlag.homing);
        portCacheStatus.setHomeStatus(checkReq.getPortHoming());
        YkcDeviceGunHomeStatusEnum statusEnum = YkcDeviceGunHomeStatusEnum.getStatus(portCacheStatus.getHomeStatus());
        if (statusEnum != null) {
            return statusEnum.getDesc();
        }
        return "充电枪归位状态改变";
    }

    private String buildOrderChange(DevicePortStatus portCacheStatus, DevicePortStatusDTO checkReq) {
        String oldOrderSn = portCacheStatus.getOrderSn();
        // 设置变化标识
        portCacheStatus.setChangeFlag(PortStatusChangeFlag.order_change);
        PortChangeValue changeValue = PortChangeValue.builder()
                .orderSn(oldOrderSn)
                .build();
        portCacheStatus.setChangeValue(changeValue);
        portCacheStatus.setOrderSn(checkReq.getOrderSn());
        return StringUtils.format("订单号由【{}】变成【{}】", oldOrderSn, checkReq.getOrderSn());
    }

    @Transactional(rollbackFor = Exception.class)
    public DevicePortStatus modifyVin(DevicePortStatusDTO portStatusDTO){
        Long portId = portStatusDTO.getPortId();
        // 获取当前枪口状态
        DevicePortStatus portStatus = getPortStatus(portId);
        if (portStatus == null) {
            log.error("modifyVin 失败，枪口状态为空!");
            return null;
        }
        String oldVin = portStatus.getVin();
        Date now = new Date();
        portStatusDTO.setEventTime(now.getTime());
        portStatus.setUpdateTime(now);
        portStatus.setVin(portStatusDTO.getVin());
        portStatus.setRemark(StringUtils.format("VIN码由【{}】变成【{}】", oldVin, portStatus.getVin()));
        // 修改数据库
        devicePortStatusMapper.updateVin(portStatus);
        return portStatus;
    }

    private boolean isStatusModify(Integer reqStatus, Integer cacheStatus) {
        return reqStatus != null && !reqStatus.equals(cacheStatus);
    }

    private boolean isPortInsertedModify(Boolean reqPortInserted, Boolean cachePortInserted) {
        return reqPortInserted != null && !reqPortInserted.equals(cachePortInserted);
    }

    private boolean isHomingStatusModify(Integer homingStatus, Integer cacheStatus) {
        return homingStatus != null && !homingStatus.equals(cacheStatus);
    }

    private boolean isOrderSnModify(String orderSn, String cacheSn){
        return orderSn != null && !orderSn.equals(cacheSn);
    }

    private String getLockKey(Long portId) {
        return String.format(RedisKeyConstant.DEVICE_PORT_STATUS_LOCK_KEY, portId);
    }

    private String getStatusKey(Long portId) {
        return String.format(RedisKeyConstant.DEVICE_PORT_STATUS_KEY, portId);
    }

    private void modifyCache(String key, DevicePortStatus modifyStatus) {
        redisService.setCacheObject(key, modifyStatus);
    }

    private void removeCache(String key) {
        redisService.deleteObject(key);
    }

    private void modifyDb(Long portId, DevicePortStatus modifyStatus) {
        modifyStatus.setPortId(portId);
        modifyStatus.setUpdateTime(new Date());
        devicePortStatusMapper.saveOrUpdateStatus(modifyStatus);
        if (PortStatusChangeFlag.drawn.equals(modifyStatus.getChangeFlag())) {
            // 拔枪清空VIN码
            modifyStatus.setVin(null);
            devicePortStatusMapper.updateVin(modifyStatus);
        }
    }
}
