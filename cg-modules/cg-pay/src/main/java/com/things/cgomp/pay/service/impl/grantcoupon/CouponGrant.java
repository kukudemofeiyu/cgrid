package com.things.cgomp.pay.service.impl.grantcoupon;

import cn.hutool.core.lang.Snowflake;
import com.things.cgomp.common.core.constant.DateFormatter;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.api.domain.CouponTemplate;
import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.ActivityCouponDTO;
import com.things.cgomp.pay.enums.ErrorCodeConstants;
import com.things.cgomp.pay.service.ICouponService;
import com.things.cgomp.pay.service.ICouponTemplateService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;

public abstract class CouponGrant {

    private final static Snowflake snowflake = new Snowflake(
            1,
            1
    );

    private static final int COUPON_USER_ID_LENGTH = 7;

    @Resource
    protected ApplicationContext applicationContext;

    @NotNull
    protected ICouponService getCouponService() {
        return applicationContext.getBean(ICouponService.class);
    }

    protected String generateCouponSn(
            Long userId
    ) {
        String datePrefix = LocalDate.now()
                .format(DateFormatter.YYYYMMDD);
        String id = snowflake.nextId()
                + "";

        String formattedUserId = formatUserId(
                userId
        );

        String suffix = id.substring(
                id.length() - 1 - 7,
                id.length() - 1
        );
        return datePrefix
                + formattedUserId
                + suffix;
    }

    @NotNull
    private String formatUserId(
            Long userId
    ) {
        String format = String.format(
                "%0" + COUPON_USER_ID_LENGTH + "d",
                userId
        );
        if (format.length() == COUPON_USER_ID_LENGTH) {
            return format;
        }

        return format.substring(
                format.length() - 1 - COUPON_USER_ID_LENGTH,
                format.length() - 1
        );
    }

    public void grant(
            CouponActivity activity,
            Long userId,
            ActivityCouponDTO activityCoupon
    ) {
        Stream.iterate(0, n -> n + 1)
                .limit(activityCoupon.getCountPerPerson())
                .forEach(n -> grant(
                        activity,
                        userId,
                        activityCoupon.getTemplateId()
                ));
    }

    private CouponTemplate getCouponTemplate(Long templateId) {
        ICouponTemplateService templateService = getTemplateService();

        return templateService.getById(templateId);
    }

    @NotNull
    private ICouponTemplateService getTemplateService() {
        return applicationContext.getBean(ICouponTemplateService.class);
    }

    private void grant(
            CouponActivity activity,
            Long userId,
            Long templateId
    ) {
        CouponTemplate template = getCouponTemplate(templateId);
        if(template == null || template.getAvailableNumber() <= 0){
            return;
        }

        LocalDateTime startTime = getStartTime(
                activity,
                template
        );
        LocalDateTime endTime = getEndTime(
                activity,
                template
        );
        if (endTime == null
                || startTime == null
                || startTime.isAfter(endTime)
        ) {
            return;
        }

        String sn = generateCouponSn(userId);
        Coupon coupon = new Coupon()
                .setSn(sn)
                .setActivityId(activity.getId())
                .setTemplateId(template.getId())
                .setUserId(userId)
                .setStartTime(startTime)
                .setEndTime(endTime);

        ICouponService couponService = getCouponService();
        couponService.save(coupon);

        updateTemplate(template);

    }

    private void updateTemplate(CouponTemplate template) {
        CouponTemplate updateTemplate = new CouponTemplate()
                .setId(template.getId())
                .setVersion(template.getVersion())
                .setAvailableNumber(template.getAvailableNumber() -1);

        ICouponTemplateService templateService = getTemplateService();
        boolean success = templateService.updateById(updateTemplate);
        if (!success) {
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }
    }

    @Nullable
    private LocalDateTime getStartTime(
            CouponActivity activity,
            CouponTemplate template
    ) {
        return DateUtils.max(
                template.buildStartTime(),
                activity.getStartTime()
        );
    }

    @Nullable
    private LocalDateTime getEndTime(
            CouponActivity activity,
            CouponTemplate template
    ) {
        return DateUtils.min(
                template.buildEndTime(),
                activity.getEndTime()
        );
    }

    public abstract void grant(
            CouponActivity activity,
            Long userId
    );
}
