package com.things.cgomp.gateway.device.broker.ykc.codec.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YkcChargeConfigSettingIn extends YkcMessageIn{

    private String orderNo;
    private String deviceNo;

    private String gunNo;

    private Double maxAllowCur;

    private Double maxAllowEle;

    private Double standElecQ;

    private Double maxAllowTolCur;

    private Integer maxAllowTemp;

    private Double soc;

    private Integer currentCur;

    private Double maxOutCurrent;

    private Double minOutCurrent;

    private Double maxOutElec;

    private Double minOutElec;

}
