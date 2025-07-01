package com.things.cgomp.device.dto.statistics;

import com.things.cgomp.device.dto.statistics.total.trend.TotalDecimalTrend;
import com.things.cgomp.device.dto.statistics.total.trend.TotalLongTrend;
import com.things.cgomp.device.dto.statistics.total.trend.TotalTrendData;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author things
 */
@Data
public class StatisticsTotalDataResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 累计金额
     */
    private TotalDecimalTrend totalAmount;
    /**
     * 累计次数
     */
    private TotalLongTrend totalCount;
    /**
     * 累计电量
     */
    private TotalDecimalTrend totalElectricity;
    /**
     * 累计时长
     * 累计时长（xx小时xx分钟）
     */
    private TotalDecimalTrend totalTime;
    /**
     * 用户数量
     */
    private TotalLongTrend userCount;

    public void fillTodayValue(BigDecimal amount, Long count, BigDecimal electricity, BigDecimal time){
        if (totalAmount != null) {
            totalAmount.setValue(amount);
        }
        if (totalCount != null) {
            totalCount.setValue(count);
        }
        if (totalElectricity != null) {
            totalElectricity.setValue(electricity);
        }
        if (totalTime != null) {
            totalTime.setValue(time);
        }
    }

    public void fillTrends(List<TotalTrendData<BigDecimal>> amountTrends,
                           List<TotalTrendData<Long>> countTrends,
                           List<TotalTrendData<BigDecimal>> electricityTrends,
                           List<TotalTrendData<BigDecimal>> timeTrends) {
        if (totalAmount != null) {
            totalAmount.setTrends(amountTrends);
        }
        if (totalCount != null) {
            totalCount.setTrends(countTrends);
        }
        if (totalElectricity != null) {
            totalElectricity.setTrends(electricityTrends);
        }
        if (totalTime != null) {
            totalTime.setTrends(timeTrends);
        }
    }
}
