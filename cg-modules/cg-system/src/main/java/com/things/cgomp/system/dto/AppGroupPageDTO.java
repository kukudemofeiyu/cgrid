package com.things.cgomp.system.dto;

import com.things.cgomp.common.core.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class AppGroupPageDTO extends PageDTO {

    /**
     * 用户组名称
     */
    private String name;

}
