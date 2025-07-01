package com.things.cgomp.app.domain.dto;

import lombok.Data;

@Data
public class UpdateUserDTO {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 手机号
     */
    private String mobile;

}
