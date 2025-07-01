package com.things.cgomp.devicescale.message.req;

import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.DataType;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class Req0x5F extends AbstractBody {
    private String sn;
    private String aliSn;
    private Integer cgStatus;
    private Integer authTime;
    private Integer downTime;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩编号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 1, desc = "枪编号")
    public String getAliSn() {
        return aliSn;
    }

    @Property(index = 8, type = DataType.BYTE, length = 1, desc = "即插即充开关")
    public Integer getCgStatus() {
        return cgStatus;
    }

    @Property(index = 9, type = DataType.BCD, length = 1, desc = "鉴权超时时间")
    public Integer getAuthTime() {
        return authTime;
    }

    @Property(index = 10, type = DataType.BCD, length = 1, desc = "离线充电时间")
    public Integer getDownTime() {
        return downTime;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setAliSn(String aliSn) {
        this.aliSn = aliSn;
    }

    public void setCgStatus(Integer cgStatus) {
        this.cgStatus = cgStatus;
    }

    public void setAuthTime(Integer authTime) {
        this.authTime = authTime;
    }

    public void setDownTime(Integer downTime) {
        this.downTime = downTime;
    }


    @Override
    public String toString() {
        return "Req0x5F{" +
                "sn='" + sn + '\'' +
                ", aliSn='" + aliSn + '\'' +
                ", cgStatus=" + cgStatus +
                ", authTime=" + authTime +
                ", downTime=" + downTime +
                '}';
    }
}
