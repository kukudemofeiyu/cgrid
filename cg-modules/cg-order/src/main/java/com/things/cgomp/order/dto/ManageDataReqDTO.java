package com.things.cgomp.order.dto;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author things
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class ManageDataReqDTO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 运营商ID
     */
    private Long operatorId;
    /**
     * 站点ID
     */
    private Long siteId;
    /**
     * 充电桩编号
     */
    private String pileSn;
    /**
     * 运行状态
     * 1在线 0离线
     */
    private Integer runStatus;
    /**
     * 开始日期
     * yyyy-MM-dd
     */
    private String beginTime;
    /**
     * 结束日期
     * yyyy-MM-dd
     */
    private String endTime;

    public void setBeginTime(String beginTime) {
        if (StringUtils.isEmpty(beginTime)) {
            return;
        }
        DateTime dateTime = DateUtil.beginOfDay(DateUtil.parseDate(beginTime));
        this.beginTime = dateTime.toString();
    }

    public void setEndTime(String endTime) {
        if (StringUtils.isEmpty(endTime)) {
            return;
        }
        DateTime dateTime = DateUtil.endOfDay(DateUtil.parseDate(endTime));
        this.endTime = dateTime.toString();
    }
}
