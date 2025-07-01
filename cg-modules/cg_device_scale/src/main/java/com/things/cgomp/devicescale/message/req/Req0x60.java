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
@Type(CmdId.默认最大功率下发)
public class Req0x60 extends AbstractBody {
    private String sn;
    private String aliSn;
    private Integer kw;
    private String startTime;
    private String endTime;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩编号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 1, desc = "枪编号")
    public String getAliSn() {
        return aliSn;
    }

    @Property(index = 8, type = DataType.UNSIGNED_SHORT_LE, length = 2, desc = "默认最大功率")
    public Integer getKw() {
        return kw;
    }

    @Property(index = 10, type = DataType.CP56Time2a, length = 7, desc = "开始时间")
    public String getStartTime() {
        return startTime;
    }

    @Property(index = 17, type = DataType.CP56Time2a, length = 7, desc = "结束时间")
    public String getEndTime() {
        return endTime;
    }


    public void setSn(String sn) {
        this.sn = sn;
    }


    public void setAliSn(String aliSn) {
        this.aliSn = aliSn;
    }


    public void setKw(Integer kw) {
        this.kw = kw;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Req0x60{" +
                "sn='" + sn + '\'' +
                ", aliSn='" + aliSn + '\'' +
                ", kw=" + kw +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
