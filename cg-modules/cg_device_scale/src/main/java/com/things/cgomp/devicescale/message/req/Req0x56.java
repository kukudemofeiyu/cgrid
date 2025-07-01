package com.things.cgomp.devicescale.message.req;

import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.annotation.Type;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.CmdId;
import com.things.cgomp.devicescale.message.DataType;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Type(CmdId.对时设置)
public class Req0x56 extends AbstractBody {
    private String sn;
    private String serverTime;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩编号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.CP56Time2a, length = 7, desc = "服务器时间")
    public String getServerTime() {
        return serverTime;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }


    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }
}
