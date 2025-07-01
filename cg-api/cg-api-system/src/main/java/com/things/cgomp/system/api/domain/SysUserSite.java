package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户和站点关联 system_user_site
 * 
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("system_user_site")
public class SysUserSite
{
    /** 用户ID */
    private Long userId;

    /** 站点ID */
    private Long siteId;
}
