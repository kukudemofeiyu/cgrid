package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.device.api.RemoteSiteService;
import com.things.cgomp.device.api.domain.Site;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.domain.DiscountClassEnum;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.enums.OrderTypeEnum;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.api.domain.CouponTemplate;
import com.things.cgomp.pay.api.dto.CouponAppPageDTO;
import com.things.cgomp.pay.api.enums.DiscountTypeEnum;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import com.things.cgomp.pay.domain.CouponActivity;
import com.things.cgomp.pay.dto.coupon.ActivityCouponDTO;
import com.things.cgomp.pay.dto.coupon.CouponPageDTO;
import com.things.cgomp.pay.mapper.CouponMapper;
import com.things.cgomp.pay.service.ICouponService;
import com.things.cgomp.pay.service.ICouponTemplateService;
import com.things.cgomp.pay.service.ISiteDiscountActivityService;
import com.things.cgomp.pay.service.impl.grantcoupon.CouponGrant;
import com.things.cgomp.pay.service.impl.grantcoupon.CouponGrantFactory;
import com.things.cgomp.pay.vo.coupon.CouponVo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-26
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon>
        implements ICouponService {

    @Resource
    private ICouponTemplateService couponTemplateService;
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private RemoteOrderService remoteOrderService;
    @Resource
    private RemoteSiteService remoteSiteService;
    @Resource
    private CouponGrantFactory couponGrantFactory;

    @Override
    public void grantCoupons(
            CouponActivity activity
    ) {
        CouponGrant couponGrant = couponGrantFactory.createCouponGrant(activity.getType());
        if(couponGrant != null){
            activity.getUserIds()
                    .forEach(userId -> couponGrant.grant(
                            activity,
                            userId
                    ));
        }
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<CouponVo> selectPage(
            CouponPageDTO pageDTO
    ) {
        pageDTO.formatTime();
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<CouponVo> coupons = baseMapper.selectCoupons(pageDTO);
            coupons.forEach(CouponVo::formatStatus);
            return new PageInfo<>(coupons);
        }
    }

    @Override
    public Coupon selectCoupon(Long id) {
        Coupon coupon = baseMapper.selectById(id);
        if(coupon == null){
            return null;
        }

        CouponTemplate template = getTemplate(coupon.getTemplateId());
        return coupon.setTemplate(template);
    }

    @Override
    public List<DiscountCouponVo> selectSiteActivityAndCoupons(
            Long orderId
    ) {
        List<OrderInfo> orders = selectOrders(orderId);
        if (CollectionUtils.isEmpty(orders)) {
            return new ArrayList<>();
        }

        return orders.stream()
                .filter(orderInfo -> OrderTypeEnum.REAL_TIME.getType().equals(orderInfo.getOrderType()))
                .map(this::selectSiteActivityAndCoupons)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<CouponVo> selectAppCoupon(CouponAppPageDTO pageDTO) {
        return null;
    }

    @NotNull
    private List<DiscountCouponVo> selectSiteActivityAndCoupons(OrderInfo orderInfo) {
        Long operatorId = getOperatorId(orderInfo.getSiteId());
        if(operatorId == null){
            return new ArrayList<>();
        }

        List<DiscountCouponVo> siteActivities = getSiteActivities(
                operatorId,
                orderInfo
        );

        List<DiscountCouponVo> coupons = getCoupons(
                operatorId,
                orderInfo
        );

        List<DiscountCouponVo> discountCoupons = new ArrayList<>();
        discountCoupons.addAll(siteActivities);
        discountCoupons.addAll(coupons);

        fillData(
                orderInfo.getSiteId(),
                discountCoupons
        );
        return discountCoupons;
    }

    private void fillData(
            Long siteId,
            List<DiscountCouponVo> discountCoupons
    ) {
        Site site = getSite(siteId);

        discountCoupons.forEach(discountCoupon -> fillData(site, discountCoupon));
    }

    private void fillData(
            Site site,
            DiscountCouponVo discountCoupon
    ) {
        discountCoupon.setSite(site);
    }

    private Long getOperatorId(Long siteId) {
        Site site = getSite(siteId);
        if(site == null){
            return null;
        }

        return site.getOperatorId();
    }

    private Site getSite(Long siteId) {
        if(siteId == null){
            return null;
        }
        R<Site> siteR = remoteSiteService.selectSite(siteId);

        return siteR.getData();
    }

    @NotNull
    private List<DiscountCouponVo> getSiteActivities(
            Long operatorId,
            OrderInfo orderInfo
    ) {
        ISiteDiscountActivityService siteActivityService =
                applicationContext.getBean(ISiteDiscountActivityService.class);

        return siteActivityService.getDiscountCoupons(
                operatorId,
                orderInfo
        );
    }

    @NotNull
    private List<DiscountCouponVo> getCoupons(
            Long operatorId,
            OrderInfo orderInfo
    ) {
        Long userId = orderInfo.getUserId();
        LocalDateTime settlementTime = orderInfo.getSettlementTime();
        Long siteId = orderInfo.getSiteId();
        if(userId == null || settlementTime == null || siteId == null){
            return new ArrayList<>();
        }

        List<Coupon> coupons = baseMapper.selectAvailableCoupons(
                operatorId,
                userId,
                siteId,
                settlementTime
        );

        Map<Long, CouponTemplate> templateMap = selectTemplateMap(coupons);

        return coupons.stream()
                .map(coupon -> getCoupons(
                        templateMap,
                        coupon
                ))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Map<Long, CouponTemplate> selectTemplateMap(List<Coupon> coupons) {
        List<Long> templateIds = getTemplateIds(coupons);
        return couponTemplateService.selectTemplateMap(templateIds);
    }

    @Nullable
    private DiscountCouponVo getCoupons(
            Map<Long, CouponTemplate> templateMap,
            Coupon coupon
    ) {
        CouponTemplate template = templateMap.get(coupon.getTemplateId());
        if(template == null){
            return null;
        }

        Integer discountType = DiscountTypeEnum.getDiscountTypeByCouponType(
                template.getCouponType()
        );
        return new DiscountCouponVo()
                .setId(coupon.getId())
                .setSiteDimension(template.getSiteDimension())
                .setDiscountClass(DiscountClassEnum.COUPON.getType())
                .setDiscountType(discountType)
                .setDeductionType(template.getDeductionType())
                .setDiscount(template.buildDiscount())
                .setEndTime(coupon.getEndTime())
                .setFeeLimit(template.getFeeLimit())
                .setSiteIds(template.getSiteIds());
    }

    @NotNull
    private List<Long> getTemplateIds(List<Coupon> coupons) {
        return coupons.stream()
                .map(Coupon::getTemplateId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<OrderInfo> selectOrders(Long orderId) {
        OrderInfo orderInfo = selectOrder(orderId);
        if(orderInfo == null){
            return new ArrayList<>();
        }

        return Collections.singletonList(orderInfo);
    }

    private OrderInfo selectOrder(Long orderId) {
        R<OrderInfo> orderR = remoteOrderService.selectOrder(orderId);
        return orderR.getData();
    }

    private CouponTemplate getTemplate(Long id) {
        ICouponTemplateService templateService =
                applicationContext.getBean(ICouponTemplateService.class);
        return templateService.selectTemplate(id);
    }

    private Map<Long, CouponTemplate> getTemplateMap(List<ActivityCouponDTO> coupons) {
        List<Long> templateIds = coupons.stream()
                .map(ActivityCouponDTO::getTemplateId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return couponTemplateService.selectTemplateMap(templateIds);
    }

}
