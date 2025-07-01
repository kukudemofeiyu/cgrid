package com.things.cgomp.common.device.pojo.queue;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Builder
@Data
public class AuthResponseMsg implements Serializable{

    private Long deviceId;

    private Long productId;

    private String sn;

    /**
     * 设备配置信息
     */
    private String config;

    private String publicKey;
    private String privateKey;

    private String aliasSn;





}
