package com.things.cgomp.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import com.things.cgomp.common.core.xss.Xss;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 通知公告表 system_notice
 * 
 * @author things
 */
@Data
@TableName("system_notice")
public class SysNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 公告ID */
    private Long noticeId;

    /** 公告标题 */
    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    private String noticeTitle;

    /** 公告类型（1通知 2公告） */
    private String noticeType;

    /** 公告内容 */
    private String noticeContent;

    /** 公告状态（0正常 1关闭） */
    private String status;
}
