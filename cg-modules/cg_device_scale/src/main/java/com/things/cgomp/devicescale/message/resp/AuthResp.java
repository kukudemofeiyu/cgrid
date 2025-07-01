package com.things.cgomp.devicescale.message.resp;

import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.annotation.Type;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.CmdId;
import com.things.cgomp.devicescale.message.DataType;
import lombok.Getter;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Type(CmdId.充电桩登录认证响应)
public class AuthResp extends AbstractBody {


    private String sn;
    private Integer auth;
    private Integer length;
    private String rasSt;


    @Property(index = 0, type = DataType.BCD, length = 7, desc = "桩号")
    public String getSn() {
        return sn;
    }

    @Property(index = 7, type = DataType.BYTE, desc = "认证状态")
    public Integer getAuth() {
        return auth;
    }

    @Property(index = 8, type = DataType.BYTE, desc = "公钥长度")
    public Integer getLength() {
        return length;
    }

    @Property(index = 9, type = DataType.BYTE_ASCII_BASE64, lengthName = "length", desc = "公钥")
    public String getRasSt() {
        return rasSt;
    }


    public void setSn(String sn) {
        this.sn = sn;
    }


    public void setAuth(Integer auth) {
        this.auth = auth;
    }


    public void setRasSt(String rasSt) {
        this.rasSt = rasSt;
    }


    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "AuthResp{" +
                "sn='" + sn + '\'' +
                ", auth=" + auth +
                ", rasSt='" + rasSt + '\'' +
                '}';
    }
}
