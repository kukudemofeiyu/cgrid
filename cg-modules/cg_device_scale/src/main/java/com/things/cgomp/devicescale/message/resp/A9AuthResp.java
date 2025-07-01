package com.things.cgomp.devicescale.message.resp;

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
@Type(CmdId.插枪_充电桩上报vin码_响应)
public class A9AuthResp extends AbstractBody {


    private String sn;
    private String aliSn;
    private Integer auth;
    private String orderNo;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 1, desc = "枪号")
    public String getAliSn() {
        return aliSn;
    }

    @Property(index = 8, type = DataType.BYTE, length = 1, desc = "应答")
    public Integer getAuth() {
        return auth;
    }

    @Property(index = 9, type = DataType.BCD, length = 16, desc = "交易流水号")
    public String getOrderNo() {
        return orderNo;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setAliSn(String aliSn) {
        this.aliSn = aliSn;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "A9AuthResp{" +
                "sn='" + sn + '\'' +
                ", aliSn='" + aliSn + '\'' +
                ", auth=" + auth +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
