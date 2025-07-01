package com.things.cgomp.common.gw.device.context.broker;


import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.broker.constant.State;
import com.things.cgomp.common.gw.device.context.rest.IPushService;
import com.things.cgomp.common.gw.device.context.session.Session;

public interface IBroker<P> {

    /**
     * @return
     */
    HubBrokerConfig getBrokerConfig();


    /**
     * broker初始化
     *
     * @param initConfig
     */
    P init(HubBrokerConfig initConfig) throws ServiceException;

    /**
     * broker启动
     *
     * @param param
     */
    void start(P param) throws ServiceException;

    /**
     * broker销毁
     *
     * @param
     */
    void destroy() throws ServiceException;


    <C> C getService(String cmdId, String serviceName, Class<C> cClass);

    Integer getListenerPort();

    State getState();

    void starBrokerContextTask();

    IPushService getPushService();

    Long getValidTime(Session session);
}
