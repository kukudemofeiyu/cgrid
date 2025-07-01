package com.things.cgomp.system.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author things
 * @date 2025/2/27
 */
@Data
@Builder
public class RoleMenuVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 所有菜单
     */
    private List<TreeSelect> menus;
    /**
     * 已选中的菜单
     */
    private List<Long> checkedKeys;
}
