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
 * @TableName app_banners
 */
@TableName(value ="app_banners")
@Data
public class AppBanners implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * Banner 标题
     */
    private String title;

    /**
     * Banner 图片 URL
     */
    private String imageUrl;

    /**
     * Banner 链接 URL
     */
    private String linkUrl;

    /**
     * 是否激活（1=激活，0=不激活）
     */
    private Integer isActive;

    /**
     * 排序
     */
    private Integer sortOrder;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}