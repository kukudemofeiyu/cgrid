package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import com.things.cgomp.gateway.device.broker.ykc.codec.YkcMessage;
import io.netty.buffer.ByteBuf;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;


@Data
@Slf4j
public class YkcMessageIn extends YkcMessage implements Serializable {

    protected String deviceNo;

    protected String gunNo;
    /**
     * 帧序列号
     */
    private Integer frameSerialNo;
    /**
     * 加密类型
     */
    private Boolean cryptoFlag;
    /**
     * 消息类型
     */
    private Integer messageType;
    /**
     * 消息体
     */
    private ByteBuf messageBody;

    private Long ts;

    private String originHex;

    /**
     * 包体、没有加密的十六进制字符
     */
    private String body;

    public YkcMessageIn(Integer frameSerialNo, Boolean cryptoFlag, Integer messageType, ByteBuf messageBody, Long ts, String originHex, String body) {
        this.frameSerialNo = frameSerialNo;
        this.cryptoFlag = cryptoFlag;
        this.messageType = messageType;
        this.messageBody = messageBody;
        this.ts = ts;
        this.originHex = originHex;
        this.body = body;
    }

    public YkcMessageIn(){

    }
}
