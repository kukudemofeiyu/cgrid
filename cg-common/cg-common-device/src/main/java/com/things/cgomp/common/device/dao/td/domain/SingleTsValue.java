package com.things.cgomp.common.device.dao.td.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author things
 */
@Data
public class SingleTsValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("_ts")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date ts;

    private Object value;
}
