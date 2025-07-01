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
@Type(CmdId.远程账户余额更新)
public class Req0x42 extends AbstractBody {

    private String sn;
    private String aliSn;
    private String cardNo;
    private Long account;

    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩编号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BCD, length = 1, desc = "枪编号")
    public String getAliSn() {
        return aliSn;
    }

    @Property(index = 8, type = DataType.BCD, length = 8, desc = "卡号")
    public String getCardNo() {
        return cardNo;
    }

    @Property(index = 16, type = DataType.UNSIGNED_INT_LE, length = 4, desc = "余额")
    public Long getAccount() {
        return account;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public void setAliSn(String aliSn) {
        this.aliSn = aliSn;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Req0x42{" +
                "sn='" + sn + '\'' +
                ", aliSn='" + aliSn + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", account=" + account +
                '}';
    }
}
