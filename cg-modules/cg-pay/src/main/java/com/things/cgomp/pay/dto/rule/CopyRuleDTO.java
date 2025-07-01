package com.things.cgomp.pay.dto.rule;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Getter
@Setter
@Accessors(chain = true)
public class CopyRuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long operatorId;

}
