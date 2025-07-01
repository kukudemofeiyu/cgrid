package com.things.cgomp.common.device.dao.td.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("device_cmd_log")
public class DeviceCmdLogData extends BasePersistData {

    /**
     * 时间戳
     */
    @TableField(exist = false)
    @JsonIgnore
    private Long updateTs;

    private Long portId;

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

    private String originHex;

    private String remark;

    private String secret;
}
