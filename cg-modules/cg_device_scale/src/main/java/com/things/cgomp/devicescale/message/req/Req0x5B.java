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
@Type(CmdId.二维码设置)
public class Req0x5B extends AbstractBody {

    private String sn;
    private String aliSn;
    private Integer qrType;
    private Integer qrLength;
    private String qrContext;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩编号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 1, desc = "枪编号")
    public String getAliSn() {
        return aliSn;
    }

    @Property(index = 8, type = DataType.BYTE, length = 1, desc = "二维码码制")
    public Integer getQrType() {
        return qrType;
    }

    @Property(index = 9, type = DataType.BYTE, length = 1, desc = "二维码长度")
    public Integer getQrLength() {
        return qrLength;
    }

    @Property(index = 10, type = DataType.BYTE_ASCII, lengthName = "qrLength", desc = "二维码内容")
    public String getQrContext() {
        return qrContext;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setAliSn(String aliSn) {
        this.aliSn = aliSn;
    }

    public void setQrType(Integer qrType) {
        this.qrType = qrType;
    }

    public void setQrLength(Integer qrLength) {
        this.qrLength = qrLength;
    }

    public void setQrContext(String qrContext) {
        this.qrContext = qrContext;
    }

    @Override
    public String toString() {
        return "Req0x5B{" +
                "sn='" + sn + '\'' +
                ", aliSn='" + aliSn + '\'' +
                ", qrType=" + qrType +
                ", qrLength=" + qrLength +
                ", qrContext='" + qrContext + '\'' +
                '}';
    }
}
