package com.things.cgomp.common.gw.device.context.broker.config;



import com.things.cgomp.common.core.exception.ServiceException;

import java.util.Map;


public interface ILoadConfig {
    Map<Integer, HubBrokerConfig> read() throws ServiceException;

}
