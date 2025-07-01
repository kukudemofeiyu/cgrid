package com.things.cgomp.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 * @date 2025/3/4
 */
@Data
public class ShareholdersSimpleVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分成者ID
     */
    private Long id;
    /**
     * 分成者用户ID
     */
    private Long userId;
    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实名称
     */
    private String realName;
}
