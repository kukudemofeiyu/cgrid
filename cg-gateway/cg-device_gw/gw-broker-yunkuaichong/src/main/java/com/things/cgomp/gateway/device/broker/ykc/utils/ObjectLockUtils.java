package com.things.cgomp.gateway.device.broker.ykc.utils;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

public class ObjectLockUtils {

    private static Interner<String> deviceIdLock = Interners.newWeakInterner();

    public static Object getSendToDeviceLock(String deviceId){
        return deviceIdLock.intern(deviceId);
    }
}
