package com.things.cgomp.app.api.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.things.cgomp.common.core.handler.JsonLongSetTypeHandler;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * 黑名单表
 * app_user_blacklist
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName(value ="app_user_blacklist")
public class AppUserBlacklist extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 站点维度（1全部站点 2部分站点）
     */
    private Integer siteRange;
    /**
     * 站点ID集合
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> siteIds;
    /**
     * 解禁时间
     */
    @NotNull(message = "解禁日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date unsealTime;
    /**
     * 用户昵称
     */
    @TableField(exist = false)
    private String nickName;
    /**
     * 用户手机号码
     */
    @TableField(exist = false)
    @NotBlank(message = "用户手机号码不能为空")
    private String mobile;
    /**
     * 修改人名称
     */
    @TableField(exist = false)
    private String updateUser;
}