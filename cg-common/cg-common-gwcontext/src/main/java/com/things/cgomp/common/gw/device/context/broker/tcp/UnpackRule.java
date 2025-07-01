package com.things.cgomp.common.gw.device.context.broker.tcp;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UnpackRule {

    private String character;

    private String decodeClass;
    private String encodeClass;

    private String fileName;
    private String fileContext;

    private String basedFrameDecoder;

}
