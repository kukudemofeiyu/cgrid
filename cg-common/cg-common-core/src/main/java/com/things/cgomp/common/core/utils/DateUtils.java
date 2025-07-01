package com.things.cgomp.common.core.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author things
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    private static final LocalTime LAST_TIME = LocalTime.of(23, 59, 59);

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08/1719540424139
     */
    public static final String dateTimePath()
    {
        Date now = new Date();
        String format = DateFormatUtils.format(now, "yyyy/MM/dd");
        return format + "/" + now.getTime();
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算时间差
     *
     * @param endDate 最后时间
     * @param startTime 开始时间
     * @return 时间差（天/小时/分钟）
     */
    public static String timeDistance(Date endDate, Date startTime)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startTime.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 增加 LocalDateTime ==> Date
     */
    public static Date toDate(LocalDateTime temporalAccessor)
    {
        ZonedDateTime zdt = temporalAccessor.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    public static Date toDate(Long timestamp)
    {
        return new Date(timestamp);
    }

    /**
     * 增加 LocalDate ==> Date
     */
    public static Date toDate(LocalDate temporalAccessor)
    {
        LocalDateTime localDateTime = LocalDateTime.of(temporalAccessor, LocalTime.of(0, 0, 0));
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zdt.toInstant());
    }

    public static LocalDateTime toLocalDateTime(Long mills) {
        if(mills == null){
            return null;
        }
        return LocalDateTime.ofInstant(
                Instant.ofEpochMilli(mills),
                ZoneId.systemDefault()
        );
    }

    public static LocalDateTime toLocalDateTime(
            Date date
    ) {
        if (date == null) {
            return null;
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

    }

    public static long toTimestamp(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public static BigDecimal calIntervalTime(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        if(startTime == null || endTime == null){
            return null;
        }

        long startTimeTs = toTimestamp(startTime);
        long endTimeTs = toTimestamp(endTime);

        double intervalTime = (endTimeTs - startTimeTs) / 1000.0 / 60 / 60;
        return BigDecimal.valueOf(intervalTime)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isFullOrHalfHour(LocalTime time) {
        if(time == null){
            return false;
        }

        int minute = time.getMinute();
        return minute == 0 || minute == 30;
    }

    public static boolean isFullOrHalfHour(LocalDateTime time) {
        if(time == null){
            return false;
        }

        int minute = time.getMinute();
        return minute == 0 || minute == 30;
    }

    public static LocalDateTime getPreviousHalfOrFullHour(LocalDateTime time) {
        if(isFullOrHalfHour(time)){
            return time;
        }

        int minute = time.getMinute();
        int adjustedMinute = (minute / 30) * 30; // 取最近的30分钟倍数
        return time.withMinute(adjustedMinute)
                .withSecond(0)
                .withNano(0);
    }

    public static LocalDateTime endOfDay(
            LocalDate date
    ) {
        return date.atTime(LAST_TIME);
    }

    public static LocalDateTime min(
            LocalDateTime a,
            LocalDateTime b
    ) {
        if(a == null || b == null){
            return null;
        }

        return a.isBefore(b) ? a : b;
    }

    public static LocalDateTime max(
            LocalDateTime a,
            LocalDateTime b
    ) {
        if(a == null || b == null){
            return null;
        }

        return a.isAfter(b) ? a : b;
    }
}
