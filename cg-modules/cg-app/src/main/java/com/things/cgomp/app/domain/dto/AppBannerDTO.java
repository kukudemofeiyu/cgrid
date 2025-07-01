package com.things.cgomp.app.domain.dto;

import lombok.Data;

@Data
public class AppBannerDTO {
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

}
