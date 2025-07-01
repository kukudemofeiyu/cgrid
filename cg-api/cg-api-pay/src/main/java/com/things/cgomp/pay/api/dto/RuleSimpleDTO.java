package com.things.cgomp.pay.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
public class RuleSimpleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 规则名称
     */
    private String name;

    private LocalDateTime updateTime;

}
