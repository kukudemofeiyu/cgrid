package com.things.cgomp.system.api.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户和运营商关联 system_user_operator
 * 
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("system_user_operator")
public class SysUserOperator
{
    /** 用户ID */
    private Long userId;

    /** 运营商ID */
    private Long operatorId;
}
