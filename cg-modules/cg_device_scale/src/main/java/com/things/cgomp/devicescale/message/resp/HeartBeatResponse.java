package com.things.cgomp.devicescale.message.resp;

import cn.hutool.core.codec.BCD;
import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.annotation.Type;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.CmdId;
import com.things.cgomp.devicescale.message.DataType;
import lombok.Getter;

import java.util.Base64;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Type(CmdId.心跳响应)
public class HeartBeatResponse extends AbstractBody {


    private String deviceId;
    private String aliasSn;
    private Integer code;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩号")
    public String getDeviceId() {
        return deviceId;
    }

    @Property(index = 7, type = DataType.BCD, length = 1, desc = "抢号")
    public String getAliasSn() {
        return aliasSn;
    }

    @Property(index = 8, type = DataType.BYTE, length = 1, desc = "心跳应答")
    public Integer getCode() {
        return code;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public void setAliasSn(String aliasSn) {
        this.aliasSn = aliasSn;
    }


    public void setCode(Integer code) {
        this.code = code;
    }


    @Override
    public String toString() {
        return "HeartBeatResponse{" +
                "deviceId='" + deviceId + '\'' +
                ", aliasSn='" + aliasSn + '\'' +
                ", code=" + code +
                '}';
    }
}

