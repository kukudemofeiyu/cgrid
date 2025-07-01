package com.things.cgomp.app.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserCarInfoVO {
    /**
     * 车辆id
     */
    private Long id;
    /**
     * 车牌号码
     */
    private String licensePlateNumber;
    /**
     * 是否默认（0非默认 1默认）
     */
    private Integer isDefault;
}
