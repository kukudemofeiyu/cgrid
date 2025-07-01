package com.things.cgomp.system.api.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author things
 */
@Data
@Accessors(chain = true)
public class SysSiteEf implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 清除站点关联的组织
     * 用于站点修改运营商时使用
     */
    private SysOrgSite removeOrgSite;

    /**
     * 组织站点关联
     */
    private List<SysOrgSite> orgSites;
    /**
     * 用户站点关联
     */
    private List<SysUserSite> userSites;
}
