package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 组织和站点关联 system_user_site
 * 
 * @author things
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("system_org_site")
public class SysOrgSite
{
    /** 组织ID */
    private Long orgId;

    /** 站点ID */
    private Long siteId;
}
