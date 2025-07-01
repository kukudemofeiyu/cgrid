package com.things.cgomp.system.api.dto;

import com.things.cgomp.system.api.domain.AppUserTrendDateData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AppUserTrendDataDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总用户数量
     */
    private Long totalCount;
    /**
     * 日期趋势
     */
    private List<AppUserTrendDateData> dateTrends;
}
