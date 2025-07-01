package com.things.cgomp.common.gw.device.context.api;

import com.things.cgomp.common.device.pojo.queue.AuthReqMsg;
import com.things.cgomp.common.device.pojo.queue.AuthResponseMsg;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.mq.message.DeviceOffLineReqMsg;
import com.things.cgomp.common.mq.message.DeviceOnLineReqMsg;
import com.things.cgomp.common.mq.message.DeviceSessionSyncReqMsg;
import com.things.cgomp.common.mq.message.callback.CallBackMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;

public interface DeviceLifecycleMessageService {

    void auth(AuthReqMsg reqMsg, SimpleServiceCallback<CallBackMsg<AuthResponseMsg>> callback);

    void onLine(DeviceOnLineReqMsg reqMsg, Session session, SimpleServiceCallback<Void> callback);

    void offLine(DeviceOffLineReqMsg reqMsg, Session session, SimpleServiceCallback<Void> callback);

    void syncSessionInfo(DeviceSessionSyncReqMsg reqMsg, Session session, SimpleServiceCallback<Void> callback);

    public void authChild(SessionContext sessionContext, Session session, String deviceName) ;
}
