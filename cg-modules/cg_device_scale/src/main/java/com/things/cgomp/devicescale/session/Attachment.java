package com.things.cgomp.devicescale.session;

import lombok.Builder;
import lombok.Getter;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Builder
@Getter
public class Attachment {
    private String aesKey;
    private String sn;

}
