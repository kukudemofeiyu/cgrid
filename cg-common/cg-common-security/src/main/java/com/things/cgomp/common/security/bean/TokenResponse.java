package com.things.cgomp.common.security.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TokenResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String access_token;

    /**
     * 有效期
     */
    protected Long expires_in;

}
