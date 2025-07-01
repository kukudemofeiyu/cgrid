package com.things.cgomp.common.device.pojo.device;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Data
@Builder
@Jacksonized
public class DeviceCommandVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private boolean succeed;

    private String requestId;

    private String errorMsg;

    private Object responseData;


}
