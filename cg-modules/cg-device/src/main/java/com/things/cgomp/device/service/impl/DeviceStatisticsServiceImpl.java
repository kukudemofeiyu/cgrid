package com.things.cgomp.device.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.device.convert.StatisticsConvert;
import com.things.cgomp.device.dto.statistics.ChargeGridStatisticsTotalDataResp;
import com.things.cgomp.device.service.IDeviceStatisticsService;
import com.things.cgomp.order.api.RemoteOrderStatisticsService;
import com.things.cgomp.order.api.domain.OrderStatisticsData;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author things
 */
@Slf4j
@Service
public class DeviceStatisticsServiceImpl implements IDeviceStatisticsService {

    @Resource
    private RemoteOrderStatisticsService remoteOrderStatisticsService;

    @Override
    public ChargeGridStatisticsTotalDataResp selectGridManageData(OrderStatisticsReq req) {
        // 获取指定日期订单统计数据
        OrderStatisticsData reqData = selectRemoteOrder(req);
        // 获取累计订单统计数据
        String beginTime = req.getBeginTime();
        String endTime = req.getEndTime();
        req.clearTime();
        OrderStatisticsData totalData = selectRemoteOrder(req);
        // 数据转换
        ChargeGridStatisticsTotalDataResp resp = StatisticsConvert.INSTANCE.convertDeviceTotal(reqData, totalData);
        // 计算环比
        calcQoQ(req, resp, beginTime, endTime);
        return resp;
    }

    /**
     * 计算环比
     *
     * @param req       请求体
     * @param resp      响应体
     * @param beginTime 查询开始时间
     * @param endTime   查询结束时间
     */
    private void calcQoQ(OrderStatisticsReq req, ChargeGridStatisticsTotalDataResp resp, String beginTime, String endTime) {
        if (StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)) {
            return;
        }
        Pair<String, String> qoqTimePair = convertQoQTime(beginTime, endTime);
        if (qoqTimePair == null) {
            return;
        }
        req.setBeginTime(qoqTimePair.getLeft());
        req.setEndTime(qoqTimePair.getRight());
        // 查询环比数据
        OrderStatisticsData qoqData = selectRemoteOrder(req);
        resp.calcQoQ(qoqData);
    }

    private OrderStatisticsData selectRemoteOrder(OrderStatisticsReq req) {
        R<OrderStatisticsData> reqDataR = remoteOrderStatisticsService.getOrderStatisticsTotalData(req, SecurityConstants.INNER);
        if (!R.isSuccess(reqDataR) || reqDataR.getData() == null) {
            log.error("selectTotalData 获取订单统计数据失败, msg={}", reqDataR.getMsg());
            return null;
        }
        return reqDataR.getData();
    }

    private Pair<String, String> convertQoQTime(String beginTime, String endTime) {
        DateTime start = DateUtil.parseDate(beginTime);
        DateTime end = DateUtil.parseDate(endTime);
        DateTime sQoQTime;
        DateTime eQoQTime;
        if (DateUtil.isSameDay(start, end)) {
            // 开始结束时间为同一天，环比时间为昨天
            sQoQTime = DateUtil.offsetDay(start, -1);
            eQoQTime = DateUtil.offsetDay(end, -1);
        } else if (DateUtil.isSameMonth(start, end)) {
            // 开始结束时间为同一个月，环比时间为上个月
            sQoQTime = DateUtil.offsetMonth(start, -1);
            eQoQTime = DateUtil.offsetMonth(end, -1);
        } else if (DateUtil.year(start) == DateUtil.year(end)) {
            // 开始结束时间为同一年，环比时间为上一年
            sQoQTime = start.offset(DateField.YEAR, -1);
            eQoQTime = end.offset(DateField.YEAR, -1);
        } else {
            // 跨年的时间不计算环比
            return null;
        }
        DateTime beginQoQTime = DateUtil.beginOfDay(sQoQTime);
        DateTime endQoQTime = DateUtil.endOfDay(eQoQTime);
        return Pair.of(DateUtil.formatDateTime(beginQoQTime), DateUtil.formatDateTime(endQoQTime));
    }
}
