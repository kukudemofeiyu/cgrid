package com.things.cgomp.device.data.queue.consumer;

import com.things.cgomp.common.device.dao.node.domain.BrokerServiceRunInfo;
import com.things.cgomp.common.device.dao.node.domain.ServiceInfo;
import com.things.cgomp.common.device.dao.node.mapper.BrokerServiceRunInfoDao;
import com.things.cgomp.common.device.dao.node.mapper.ServiceInfoDao;
import com.things.cgomp.common.mq.annotation.RocketMQConsumer;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.consumer.AbrRocketMQConsumer;
import com.things.cgomp.common.mq.message.BrokerInfo;
import com.things.cgomp.common.mq.message.DeviceGwNodeInfoReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备网关节点消息消费者
 *
 * @author things
 */
@Slf4j
@RocketMQConsumer
@RocketMQMessageListener(
        topic = MQTopics.NODE,
        consumerGroup = MQTopics.Group.NODE
)
public class GwNodeCollectConsumer extends AbrRocketMQConsumer<DeviceGwNodeInfoReq> {

    @Resource
    private ServiceInfoDao serviceInfoDao;
    @Resource
    private BrokerServiceRunInfoDao brokerServiceRunInfoDao;

    @Override
    protected void onMessage(DeviceGwNodeInfoReq reqMsg, Metadata metadata) {

        ServiceInfo reqNodeInfo = buildReqNodeInfo(reqMsg, metadata);
        ServiceInfo serviceInfo = serviceInfoDao.findServiceInfoByServiceId(reqMsg.getNodeId());
        if (serviceInfo == null) {
            // 插入单个应用全量数据
            serviceInfoDao.save(reqNodeInfo);
            saveBrokerServiceRunInfos(
                    metadata,
                    reqNodeInfo.getServiceId(),
                    reqMsg.getBrokerInfoList()
            );
            log.info("新增应用上报的消息成功, serviceId={}", reqMsg.getNodeId());
        } else {
            // 更新单个应用全量数据
            serviceInfoDao.update(reqNodeInfo);
            updateBrokerServiceRunInfos(reqMsg, metadata);

            log.info("更新应用上报的消息成功, serviceId={}", reqMsg.getNodeId());
        }
    }

    private ServiceInfo buildReqNodeInfo(DeviceGwNodeInfoReq req, Metadata metadata) {
        return new ServiceInfo()
                .setServiceId(req.getNodeId())
                .setServiceName(req.getServiceName())
                .setVisitIp(req.getVisitIp())
                .setVisitPort(req.getVisitPort())
                .setContextPath(req.getContextPath())
                .setUpdateTime(new Timestamp(metadata.getEventTime()))
                .setTopic(req.getTopic());
    }

    private void saveBrokerServiceRunInfos(Metadata metadata, String serviceId, List<BrokerInfo> brokerInfos) {
        if (brokerInfos == null) {
            return;
        }
        brokerServiceRunInfoDao.deleteByServiceId(serviceId);
        brokerInfos.forEach(brokerInfo -> saveBrokerServiceRunInfo(metadata, serviceId, brokerInfo));
    }

    private void saveBrokerServiceRunInfo(Metadata metadata, String serviceId, BrokerInfo brokerInfo) {
        BrokerServiceRunInfo brokerServiceRunInfo = new BrokerServiceRunInfo()
                .setServiceId(serviceId)
                .setBrokerId(brokerInfo.getBrokerId())
                .setBrokerName(brokerInfo.getName())
                .setServicePort(brokerInfo.getServicePort())
                .setUpdateTime(new Timestamp(metadata.getEventTime()))
                .setState(brokerInfo.getState());
        brokerServiceRunInfoDao.insert(brokerServiceRunInfo);
    }

    private void updateBrokerServiceRunInfos(DeviceGwNodeInfoReq gwNodeInfoReq, Metadata metadata) {
        // 推送过来的brokerInfo列表
        List<BrokerInfo> currentBrokers = gwNodeInfoReq.getBrokerInfoList();
        // 推送过来的brokerInfo Map
        Map<Integer, BrokerInfo> currentBrokerInfoMap = new HashMap<>(16);
        currentBrokers.forEach(brokerInfo -> currentBrokerInfoMap.put(brokerInfo.getBrokerId(), brokerInfo));

        // 数据库的brokerInfo列表
        List<BrokerServiceRunInfo> brokerRunInfosFromDb =
                brokerServiceRunInfoDao.selectListByServiceId(gwNodeInfoReq.getNodeId());
        // 数据库的brokerInfo Map
        Map<Integer, BrokerServiceRunInfo> brokerInfoMapFromDb = new HashMap<>(16);
        brokerRunInfosFromDb
                .forEach(brokerInfo -> brokerInfoMapFromDb.put(brokerInfo.getBrokerId(), brokerInfo));

        brokerRunInfosFromDb.forEach(brokerRunInfoFromDb -> {
            BrokerInfo currentBrokerInfo = currentBrokerInfoMap.get(brokerRunInfoFromDb.getBrokerId());
            // 推送和数据库都存在，更新
            if (currentBrokerInfo != null) {
                brokerRunInfoFromDb.setServiceId(gwNodeInfoReq.getNodeId())
                        .setUpdateTime(new Timestamp(metadata.getEventTime()))
                        .setServicePort(currentBrokerInfo.getServicePort())
                        .setState(currentBrokerInfo.getState())
                        .setBrokerName(currentBrokerInfo.getName());
                brokerServiceRunInfoDao.updateById(brokerRunInfoFromDb);
            } else {
                // 推送不存在，数据库存在，删除
                brokerServiceRunInfoDao.deleteById(brokerRunInfoFromDb.getId());
            }
        });

        currentBrokerInfoMap.values()
                .forEach(currentBrokerInfo -> {
                    BrokerServiceRunInfo brokerRunInfoFromDb = brokerInfoMapFromDb.get(currentBrokerInfo.getBrokerId());
                    // 推送有，数据库不存在，插入
                    if (brokerRunInfoFromDb == null) {
                        saveBrokerServiceRunInfo(metadata, gwNodeInfoReq.getNodeId(), currentBrokerInfo);
                    }
                });
    }
}
