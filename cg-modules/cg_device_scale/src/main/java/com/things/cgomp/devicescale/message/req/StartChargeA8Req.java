package com.things.cgomp.devicescale.message.req;

import cn.hutool.core.codec.BCD;
import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.annotation.Type;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.CmdId;
import com.things.cgomp.devicescale.message.DataType;
import com.things.cgomp.devicescale.utils.ByteUtil;
import lombok.Getter;

import java.util.Base64;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Type(CmdId.运营平台远程控制启机)
public class StartChargeA8Req extends AbstractBody {
    private String orderNo;
    private String sn;
    private String aliSn;
    private String virCardNo;
    private String phyCardNo;
    private Double account;
    private Double kv;
    private Integer soc;
    private Long vl;

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

    @Property(index = 24, type = DataType.BCD, length = 8, desc = "逻辑卡号")
    public String getVirCardNo() {
        return virCardNo;
    }

    @Property(index = 32, type = DataType.BCD, length = 8, desc = "物理卡号")
    public String getPhyCardNo() {
        return phyCardNo;
    }

    @Property(index = 40, type = DataType.UNSIGNED_INT_LE, length = 4, desc = "账户余额")
    public Double getAccount() {
        return account;
    }

    @Property(index = 44, type = DataType.UNSIGNED_SHORT_LE, length = 2, desc = "本次充电当前允许的最大功率")
    public Double getKv() {
        return kv;
    }

    @Property(index = 46, type = DataType.BYTE, length = 1, desc = "SOC 限制")
    public Integer getSoc() {
        return soc;
    }

    @Property(index = 47, type = DataType.UNSIGNED_INT_LE, length = 4, desc = "充电电量限制")
    public Double getVl() {
        return vl * 0.0001;
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

    public void setVirCardNo(String virCardNo) {
        this.virCardNo = virCardNo;
    }

    public void setPhyCardNo(String phyCardNo) {
        this.phyCardNo = phyCardNo;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    public void setKv(Double kv) {
        this.kv = kv;
    }

    public void setSoc(Integer soc) {
        this.soc = soc;
    }

    public void setVl(Long vl) {
        this.vl = vl;
    }
}

