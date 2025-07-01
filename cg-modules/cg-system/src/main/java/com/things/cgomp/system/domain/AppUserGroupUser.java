package com.things.cgomp.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * app用户组映射表
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("app_user_group_user")
@NoArgsConstructor
@AllArgsConstructor
public class AppUserGroupUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long groupId;

    private Long userId;
}
