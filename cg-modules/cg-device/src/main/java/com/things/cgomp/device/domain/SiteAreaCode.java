package com.things.cgomp.device.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 地区编码表
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("device_site_area_code")
public class SiteAreaCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 地区编码
     */
    private String areaCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 级别
     */
    private String level;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;

    /**
     * 父节点，当前表id
     */
    private Long parentId;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后修改人
     */
    private Long lastModifyBy;

    /**
     * 最后修改时间
     */
    private LocalDateTime lastModifyTime;

    /**
     * 备注
     */
    private String description;

    /**
     * 0-隐藏 1-显示
     */
    private Integer showFlag;
}
