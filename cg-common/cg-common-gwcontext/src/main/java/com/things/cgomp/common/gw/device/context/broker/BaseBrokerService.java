package com.things.cgomp.common.gw.device.context.broker;


import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.broker.constant.State;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import com.things.cgomp.common.gw.device.context.rest.IPushService;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import javafx.concurrent.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;


@Slf4j
public abstract class BaseBrokerService<P> implements HubBrokerLifecycle<P> {

/*    @Autowired
    protected DeviceServiceApiImpl deviceService;*/

    @Autowired
    private ApplicationContext applicationContext;

    protected volatile State serviceState = State.initialize;
    protected HubBrokerConfig initConfig;

    private SessionContext sessionContext;


    @Override
    public P init(HubBrokerConfig initConfig) throws ServiceException {
        this.initConfig = initConfig;

        try {
            sessionContext = sessionContextLoad();
            P p = processInit();
            serviceState = State.initializeComplete;
            return p;

        } catch (Exception e) {
            log.error("初始化失败,broker={}", initConfig, e);
            throw new ServiceException(ErrorCodeConstants.NO_CONFIG_BROKER_FILE);
        }

    }


    @Override
    public IPushService getPushService() {
        return null;
    }

    @Override
    public <C> C getService(String cmdId, String springServiceName, Class<C> cClass) {
        if (springServiceName == null || springServiceName.isEmpty()) {
            return null;
        }
        try {
            return applicationContext.getBean(springServiceName, cClass);
        } catch (Exception e) {
            log.error("getService error,cmdId={},springServiceName={},cClass={}", cmdId, springServiceName, cClass, e);
            return null;
        }

    }


    protected abstract SessionContext sessionContextLoad();

    @Override
    public SessionContext getSessionContext() {
        return this.sessionContext;
    }

    @Override
    public Integer getListenerPort() {
        return initConfig.getPort();
    }

    @Override
    public State getState() {
        return serviceState;
    }

    @Override
    public void start(P p) throws ServiceException {
        try {
            processStart(p);
            if (this.initConfig.isTaskCheck()) {
                starBrokerContextTask();
            }
            serviceState = State.runIng;

        } catch (Exception e) {
            log.error("启动失败,broker={}", initConfig, e);
            throw new ServiceException(ErrorCodeConstants.INIT_BROKER_FILE);
        }
    }

    public void starBrokerContextTask() {

    }

    @Override
    public void destroy() throws ServiceException {
        try {
            processDestroy();
        } catch (Exception e) {
            log.error("销毁失败,broker={}", initConfig);
        }
        serviceState = State.stop;
    }


    @Override
    public HubBrokerConfig getBrokerConfig() {
        return initConfig;
    }


    protected abstract P processInit() throws Exception;

    protected abstract void processStart(P p) throws Exception;

    protected abstract void processDestroy() throws Exception;


    protected HubBrokerConfig getConfig() {
        return initConfig;
    }


    @Override
    public Long getValidTime(Session session) {
        Integer abnormalOffLineTime = session.getAbnormalOffLineTime();
        if (abnormalOffLineTime == null) {
            return null;
        }

        return session.getLastActiveTime() + abnormalOffLineTime * 60 * 1000;
    }


}
