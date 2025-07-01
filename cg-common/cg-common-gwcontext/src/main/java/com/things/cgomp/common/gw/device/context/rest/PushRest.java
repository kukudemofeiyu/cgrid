package com.things.cgomp.common.gw.device.context.rest;


import com.things.cgomp.common.device.pojo.device.push.PushInfo;
import com.things.cgomp.common.device.pojo.device.push.PushResult;
import com.things.cgomp.common.device.pojo.device.push.ResponseData;
import com.things.cgomp.common.gw.device.context.broker.HubBrokerManager;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/push")
public class PushRest {

    @Autowired
    private HubBrokerManager hubServiceManager;

    @RequestMapping(method = {RequestMethod.POST}, value = "/command/device")
    public ResponseData<PushResult> push(@RequestBody PushInfo pushInfo) {
        IPushService pushService = hubServiceManager.
                getPushService(pushInfo.getBrokerId());
        if (null == pushService) {
            return ResponseData.fail(ErrorCodeConstants.PUSH_SERVICE_NO_BROKER.getCode().intValue(),
                    ErrorCodeConstants.PUSH_SERVICE_NO_BROKER.getMsg());
        }
        PushResult pushResult = pushService.processPush(pushInfo);
        return ResponseData.ok(pushResult);
    }





/*
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/session/device/destroy", name = "销毁设备会话")
    public ResponseData<PushResult> destroyDeviceSession(@RequestBody PushInfo pushInfo) {

        HubBrokerLifecycle broker = hubServiceManager.getBroker(pushInfo.getBrokerId());
        boolean result = broker.destroySession(pushInfo.getEventTime(), pushInfo.getConnectId());
        if (result) {
            return ResponseData.ok(PushResult.builder().succeed(true).build());
        } else {
            return ResponseData.ok(PushResult.builder().succeed(false).build());
        }

    }*/


}
