package com.things.cgomp.order.api.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
public class OrderSnVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sn;

    private Long insertTime;

}
