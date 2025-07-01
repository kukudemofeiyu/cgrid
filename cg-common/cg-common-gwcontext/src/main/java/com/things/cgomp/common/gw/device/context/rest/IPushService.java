package com.things.cgomp.common.gw.device.context.rest;


import com.things.cgomp.common.device.pojo.device.push.PushInfo;
import com.things.cgomp.common.device.pojo.device.push.PushResult;

public interface IPushService {

    PushResult processPush(PushInfo pushInfo);

}
