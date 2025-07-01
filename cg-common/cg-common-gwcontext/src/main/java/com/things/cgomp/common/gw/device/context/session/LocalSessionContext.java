package com.things.cgomp.common.gw.device.context.session;


import com.things.cgomp.common.gw.device.context.api.IDeviceServiceApi;
import com.things.cgomp.common.gw.device.context.broker.IBroker;

public interface LocalSessionContext<Key, T extends Session, channel> extends SessionContext<Key, T, channel> {


}
