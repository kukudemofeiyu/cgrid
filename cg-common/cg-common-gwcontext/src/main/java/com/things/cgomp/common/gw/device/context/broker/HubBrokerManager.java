package com.things.cgomp.common.gw.device.context.broker;


import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.gw.device.context.api.queue.MessageRequestTemplate;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.broker.config.ILoadConfig;
import com.things.cgomp.common.gw.device.context.broker.config.ServiceNodeInfo;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import com.things.cgomp.common.gw.device.context.rest.IPushService;
import com.things.cgomp.common.mq.common.Metadata;
import com.things.cgomp.common.mq.common.QueueMsg;
import com.things.cgomp.common.mq.constants.MQTopics;
import com.things.cgomp.common.mq.message.BrokerInfo;
import com.things.cgomp.common.mq.message.DeviceGwNodeInfoReq;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class HubBrokerManager  {

    @Autowired(required = false)
    private ILoadConfig loadConfig;
    /**
     * 信息收集上报频率 单位为毫秒
     */
    @Value("${hub.manager.collect.frequency:60000}")
    private Long collectFrequency;

    private volatile Map<Integer/** brokerId*/, IBroker> brokerRef;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ServiceNodeInfo nodeInfo;
    @Autowired
    private MessageRequestTemplate messageRequestTemplate;

    @PostConstruct
    public void init() throws ServiceException {
        brokerRef = new ConcurrentHashMap<>();
        try {
            log.info("HubBrokerManager init start");
            Map<Integer, HubBrokerConfig> brokerConfigMap = loadConfig.read();
            for (Map.Entry<Integer, HubBrokerConfig> entry : brokerConfigMap.entrySet()) {
                Integer brokerId = entry.getKey();
                HubBrokerConfig brokerConfig = entry.getValue();
                brokerConfig.setHubNodeInfo(nodeInfo);
                log.info("HubServiceManager init,brokerId:{}", brokerId);
                IBroker hubServiceLifecycle = (IBroker) applicationContext.getBean(brokerConfig.getServiceBean());
                if (brokerConfig.getStart()) {
                    Object configParam = hubServiceLifecycle.init(brokerConfig);
                    hubServiceLifecycle.start(configParam);
                    brokerRef.put(brokerConfig.getBrokerId(), hubServiceLifecycle);
                } else {
                    log.info("配置为不需要启动,{}",  entry);
                }
            }
            log.info("HubBrokerManager init complete");
            startMonitor();
        } catch (ServiceException e) {
            log.error("init exception error", e);
            throw e;
        } catch (Exception e) {
            log.error("init Exception error", e);
            throw new ServiceException(ErrorCodeConstants.INIT_BROKER_FILE);
        }

    }

    private void startMonitor() {
        new Thread(() -> {

            for (; ; ) {
                try {
                    List<BrokerInfo> brokerInfoList = new ArrayList<>();
                    for (Map.Entry<Integer, IBroker> entry : brokerRef.entrySet()) {
                        IBroker lifecycle = entry.getValue();
                        HubBrokerConfig hubBrokerConfig = lifecycle.getBrokerConfig();
                        brokerInfoList.add(BrokerInfo.builder()
                                .brokerId(hubBrokerConfig.getBrokerId())
                                .servicePort(lifecycle.getListenerPort())
                                .state(lifecycle.getState().getValue())
                                .name(hubBrokerConfig.getName()).build());
                    }
                    DeviceGwNodeInfoReq reqMsg = DeviceGwNodeInfoReq.builder()
                            .nodeId(nodeInfo.getNodeId())
                            .serviceName(nodeInfo.getServiceName())
                            .visitIp(nodeInfo.getVisitIp())
                            .visitPort(nodeInfo.getVisitPort())
                            .contextPath(nodeInfo.getContextPath())
                            .topic(nodeInfo.getTopic())
                            .brokerInfoList(brokerInfoList)
                            .build();
                    String uuid = UUID.randomUUID().toString();
                    QueueMsg msg = QueueMsg.builder().build();
                    msg.setMetadata(Metadata.builder().eventTime(System.currentTimeMillis()).requestId(uuid).txId(uuid).build());
                    msg.setBody(reqMsg);
                    messageRequestTemplate.asyncSend(MQTopics.NODE, null, msg, new SimpleServiceCallback<Void>() {
                        @Override
                        public void onSuccess(Void msg) {
                            log.info("node节点上报成功,reqMsg:{}", reqMsg);
                        }

                        @Override
                        public void onError(Throwable e) {
                            log.info("node节点上报失败,reqMsg:{}", reqMsg);

                        }
                    });

                } catch (Exception e) {
                    log.error("startMonitor.error", e);
                } finally {
                    try {
                        Thread.sleep(collectFrequency);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }, "hub_node_manager").start();


    }

    public IPushService getPushService(Integer brokerId) {
        if (null == brokerId) {
            return null;
        }
        IBroker iBroker = brokerRef.get(brokerId);
        if (null == iBroker) {
            return null;
        }
        if (iBroker instanceof IPushService) {
            return (IPushService) iBroker;
        }

        return iBroker.getPushService();

    }

    public HubBrokerLifecycle getBroker(Integer brokerId){
        if (null == brokerId) {
            return null;
        }
        IBroker iBroker = brokerRef.get(brokerId);
        return (HubBrokerLifecycle)iBroker;
    }
}
