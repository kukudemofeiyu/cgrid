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
@Type(CmdId.运营平台远程停机)
public class StopChargeA36Req extends AbstractBody {

    private String sn;
    private String aliSn;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩编号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 1, desc = "枪编号")
    public String getAliSn() {
        return aliSn;
    }


    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setAliSn(String aliSn) {
        this.aliSn = aliSn;
    }
}

