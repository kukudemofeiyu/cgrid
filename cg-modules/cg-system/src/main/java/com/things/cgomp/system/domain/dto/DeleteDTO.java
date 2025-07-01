package com.things.cgomp.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


@Data
public class DeleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "ids不能为空")
    private Long[] ids;
}
