package com.things.cgomp.common.device.dao.td.domain;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("port_status_data")
public class PortStatusData extends BasePersistData {

    /**
     * 枪状态
     * 0离线 1故障 2空闲 3充电
     */
    private Integer status;
    /**
     * 车VIN码
     */
    private String vin;
    /**
     * 状态变化时间
     */
    private Long statusTime;
    /**
     * 是否插枪
     */
    private Boolean portInserted;
    /**
     * 插枪时间
     * 第一次插枪时间
     */
    private Long portInsertedTime;
    /**
     * 归位状态
     * 0否 1是 2未知
     */
    private Integer homeStatus;
    /**
     * 备注
     */
    private String desc;
}
