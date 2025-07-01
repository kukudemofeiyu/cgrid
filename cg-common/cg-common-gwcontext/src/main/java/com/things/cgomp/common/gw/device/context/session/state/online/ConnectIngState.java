package com.things.cgomp.common.gw.device.context.session.state.online;

import com.things.cgomp.common.gw.device.context.api.DeviceLifecycleMessageService;
import com.things.cgomp.common.gw.device.context.session.SessionDeviceInfo;
import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.gw.device.context.session.SessionContext;
import com.things.cgomp.common.mq.message.DeviceSessionSyncReqMsg;
import com.things.cgomp.common.mq.message.SyncDeviceInfo;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class ConnectIngState extends BaseConnectState {

   private int SYNC_SESSION_INTERVAL_TIME;

    public ConnectIngState(SessionContext sessionContext, DeviceLifecycleMessageService deviceLifecycleMessageService, int syncSessionIntervalTime) {
        super(sessionContext, deviceLifecycleMessageService);
        this.SYNC_SESSION_INTERVAL_TIME = syncSessionIntervalTime;
    }

    @Override
    boolean doBusinessExe(Session state) {
        if (state.isAuth()) {
            Long nowTime = System.currentTimeMillis();
            Long syncTime = state.getLastSyncSessionTime();
            if (null == syncTime || (nowTime - syncTime > SYNC_SESSION_INTERVAL_TIME * 1000 * 60)) {
                DeviceSessionSyncReqMsg syncSessionInfoReqMsg = getSyncSessionInfoReqMsg(state);
                deviceLifecycleMessageService.syncSessionInfo(syncSessionInfoReqMsg, state, new SimpleServiceCallback<Void>() {
                    @Override
                    public void onSuccess(Void msg) {
                        state.setLastSyncSessionTime(nowTime);
                    }

                    @Override
                    public void onError(Throwable e) {
                        log.error("ConnectIngState.error", e);
                    }
                });
            }

        }

        return true;
    }

    private DeviceSessionSyncReqMsg getSyncSessionInfoReqMsg(Session state) {
        List<SyncDeviceInfo> syncDeviceInfos = getSyncDeviceInfos(state);

        return DeviceSessionSyncReqMsg.builder()
                .deviceInfos(syncDeviceInfos)
                .build();
    }

    private List<SyncDeviceInfo> getSyncDeviceInfos(Session state) {
        Long validTime = sessionContext.getBroker().getValidTime(state);

        List<SyncDeviceInfo> syncDeviceInfos = new ArrayList<>();
        SyncDeviceInfo parentDevice = SyncDeviceInfo.builder()
                .lastActiveTime(state.getLastActiveTime())
                .deviceId(state.getDeviceId())
                .validTime(validTime)
                .build();
        syncDeviceInfos.add(parentDevice);

        Map<String, SessionDeviceInfo> childDevice = state.getChildDevices();
        if (null != childDevice) {
            childDevice.values().forEach(deviceInfo -> {
                    SyncDeviceInfo childDeviceSyncDeviceInfo = SyncDeviceInfo.builder()
                            .lastActiveTime(state.getLastActiveTime())
                            .deviceId(deviceInfo.getDeviceId())
                            .validTime(validTime)
                            .build();
                    syncDeviceInfos.add(childDeviceSyncDeviceInfo);

            });
        }

        return syncDeviceInfos;
    }



}
