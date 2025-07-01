package com.things.cgomp.app.domain.vo;

import com.things.cgomp.app.api.domain.AppUser;
import com.things.cgomp.common.security.bean.TokenResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AppLoginVO extends TokenResponse {
    private static final long serialVersionUID = 1L;


    private AppUser user;

    public AppLoginVO() {
    }

    public AppLoginVO(TokenResponse tokenResponse) {
        super.expires_in = tokenResponse.getExpires_in();
        super.access_token = tokenResponse.getAccess_token();
    }
}
