package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.device.api.RemoteSiteService;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.CouponActivityPageDTO;
import com.things.cgomp.pay.enums.CouponActivityTypeEnum;
import com.things.cgomp.pay.mapper.CouponActivityMapper;
import com.things.cgomp.pay.service.ICouponActivityService;
import com.things.cgomp.pay.service.ICouponActivityTemplateService;
import com.things.cgomp.pay.service.ICouponActivityUserService;
import com.things.cgomp.pay.service.ICouponService;
import com.things.cgomp.pay.vo.coupon.CouponActivityVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券活动表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Service
public class CouponActivityServiceImpl extends ServiceImpl<CouponActivityMapper, CouponActivity>
        implements ICouponActivityService {

    @Resource
    private ICouponActivityUserService couponActivityUserService;
    @Resource
    private ICouponService couponService;
    @Resource
    private RemoteSiteService remoteSiteService;
    @Resource
    private ICouponActivityTemplateService templateService;

    @Override
    public Map<Long, String> getNameMap(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new HashMap<>();
        }
        List<CouponActivity> activities = baseMapper.selectBatchIds(ids);
        return activities.stream()
                .collect(Collectors.toMap(
                        CouponActivity::getId,
                        CouponActivity::getName,
                        (a,b)->a
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveActivity(
            CouponActivity activity
    ) {
        activity.setCreateBy(SecurityUtils.getUserId())
                .setConfig();
        baseMapper.insert(activity);

        couponActivityUserService.saveUserIds(
                activity.getId(),
                activity.getUserIds()
        );

        templateService.saveTemplateIds(
                activity.getId(),
                activity.buildTemplateIds()
        );

        if(CouponActivityTypeEnum.INTERNAL_COUPON.getType().equals(activity.getType())){
            couponService.grantCoupons(
                    activity
            );
        }

        return activity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantCoupons(OrderInfo orderInfo) {
        List<CouponActivity> activities = getMeetConditionActivities(orderInfo);

        activities.forEach(activity -> couponService.grantCoupons(activity));
    }

    @NotNull
    private List<CouponActivity> getMeetConditionActivities(
            OrderInfo orderInfo
    ) {
        if (orderInfo.getUserId() == null
                || orderInfo.getSiteId() == null
                || orderInfo.getSettlementTime() == null
        ) {
            return new ArrayList<>();
        }

        Long operatorId = getOperatorId(orderInfo.getSiteId());
        if(operatorId == null){
            return new ArrayList<>();
        }

        List<CouponActivity> activities = baseMapper.selectEnableActivities(
                operatorId,
                orderInfo.getSiteId(),
                orderInfo.getSettlementTime()
        );

        return activities.stream()
                .peek(CouponActivity::setActivityConfig)
                .peek(CouponActivity::filterCoupons)
                .filter(activity -> activity.meetCondition(orderInfo))
                .peek(activity -> activity.setUserIds(Collections.singletonList(orderInfo.getUserId())))
                .collect(Collectors.toList());
    }

    private Long getOperatorId(Long siteId) {
        R<Long> operatorIdR = remoteSiteService.getOperatorId(siteId);
        return operatorIdR.getData();
    }

    @Override
    public CouponActivity selectActivity(
            Long id
    ) {
        CouponActivity activity = baseMapper.selectById(id);
        if (activity == null) {
            return null;
        }

        List<Long> userIds = couponActivityUserService.selectUserIds(id);

        return activity.setActivityConfig()
                .setUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editActivity(
            CouponActivity activity
    ) {
        activity.setUpdateBy(SecurityUtils.getUserId())
                .setConfig();
        baseMapper.updateById(activity);

        couponActivityUserService.updateUserIds(
                activity.getId(),
                activity.getUserIds()
        );

        templateService.updateTemplateIds(
                activity.getId(),
                activity.buildTemplateIds()
        );
    }

    @Override
    public void switchActivity(
            CouponActivity activity
    ) {
        CouponActivity updateActivity = new CouponActivity()
                .setId(activity.getId())
                .setUpdateBy(SecurityUtils.getUserId())
                .setStatus(activity.getStatus());

        baseMapper.updateById(updateActivity);
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<CouponActivityVo> selectPage(
            CouponActivityPageDTO pageDTO
    ) {
        pageDTO.formatTime();
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<CouponActivityVo> activities = baseMapper.selectActivities(pageDTO);
            activities.forEach(CouponActivityVo::formatStatus);
            return new PageInfo<>(activities);
        }
    }
}
