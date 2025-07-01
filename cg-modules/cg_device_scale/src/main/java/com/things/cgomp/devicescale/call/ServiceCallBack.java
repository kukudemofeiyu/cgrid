package com.things.cgomp.devicescale.call;

import com.things.cgomp.devicescale.session.State;
import javafx.stage.Stage;

public interface ServiceCallBack {
    void call(Integer type, String sn, State state);
}
