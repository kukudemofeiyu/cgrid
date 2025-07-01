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
@Type(CmdId.交易记录召唤)
public class Req0x4D extends AbstractBody {

    private String orderNo;
    private String sn;
    private String aliSn;

    @Property(index = 0, type = DataType.BCD, length = 16, desc = "交易流水号")
    public String getOrderNo() {
        return orderNo;
    }

    @Property(index = 16, type = DataType.BCD, length = 7, desc = "桩编号")
    public String getSn() {
        return sn;
    }

    @Property(index = 23, type = DataType.BCD, length = 1, desc = "枪编号")
    public String getAliSn() {
        return aliSn;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setAliSn(String aliSn) {
        this.aliSn = aliSn;
    }

    @Override
    public String toString() {
        return "Req0x4D{" +
                "orderNo='" + orderNo + '\'' +
                ", sn='" + sn + '\'' +
                ", aliSn='" + aliSn + '\'' +
                '}';
    }
}
