package com.things.cgomp.system.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class AppUserTrendDateData implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期 yyyy-mm-dd
     */
    private String date;
    /**
     * 用户数量
     */
    private Long userCount;
}
