package com.things.cgomp.common.security.service;

import com.things.cgomp.common.core.context.ISecurityContext;
import com.things.cgomp.common.security.utils.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
public class SecurityContextService implements ISecurityContext {

    @Override
    public Long getUserId() {
        return SecurityUtils.getUserId();
    }

    @Override
    public Long getTenantId() {
        return SecurityUtils.getTenantId();
    }

}
