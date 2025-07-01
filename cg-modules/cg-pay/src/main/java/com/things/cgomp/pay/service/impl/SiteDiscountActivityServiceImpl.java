package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.enums.DelFlagEnum;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.order.api.domain.DiscountClassEnum;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;
import com.things.cgomp.pay.api.enums.DeductionTypeEnum;
import com.things.cgomp.pay.api.enums.DiscountTypeEnum;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountActivityPageDTO;
import com.things.cgomp.pay.enums.ActivityDimensionEnum;
import com.things.cgomp.pay.enums.ErrorCodeConstants;
import com.things.cgomp.pay.mapper.SiteDiscountActivityMapper;
import com.things.cgomp.pay.service.ISiteDiscountActivityService;
import com.things.cgomp.pay.service.ISiteDiscountActivitySiteService;
import com.things.cgomp.pay.service.ISiteDiscountActivityTemplateService;
import com.things.cgomp.pay.service.ISiteDiscountTemplateService;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountActivityVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 站点折扣活动表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Service
public class SiteDiscountActivityServiceImpl extends ServiceImpl<SiteDiscountActivityMapper, SiteDiscountActivity>
        implements ISiteDiscountActivityService {

    @Resource
    private ISiteDiscountActivitySiteService activitySiteService;
    @Resource
    private ISiteDiscountActivityTemplateService activityTemplateService;
    @Resource
    private ISiteDiscountTemplateService discountTemplateService;

    @Override
    public Map<Long, String> getActivityNameMap(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new HashMap<>();
        }
        List<SiteDiscountActivity> activities = baseMapper.selectBatchIds(ids);
        return activities.stream()
                .collect(Collectors.toMap(
                        SiteDiscountActivity::getId,
                        SiteDiscountActivity::getName,
                        (a,b)->a
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveActivity(
            SiteDiscountActivity activity
    ) {
        check(activity);

        activity.setCreateBy(SecurityUtils.getUserId());
        baseMapper.insert(activity);

        activitySiteService.saveSiteIds(
                activity.getId(),
                activity.getSiteIds()
        );

        activityTemplateService.saveTemplateIds(
                activity.getId(),
                activity.getTemplateIds()
        );

        return activity.getId();
    }

    @Override
    public List<DiscountCouponVo> getDiscountCoupons(
            Long operatorId,
            OrderInfo orderInfo
    ) {
        LocalDateTime settlementTime = orderInfo.getSettlementTime();
        Long siteId = orderInfo.getSiteId();
        if (settlementTime == null || operatorId == null || siteId == null) {
            return new ArrayList<>();
        }

        List<SiteDiscountActivity> activities = baseMapper.selectAvailableActivities(
                operatorId,
                settlementTime.toLocalDate(),
                settlementTime.toLocalTime(),
                siteId
        );

        Map<Long, List<Long>> siteIdsMap = getSiteIdsMap(activities);

        Map<Long, SiteDiscountTemplate> templateMap = getTemplateMap(activities);

        return activities.stream()
                .map(activity -> getDiscountCoupon(
                        activity,
                        templateMap,
                        siteIdsMap
                ))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Map<Long, List<Long>> getSiteIdsMap(List<SiteDiscountActivity> activities) {
        List<Long> activityIds = activities.stream()
                .map(SiteDiscountActivity::getId)
                .collect(Collectors.toList());

        return activitySiteService.getSiteIdsMap(activityIds);
    }

    private DiscountCouponVo getDiscountCoupon(
            SiteDiscountActivity activity,
            Map<Long, SiteDiscountTemplate> templateMap,
            Map<Long, List<Long>> siteIdsMap
    ) {
        SiteDiscountTemplate template = templateMap.get(activity.getTemplateId());
        if(template == null){
            return null;
        }

        Integer discountType = DiscountTypeEnum.getDiscountTypeByActivityType(
                template.getActivityType()
        );
        Integer deductionType = DeductionTypeEnum.getDeductionType(
                template.getActivityType()
        );

        LocalDateTime endTime = DateUtils.endOfDay(activity.getEndTime());
        List<Long> siteIds = siteIdsMap.get(activity.getId());
        return new DiscountCouponVo()
                .setId(activity.getId())
                .setSiteDimension(activity.getSiteDimension())
                .setDiscountClass(DiscountClassEnum.SITE.getType())
                .setDiscountType(discountType)
                .setDeductionType(deductionType)
                .setDiscount(template.getDiscount())
                .setEndTime(endTime)
                .setSiteIds(siteIds);
    }

    private Map<Long, SiteDiscountTemplate> getTemplateMap(
            List<SiteDiscountActivity> activities
    ) {
        List<Long> templateIds = activities.stream()
                .map(SiteDiscountActivity::getTemplateId)
                .collect(Collectors.toList());

        return discountTemplateService.getTemplateMap(templateIds);
    }

    private void check(
            SiteDiscountActivity activity
    ) {
        discountTemplateService.checkTemplates(
                activity.getTemplateIds()
        );

        checkActivityTime(
                activity
        );
    }

    private void checkActivityTime(
            SiteDiscountActivity activity
    ) {
        if (ActivityDimensionEnum.All.getType().equals(activity.getSiteDimension())) {
            checkAllActivityTime(
                    activity
            );
            return;
        }

        checkAllSiteDimensionActivityTime(
                activity
        );

        checkPartSiteDimensionActivityTime(
                activity
        );
    }

    private void checkPartSiteDimensionActivityTime(
            SiteDiscountActivity activity
    ) {
        List<SiteDiscountActivity> partSiteActivities = baseMapper.selectSiteActivities(
                activity.getOperatorId(),
                ActivityDimensionEnum.PART.getType(),
                activity.getStartTime(),
                activity.getEndTime(),
                activity.getSiteIds()
        );

        checkActivityTime(
                activity.getId(),
                partSiteActivities
        );
    }

    private void checkAllActivityTime(
            SiteDiscountActivity activity
    ) {
        List<SiteDiscountActivity> activities = baseMapper.selectSiteActivities(
                activity.getOperatorId(),
                null,
                activity.getStartTime(),
                activity.getEndTime(),
                null
        );

        checkActivityTime(
                activity.getId(),
                activities
        );
    }

    private void checkAllSiteDimensionActivityTime(
            SiteDiscountActivity activity
    ) {
        List<SiteDiscountActivity> allSiteActivities = baseMapper.selectSiteActivities(
                activity.getOperatorId(),
                ActivityDimensionEnum.All.getType(),
                activity.getStartTime(),
                activity.getEndTime(),
                null
        );

        checkActivityTime(
                activity.getId(),
                allSiteActivities
        );
    }

    private void checkActivityTime(
            Long activityId,
            List<SiteDiscountActivity> dbActivities
    ) {
        if(dbActivities.isEmpty()){
            return;
        }

        if (activityId == null) {
            throw new ServiceException(ErrorCodeConstants.ACTIVITY_TIME_CONFLICT);
        }

        if(dbActivities.size() > 1 || !dbActivities.get(0).getId().equals(activityId)){
            throw new ServiceException(ErrorCodeConstants.ACTIVITY_TIME_CONFLICT);
        }
    }

    @Override
    public SiteDiscountActivity getActivity(Long id) {
        SiteDiscountActivity activity = baseMapper.selectById(id);
        if(activity == null){
            return null;
        }

        List<Long> siteIds = activitySiteService.getSiteIds(activity.getId());
        List<SiteDiscountTemplate> templates = activityTemplateService.getTemplates(
                activity.getId()
        );

        List<Long> templateIds = getTemplateIds(templates);

        activity.setSiteIds(siteIds)
                .setTemplateIds(templateIds)
                .setTemplates(templates);

        return activity;
    }

    @NotNull
    private List<Long> getTemplateIds(
            List<SiteDiscountTemplate> templates
    ) {
        return templates.stream()
                .map(SiteDiscountTemplate::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editActivity(
            SiteDiscountActivity activity
    ) {
        check(
                activity
        );

        activity.setStatus(null)
                .setUpdateBy(SecurityUtils.getUserId());
        baseMapper.updateById(activity);

        activitySiteService.updateSiteIds(
                activity.getId(),
                activity.getSiteIds()
        );

        activityTemplateService.updateTemplateIds(
                activity.getId(),
                activity.getTemplateIds()
        );

    }

    @Override
    public List<SiteDiscountActivity> selectActivities(
            Long templateId
    ) {
        return baseMapper.selectActivitiesByTemplateId(
                templateId
        );
    }

    @Override
    public void deleteActivity(Long id) {
        SiteDiscountActivity activity = new SiteDiscountActivity()
                .setId(id)
                .setUpdateBy(SecurityUtils.getUserId())
                .setDelFlag(DelFlagEnum.DELETE.getType());
        baseMapper.updateById(activity);
    }

    @Override
    public void switchActivity(
            Long id,
            Integer status
    ) {
        SiteDiscountActivity activity = new SiteDiscountActivity()
                .setId(id)
                .setUpdateBy(SecurityUtils.getUserId())
                .setStatus(status);
        baseMapper.updateById(activity);
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<SiteDiscountActivityVo> selectPage(
            SiteDiscountActivityPageDTO pageDTO
    ) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<SiteDiscountActivityVo> activities = baseMapper.selectActivities(pageDTO);
            activities.forEach(SiteDiscountActivityVo::formatStatus);

            return new PageInfo<>(activities);
        }
    }
}
