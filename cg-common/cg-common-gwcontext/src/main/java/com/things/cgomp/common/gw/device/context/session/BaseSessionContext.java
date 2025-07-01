package com.things.cgomp.common.gw.device.context.session;

import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.broker.IBroker;
import com.things.cgomp.common.gw.device.context.broker.config.HubBrokerConfig;
import com.things.cgomp.common.gw.device.context.session.state.downline.OffLineState;
import com.things.cgomp.common.gw.device.context.session.state.online.ConnectIngState;
import com.things.cgomp.common.gw.device.context.session.state.online.FirstConnectAuthPassState;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public abstract class BaseSessionContext<T extends Session, channel> implements LocalSessionContext<Long, T, channel> {

    protected Map<Long /**deviceId **/, T> globalSession = new ConcurrentHashMap();
    protected IDeviceServiceApi deviceService;
    private HubBrokerConfig config;

    private IBroker broker;

    private int default_SyncSessionIntervalTime = 3 * 60 * 1000;


    @Override
    public IDeviceServiceApi getDeviceService() {
        return deviceService;
    }

    @Override
    public IBroker getBroker() {
        return this.broker;
    }

    public BaseSessionContext(IDeviceServiceApi deviceService, IBroker broker) {
        this.deviceService = deviceService;
        this.config = broker.getBrokerConfig();
        this.broker = broker;

    }

    @Override
    public T keepSession(T session) {
        if (session.isAuth()) {
            session.setLastActiveTime(System.currentTimeMillis());
            SessionState sessionState = session.getSessionState();
            session.setSessionState(sessionState == null ? new FirstConnectAuthPassState(this, deviceService) : new ConnectIngState(this, deviceService, null == config.getSyncSessionIntervalTime() ? default_SyncSessionIntervalTime : config.getSyncSessionIntervalTime()));
            session.getSessionState().exe(session);
        }
        return session;
    }

    @Override
    public void putSession(Long deviceId, T session) {
        globalSession.put(deviceId, session);
    }

    @Override
    public channel getChannel(Long deviceId) {
        Session<channel> t = getSession(deviceId);
        if (t == null) {
            return null;
        }
        return t.getChannel();
    }


    @Override
    public T getSession(Long deviceId) {
        return globalSession.get(deviceId);
    }

    @Override
    public void releaseSession(T session, SessionCloseCause closeCause, boolean isDelSession) {
        boolean beforeStatusIsOffLine;
        synchronized (session) {
            beforeStatusIsOffLine = beforeStatusIsOffLine(session);
            if (!beforeStatusIsOffLine) {
                session.setSessionState(new OffLineState(this, closeCause, deviceService));
            }
        }

        if (session.isAuth() && session.getDeviceId() != null && globalSession.containsKey(session.getDeviceId())) {
            // session未改变（true:未改变；false:已改变）
            boolean isSessionUnchanged = false;
            if (isDelSession) {
                synchronized (globalSession) {
                    isSessionUnchanged = isSessionUnchanged(session);
                    if (isSessionUnchanged) {
                        log.info("release Session, deviceId={}, closeCause={}", session.getDeviceId(), closeCause);
                        globalSession.remove(session.getDeviceId());
                    }
                }
            }

            //之前就是下线了,不重发下线消息
            if (!beforeStatusIsOffLine && isSessionUnchanged) {
                session.getSessionState().exe(session);
            }

        }

    }

    /**
     * session未改变
     */
    private boolean isSessionUnchanged(T session) {
        Session cacheSession = globalSession.get(session.getDeviceId());
        return cacheSession != null
                && cacheSession.getSessionId().equals(session.getSessionId());
    }

    protected boolean beforeStatusIsOffLine(T session) {
        if (session.getSessionState() == null) {
            return false;
        }
        if (session.getSessionState() instanceof OffLineState) {
            return true;
        }
        return false;
    }

    protected Integer getAbnormalOffLineTime() {
        return broker.getBrokerConfig()
                .getAbnormalOffLineTime();
    }

}
