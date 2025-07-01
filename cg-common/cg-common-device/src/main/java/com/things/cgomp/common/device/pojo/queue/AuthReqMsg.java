package com.things.cgomp.common.device.pojo.queue;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;


@Builder
@Data
public class AuthReqMsg implements Serializable {
   private Map<String,Object> authParam;

}
