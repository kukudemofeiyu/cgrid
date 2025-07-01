package com.things.cgomp.common.gw.device.context.session.state.downline;


import com.things.cgomp.common.gw.device.context.api.DeviceMessageService;
import com.things.cgomp.common.gw.device.context.session.SessionCloseCause;
import com.things.cgomp.common.gw.device.context.session.SessionContext;

public class OffLineState extends BaseOffLineState {
   public OffLineState(SessionContext sessionContext, SessionCloseCause closeCause, DeviceMessageService deviceMessageService) {
        super(sessionContext, closeCause, deviceMessageService);
    }


    @Override
    public String toString() {
        return "OffLineState [closeCause=" + closeCause.getDesc() + "]";
    }
}
