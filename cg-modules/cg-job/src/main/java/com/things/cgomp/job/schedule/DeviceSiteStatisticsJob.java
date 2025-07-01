package com.things.cgomp.job.schedule;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.job.domain.SiteStatisticsWatermark;
import com.things.cgomp.job.service.LockService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 站点数据统计任务
 *
 * @author thigns
 */
@Slf4j
@Component
public class DeviceSiteStatisticsJob {

    @Resource
    private LockService lockService;
    @Resource
    private RedisService redisService;

    private static final String hourLockKey = "site_statistics_h_lock";

    @XxlJob("statisticsSiteHour")
    public void statisticsSiteHour() {
        log.debug("@@@@@@@@@@@@@@@@@@@@@开始执行 statisticsSiteHour");
        RLock lock = lockService.getLock(hourLockKey);
        if (lock == null) {
            return;
        }
        try {
            // 获取水位线
            String watermarkKey = buildWaterMarkKey("hour");
            Object obj = redisService.getCacheObject(watermarkKey);
            SiteStatisticsWatermark watermark = obj == null ? new SiteStatisticsWatermark() : (SiteStatisticsWatermark) obj;
            // 获取最新统计时间
            DateTime latestStatisticsTime = getLatestStatisticsTime();
            // 根据水位线获取分段时间
            List<DateTime> segmentTimes = getSegmentTimes(latestStatisticsTime, DateTime.of(watermark.getLatestTime()));
            if (CollectionUtils.isEmpty(segmentTimes)) {
                log.warn("segmentTimes 时间段为空，水位线时间：{}", DateUtil.formatDateTime(watermark.getLatestTime()));
            } else {
                DateTime watermarkLatestTime = null;
                try {
                    // 按照时间点循环统计
                    for (int i = 0; i < segmentTimes.size(); i++) {
                        DateTime beginTime = segmentTimes.get(i);
                        DateTime endTime = segmentTimes.size() <= i + 1 ? latestStatisticsTime : segmentTimes.get(i + 1);
                        log.info("开始统计站点分时数据，beginTime={}, endTime={}", beginTime, endTime);
                        // 查询订单数据

                        // 标记最新水位线时间为结束时间
                        watermarkLatestTime = endTime;
                    }
                } catch (Exception e) {
                    // 某个时间点统计出错时，水位线时间为出错前的结束时间，此处无需处理，等待下次定时任务再进行计算
                    log.warn("statisticsSiteHour 分时统计数据出现异常", e);
                }
                if (watermarkLatestTime != null) {
                    // 修改水位线
                    watermark.setLatestTime(watermarkLatestTime);
                    redisService.setCacheObject(watermarkKey, watermark);
                }
            }
        } catch (Exception e) {
            log.error("statisticsSiteHour error", e);
        } finally {
            // 释放锁
            lock.unlock();
        }
        log.info("@@@@@@@@@@@@@@@@@@@@@执行完成 statisticsSiteHour");
    }

    @XxlJob("statisticsSiteDay")
    public void statisticsSiteDay() {

    }

    private DateTime getLatestStatisticsTime() {
        // 获取当前整点时间
        return DateUtil.beginOfHour(new Date());
    }

    private List<DateTime> getSegmentTimes(DateTime latestStatisticsTime, DateTime watermarkTime) {
        List<DateTime> segmentTimes = new ArrayList<>();
        DateTime statisticsTime;
        if (watermarkTime == null) {
            // 水位线时间为空，设置为最新统计时间
            statisticsTime = DateTime.of(latestStatisticsTime.getTime());
        } else {
            // 如果水位线不为空，从水位线后移1个小时
            statisticsTime = watermarkTime.offsetNew(DateField.HOUR, 1);
        }
        // 计算水位线
        while (!statisticsTime.isAfter(latestStatisticsTime)) {
            segmentTimes.add(statisticsTime.offsetNew(DateField.HOUR, -1));
            // 每次循环将statisticsTime后移1个小时
            statisticsTime = statisticsTime.offset(DateField.HOUR, 1);
        }
        return segmentTimes;
    }

    public static String buildWaterMarkKey(String flag) {
        return "site_statistics_watermark:" + flag;
    }
}
