package com.things.cgomp.device.dto.statistics;

import com.things.cgomp.device.dto.statistics.total.TotalDecimalData;
import com.things.cgomp.device.dto.statistics.total.TotalLongData;
import com.things.cgomp.order.api.domain.OrderStatisticsData;
import lombok.Data;

import java.io.Serializable;

/**
 * @author things
 */
@Data
public class ChargeGridStatisticsTotalDataResp implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 总收益
     */
    private TotalDecimalData totalIncome;
    /**
     * 实际收益
     */
    private TotalDecimalData realIncome;
    /**
     * 订单退款
     */
    private TotalDecimalData refundAmount;
    /**
     * 电费
     */
    private TotalDecimalData chargeFee;
    /**
     * 服务费
     */
    private TotalDecimalData serviceFee;
    /**
     * 订单数量
     */
    private TotalLongData orderQuantity;
    /**
     * 充电次数
     */
    private TotalLongData chargeCount;
    /**
     * 充电时长
     */
    private TotalDecimalData chargeTime;
    /**
     * 总耗电量
     */
    private TotalDecimalData totalElectricity;

    /**
     * 计算环比
     */
    public void calcQoQ(OrderStatisticsData lastYearData){
        if (lastYearData == null) {
            return;
        }
        totalIncome.calcFluctuate(lastYearData.getTotalIncome());
        realIncome.calcFluctuate(lastYearData.getRealIncome());
        refundAmount.calcFluctuate(lastYearData.getRefundAmount());
        chargeFee.calcFluctuate(lastYearData.getChargeFee());
        serviceFee.calcFluctuate(lastYearData.getServiceFee());
        orderQuantity.calcFluctuate(lastYearData.getChargeCount());
        chargeCount.calcFluctuate(lastYearData.getChargeCount());
        chargeTime.calcFluctuate(lastYearData.getChargeTime());
        totalElectricity.calcFluctuate(lastYearData.getConsumeElectricity());
    }
}
