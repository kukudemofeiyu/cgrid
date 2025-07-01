package com.things.cgomp.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 注册用户转账请求对象
 * @author things
 */
@Data
public class RegisterTransferDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * IC卡ID
     */
    @NotNull(message = "IC卡不能为空")
    private Long cardId;
    /**
     * 转账金额
     */
    @NotNull(message = "转账金额不能为空")
    private BigDecimal amount;
}
