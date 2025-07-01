package com.things.cgomp.devicescale.message.resp;

import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.annotation.Type;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.CmdId;
import com.things.cgomp.devicescale.message.DataType;

import java.util.List;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Type({CmdId.充电桩计费模型响应, CmdId.计费模型设置})
public class M0AResp extends AbstractBody {


    private String sn;
    private String modeNo;
    private Integer rateNum;
    private List<ModeRate> modeRateList;
    private Integer lossRate;
    private List<TimeRangRates> timeRangRates;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 2, desc = "计费模型编号")
    public String getModeNo() {
        return modeNo;
    }

    @Property(index = 9, type = DataType.BYTE, length = 2, desc = "费率数量")
    public Integer getRateNum() {
        //个数*每一组的字节数
        return rateNum * 8;
    }

    @Property(index = 11, type = DataType.LIST, lengthName = "rateNum", desc = "费率数量")
    public List<ModeRate> getModeRateList() {
        return modeRateList;
    }

    @Property(index = 12, type = DataType.BYTE, length = 1, desc = "计损比例")
    public Integer getLossRate() {
        return lossRate;
    }


    @Property(index = 13, type = DataType.LIST, length = 48, desc = "时间段费率价格")
    public List<TimeRangRates> getTimeRangRates() {
        return timeRangRates;
    }


    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setModeNo(String modeNo) {
        this.modeNo = modeNo;
    }

    public void setRateNum(Integer rateNum) {
        this.rateNum = rateNum;
    }

    public void setModeRateList(List<ModeRate> modeRateList) {
        this.modeRateList = modeRateList;
    }

    public void setLossRate(Integer lossRate) {
        this.lossRate = lossRate;
    }


    public void setTimeRangRates(List<TimeRangRates> timeRangRates) {
        this.timeRangRates = timeRangRates;
    }
}

