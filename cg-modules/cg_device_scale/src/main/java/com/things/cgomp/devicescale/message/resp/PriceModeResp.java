package com.things.cgomp.devicescale.message.resp;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */

import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.annotation.Type;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.CmdId;
import com.things.cgomp.devicescale.message.DataType;

@Type(CmdId.计费模型验证请求_响应)
public class PriceModeResp extends AbstractBody {

    private String sn;
    private String modeNo;
    private Integer code;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 2, desc = "计费模型编号")
    public String getModeNo() {
        return modeNo;
    }

    @Property(index = 9, type = DataType.BYTE, length = 1, desc = "验证结果")
    public Integer getCode() {
        return code;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setModeNo(String modeNo) {
        this.modeNo = modeNo;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
