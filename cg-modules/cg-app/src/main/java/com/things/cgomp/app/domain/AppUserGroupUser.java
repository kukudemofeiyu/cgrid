package com.things.cgomp.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName app_user_group_user
 */
@TableName(value ="app_user_group_user")
@Data
public class AppUserGroupUser implements Serializable {
    /**
     * 
     */
    private Long groupId;

    /**
     * 
     */
    private Long userId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}