package com.things.cgomp.system.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SysOperatorAccountUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 运营商ID
     */
    @NotNull(message = "运营商ID不能为空")
    private Long operatorId;
    /**
     * 变化金额
     */
    @NotNull(message = "金额不能为空")
    private BigDecimal amount;
    /**
     * 运营商用户ID
     */
    private Long userId;
    /**
     * 流水号
     */
    private String serialNum;
    /**
     * 业务类型
     * {@link com.things.cgomp.common.record.enums.AmountRecordType}
     */
    private Integer businessType;
    /**
     * 备注
     */
    private String remark;
}
