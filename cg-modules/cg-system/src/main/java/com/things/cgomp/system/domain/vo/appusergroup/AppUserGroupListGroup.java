package com.things.cgomp.system.domain.vo.appusergroup;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Getter
@Setter
@Accessors(chain = true)
public class AppUserGroupListGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 运营商ID
     */
    private Long operatorId;

    /**
     * 用户组名称
     */
    private String name;

    /**
     * 用户列表
     */
    private List<AppUserGroupUserVo> users;
}
