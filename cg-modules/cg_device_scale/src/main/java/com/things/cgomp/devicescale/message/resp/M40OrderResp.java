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
@Type(CmdId.交易记录_应答)
public class M40OrderResp extends AbstractBody {

    private String orderNo;
    private Integer code;

    @Property(index = 0, type = DataType.BCD, length = 16, desc = "交易流水号")
    public String getOrderNo() {
        return orderNo;
    }

    @Property(index = 16, type = DataType.BYTE, length = 1, desc = "状态")
    public Integer getCode() {
        return code;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "M40OrderResp{" +
                "orderNo='" + orderNo + '\'' +
                ", code=" + code +
                '}';
    }
}

