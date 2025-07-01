package com.things.cgomp.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class AppUserGroupVo implements Serializable {

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

    private Long createBy;

    private String createByName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Long updateBy;

    private String updateByName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
