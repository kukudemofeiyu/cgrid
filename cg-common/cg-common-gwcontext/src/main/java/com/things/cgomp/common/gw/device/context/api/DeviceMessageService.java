package com.things.cgomp.common.gw.device.context.api;

import com.things.cgomp.common.gw.device.context.session.Session;
import com.things.cgomp.common.mq.common.AbstractBody;
import com.things.cgomp.common.mq.message.DeviceCmdLogReqMsg;
import com.things.cgomp.common.mq.message.callback.SimpleServiceCallback;

public interface DeviceMessageService extends DeviceLifecycleMessageService{

    /**
     *
     * @param topic
     * @param tag
     * @param deviceDataReqMsg
     * @param session
     * @param childId 枪设备id
     * @param messageType 报文消息类型
     * @param eventTs 事件时间
     * @param callback
     */
    void sendData(String topic, String tag, AbstractBody deviceDataReqMsg, Session session, Long childId, Integer messageType,  Long eventTs, SimpleServiceCallback<Void> callback);

    void sendCmdLogData(DeviceCmdLogReqMsg deviceDataReqMsg, Session session, String  gunNo  , Integer messageType, Long ts);

    Boolean sendOrderRecordTransactionMsg(String topic, String tag, AbstractBody deviceDataReqMsg, Session session, Long childId, Integer messageType,  Long eventTs);

}
