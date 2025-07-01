package com.things.cgomp.common.gw.device.context.broker.config;


import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.device.dao.node.domain.BrokerConfigInfo;
import com.things.cgomp.common.device.service.BrokerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ConditionalOnExpression("'${node.broker.readFor}'=='db'")
@Configuration
public class LoadConfigFromDB implements ILoadConfig {

    @Autowired
    private BrokerConfigService brokerConfigService;

    @Override
    public Map<Integer, HubBrokerConfig> read() throws ServiceException {
        List<BrokerConfigInfo> list = brokerConfigService.queryAll();
        if (list != null && list.size() > 0) {
            return list.stream().collect(Collectors.toMap(BrokerConfigInfo::getBrokerId,
                    info -> HubBrokerConfig.builder()
                            .brokerId(info.getBrokerId())
                            .protocol(info.getProtocol())
                            .start(info.getStatus() == 1).
                            port(info.getPort()).abnormalOffLineTime(info.getAbnormalOffLineTime())
                            .keepaliveTime(info.getKeepaliveTime())
                            .name(info.getBrokerName())
                            .serviceBean(info.getServiceBean()).
                            configInfo(info.getConfigInfo())
                            .syncSessionIntervalTime(info.getSyncSessionIntervalTime())
                            .taskCheck(info.getTaskCheck() == 1)
                            .build()));
        }
        return null;
    }


}
