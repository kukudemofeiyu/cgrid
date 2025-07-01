package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备交互日志请求信息
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceCmdLogReqMsg extends AbstractBody {


    /**
     * 命令字
     */
    private String cmd;

    /**
     * 序列号
     */
    private Integer serialNo;

    /**
     * 上下行 1-上行 2-下行
     */
    private Integer upDown;

    /**
     * 解密后的包体
     */
    private String body;

    private String cmdDesc;

    private Long updateTs;

    private String originHex;

    private String remark;

    private String secret;






}
