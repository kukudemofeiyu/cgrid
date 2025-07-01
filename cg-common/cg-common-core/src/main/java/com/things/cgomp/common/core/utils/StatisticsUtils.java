package com.things.cgomp.common.core.utils;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.core.web.domain.TrendQueryDTO;

import java.math.BigDecimal;

/**
 * @author things
 */
public class StatisticsUtils {

    public static String buildChargeTime(BigDecimal chargeHour) {
        long hour = chargeHour.longValue();
        BigDecimal remainder = chargeHour.remainder(BigDecimal.ONE);
        BigDecimal secondsD = remainder.multiply(new BigDecimal(3600));
        BigDecimal[] minutesAndSeconds = secondsD.divideAndRemainder(new BigDecimal(60));
        long minutes = minutesAndSeconds[0].longValue();
        long seconds = minutesAndSeconds[1].longValue();
        StringBuilder sb = new StringBuilder();
        if (hour != 0) {
            sb.append(hour).append("小时");
        }
        if (minutes != 0) {
            sb.append(minutes).append("分");
            if (seconds != 0) {
                sb.append(seconds).append("秒");
            }
        } else {
            if (seconds != 0) {
                sb.append(minutes).append("分").append(seconds).append("秒");
            }
        }
        if (StringUtils.isEmpty(sb.toString())) {
            return "0分";
        }
        return sb.toString();
    }

    public static TrendQueryDTO buildQueryParam(TrendQueryDTO queryDTO, String beginDate, String endDate) {
        if (StringUtils.isEmpty(beginDate) && StringUtils.isEmpty(endDate)) {
            // 默认为一周数据
            DateTime now = DateUtil.date();
            queryDTO.setEndDate(DateUtil.formatDate(now));
            queryDTO.setBeginDate(DateUtil.formatDate(now.offset(DateField.DAY_OF_MONTH, -6)));
        } else if (StringUtils.isEmpty(beginDate)) {
            DateTime endDateTime = DateUtil.parseDate(endDate);
            queryDTO.setEndDate(endDate);
            queryDTO.setBeginDate(DateUtil.formatDate(endDateTime.offset(DateField.DAY_OF_MONTH, -6)));
        } else if (StringUtils.isEmpty(endDate)) {
            DateTime beginDateTime = DateUtil.parseDate(beginDate);
            queryDTO.setEndDate(DateUtil.formatDate(beginDateTime.offset(DateField.DAY_OF_MONTH, 6)));
            queryDTO.setBeginDate(beginDate);
        } else {
            queryDTO.setBeginDate(beginDate);
            queryDTO.setEndDate(endDate);
        }
        return queryDTO;
    }

    public static TrendQueryDTO buildQueryParam(String beginDate, String endDate) {
        TrendQueryDTO queryDTO = new TrendQueryDTO();
        buildQueryParam(queryDTO, beginDate, endDate);
        return queryDTO;
    }
}
