package com.things.cgomp.gateway.device.broker.ykc.codec.out;

import com.things.cgomp.gateway.device.broker.ykc.codec.YkcMessage;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class YkcMessageOut extends YkcMessage implements Serializable {

    protected String deviceNo;

    protected String gunNo;
    /**
     * 帧序列号
     */
    protected Integer frameSerialNo;
    /**
     * 加密类型
     */
    protected Boolean cryptoFlag;
    /**
     * 消息类型
     */
    protected Integer messageType;

    protected Long ts;

    /**
     * 包体、没有加密的十六进制字符
     */
    private String body;

    private String originHex;

    protected YkcMessageOut(Integer frameSerialNo, Integer messageType, Boolean cryptoFlag) {
        this.frameSerialNo = frameSerialNo;
        this.cryptoFlag = cryptoFlag;
        this.messageType = messageType;
    }

    protected YkcMessageOut() {}

}
