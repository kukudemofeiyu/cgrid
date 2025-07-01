package com.things.cgomp.common.device.dao.td.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author things
 */
@Data
public class BasePersistData implements Serializable {

    /**
     * 格式化后的时间戳
     */
    @TableField("_ts")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;
    /**
     * 时间戳
     */
    @JsonIgnore
    @TableField(exist = false)
    @JSONField(serialize = false)
    private Long ts;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 流水号
     */
    private String orderSn;
}
