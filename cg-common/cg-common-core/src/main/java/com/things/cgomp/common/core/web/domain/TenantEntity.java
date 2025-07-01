package com.things.cgomp.common.core.web.domain;

import lombok.Data;


/**
 * Entity基类
 *
 * @author things
 */
@Data
public class TenantEntity extends BaseEntity
{

    /** 租户ID */
    private Long tenantId;

}
