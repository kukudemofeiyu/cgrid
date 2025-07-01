package com.things.cgomp.common.core.dto;

import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageDTO extends BaseEntity implements Serializable {

    @NotNull(message = "页码不能为空")
    private Integer current;

    @NotNull(message = "每页数量不能为空")
    private Integer pageSize;

    public PageDTO() {
        this.current = 1;
        this.pageSize = 10;
    }
}
