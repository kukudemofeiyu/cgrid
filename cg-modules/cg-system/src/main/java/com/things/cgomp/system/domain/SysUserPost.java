package com.things.cgomp.system.domain;

import lombok.Data;

/**
 * 用户和岗位关联 system_user_post
 * 
 * @author things
 */
@Data
public class SysUserPost
{
    /** 用户ID */
    private Long userId;

    /** 岗位ID */
    private Long postId;

    /** 租户ID */
    private Long tenantId;
}
