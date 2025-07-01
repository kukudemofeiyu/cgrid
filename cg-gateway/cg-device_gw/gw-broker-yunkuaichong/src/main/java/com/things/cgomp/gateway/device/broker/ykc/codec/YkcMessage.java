package com.things.cgomp.gateway.device.broker.ykc.codec;

public abstract class YkcMessage  {

    public abstract String getDeviceNo();

    public abstract String getGunNo();

    public abstract Integer getFrameSerialNo();

    public abstract Boolean getCryptoFlag();

    public abstract Integer getMessageType();

    public abstract Long getTs();

    public abstract String getBody() ;

    public abstract String getOriginHex() ;
}
