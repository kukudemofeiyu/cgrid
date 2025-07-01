package com.things.cgomp.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册用户简约信息
 * @author things
 */
@Data
public class AppUserSimpleVO implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 手机号码
     */
    private String mobile;
}
