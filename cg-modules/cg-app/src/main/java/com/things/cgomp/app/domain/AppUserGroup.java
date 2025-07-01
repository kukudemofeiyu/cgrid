package com.things.cgomp.app.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName app_user_group
 */
@TableName(value ="app_user_group")
@Data
public class AppUserGroup implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Long createUser;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Long updateUser;

    /**
     * 
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}