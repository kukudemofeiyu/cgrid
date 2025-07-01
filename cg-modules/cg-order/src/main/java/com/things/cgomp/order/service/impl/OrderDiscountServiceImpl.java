package com.things.cgomp.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.order.api.enums.OrderTypeEnum;
import com.things.cgomp.order.api.domain.OrderDiscount;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.domain.DiscountClassEnum;
import com.things.cgomp.pay.api.*;
import com.things.cgomp.pay.api.enums.DiscountTypeEnum;
import com.things.cgomp.order.enums.ErrorCodeConstants;
import com.things.cgomp.order.mapper.OrderDiscountMapper;
import com.things.cgomp.order.service.ICouponService;
import com.things.cgomp.order.service.IOrderDiscountService;
import com.things.cgomp.order.vo.CouponDiscountVo;
import com.things.cgomp.order.vo.SiteDiscountVo;
import com.things.cgomp.pay.api.domain.Coupon;
import com.things.cgomp.pay.api.domain.CouponTemplate;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;
import com.things.cgomp.pay.api.enums.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 订单折扣明细表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-04-01
 */
@Service
public class OrderDiscountServiceImpl extends ServiceImpl<OrderDiscountMapper, OrderDiscount> implements IOrderDiscountService {

    @Resource
    private RemoteSiteDiscountActivityService remoteSiteDiscountActivityService;
    @Resource
    private RemoteSiteDiscountTemplateService remoteSiteDiscountTemplateService;
    @Resource
    private RemoteCouponActivityService remoteCouponActivityService;
    @Resource
    private RemoteCouponTemplateService remoteCouponTemplateService;
    @Resource
    private RemoteCouponService remoteCouponService;
    @Resource
    private ICouponService couponService;

    @Override
    public void saveDiscounts(
            List<Long> siteActivityIds,
            List<Long> couponIds,
            OrderInfo orderInfo
    ) {
        saveSiteDiscounts(
                siteActivityIds,
                orderInfo
        );

        saveCouponDiscounts(
                couponIds,
                orderInfo
        );
    }

    @Override
    public void saveDiscounts(Object config) {
        List<OrderDiscount> discounts = getOrderDiscounts(config);

        if(CollectionUtils.isEmpty(discounts)){
            return;
        }

        discounts.forEach(discount->baseMapper.insert(discount));

        updateCouponStatus(discounts);
    }

    private void updateCouponStatus(List<OrderDiscount> discounts) {
        discounts.stream()
                .map(OrderDiscount::getCouponId)
                .filter(Objects::nonNull)
                .forEach(this::updateCouponStatus);
    }

    private List<OrderDiscount> getOrderDiscounts(Object config) {
        List<OrderDiscount> discounts = JSON.parseArray(
                JSON.toJSONString(config),
                OrderDiscount.class
        );

        if(CollectionUtils.isEmpty(discounts)){
            return discounts;
        }

        discounts.forEach(OrderDiscount::setConfig);
        return discounts;
    }

    @Override
    public List<OrderDiscount> getDiscounts(
            List<Long> siteActivityIds,
            List<Long> couponIds,
            OrderInfo orderInfo
    ) {

        List<OrderDiscount> siteDiscounts = getSiteDiscounts(
                siteActivityIds,
                orderInfo
        );

        List<OrderDiscount> couponDiscounts = getCouponDiscounts(
                couponIds,
                orderInfo
        );

        List<OrderDiscount> allDiscounts = new ArrayList<>();
        allDiscounts.addAll(siteDiscounts);
        allDiscounts.addAll(couponDiscounts);
        return allDiscounts;
    }

    @Override
    public List<OrderDiscount> selectDiscounts(Long orderId) {
        return baseMapper.selectDiscounts(orderId);
    }

    @Override
    public Map<Long, List<OrderDiscount>> selectDiscountsMap(List<Long> orderIds) {
        if(orderIds.isEmpty()){
            return new HashMap<>();
        }

        List<OrderDiscount> discounts = baseMapper.selectDiscountsByOrderIds(orderIds);

        fillData(discounts);

        Map<Long, List<OrderDiscount>> discountsMap = new HashMap<>();
        for (OrderDiscount discount : discounts) {
            List<OrderDiscount> subDiscounts = discountsMap.computeIfAbsent(discount.getOrderId(), k -> new ArrayList<>());
            subDiscounts.add(discount);
        }
        return discountsMap;
    }

    private void fillData(List<OrderDiscount> discounts) {
        Map<Long, String> siteActivityNameMap = getSiteActivityNameMap(discounts);

        Map<Long, String> siteTemplateNameMap = getSiteTemplateNameMap(discounts);

        Map<Long, String> couponActivityNameMap = getCouponActivityNameMap(discounts);

        Map<Long, String> couponTemplateNameMap = getCouponTemplateNameMap(discounts);

        discounts.forEach(discount ->
                fillData(
                        siteActivityNameMap,
                        siteTemplateNameMap,
                        couponActivityNameMap,
                        couponTemplateNameMap,
                        discount
                ));
    }

    private void fillData(
            Map<Long, String> siteActivityNameMap,
            Map<Long, String> siteTemplateNameMap,
            Map<Long, String> couponActivityNameMap,
            Map<Long, String> couponTemplateNameMap,
            OrderDiscount discount
    ) {
        discount.setActivityName(
                        siteActivityNameMap,
                        couponActivityNameMap
                )
                .setTemplateName(
                        siteTemplateNameMap,
                        couponTemplateNameMap
                );
    }

    private Map<Long, String> getSiteActivityNameMap(List<OrderDiscount> discounts) {
        List<Long> siteActivityIds = getActivityIds(
                discounts,
                DiscountClassEnum.SITE
        );

        if(siteActivityIds.isEmpty()){
            return new HashMap<>();
        }

        R<Map<Long, String>> activityNameMapR = remoteSiteDiscountActivityService.getActivityNameMap(siteActivityIds);
        return activityNameMapR.getData();
    }

    private Map<Long, String> getCouponActivityNameMap(List<OrderDiscount> discounts) {
        List<Long> couponActivityIds = getActivityIds(
                discounts,
                DiscountClassEnum.COUPON
        );

        if(couponActivityIds.isEmpty()){
            return new HashMap<>();
        }

        R<Map<Long, String>> activityNameMapR = remoteCouponActivityService.getNameMap(couponActivityIds);
        return activityNameMapR.getData();
    }

    private Map<Long, String> getSiteTemplateNameMap(List<OrderDiscount> discounts) {
        List<Long> siteTemplateIds = getTemplateIds(
                discounts,
                DiscountClassEnum.SITE
        );

        if(siteTemplateIds.isEmpty()){
            return new HashMap<>();
        }

        R<Map<Long, String>> nameMapR = remoteSiteDiscountTemplateService.getTemplateNameMap(siteTemplateIds);
        return nameMapR.getData();
    }

    private Map<Long, String> getCouponTemplateNameMap(List<OrderDiscount> discounts) {
        List<Long> couponTemplateIds = getTemplateIds(
                discounts,
                DiscountClassEnum.COUPON
        );

        if(couponTemplateIds.isEmpty()){
            return new HashMap<>();
        }

        R<Map<Long, String>> nameMapR = remoteCouponTemplateService.getNameMap(couponTemplateIds);
        return nameMapR.getData();
    }

    private List<Long> getActivityIds(
            List<OrderDiscount> discounts,
            DiscountClassEnum discountClassEnum
    ) {
        return discounts.stream()
                .filter(discount -> discountClassEnum.getType().equals(discount.getDiscountClass()))
                .map(OrderDiscount::getActivityId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Long> getTemplateIds(
            List<OrderDiscount> discounts,
            DiscountClassEnum discountClassEnum
    ) {
        return discounts.stream()
                .filter(discount -> discountClassEnum.getType().equals(discount.getDiscountClass()))
                .map(OrderDiscount::getTemplateId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    private void saveSiteDiscounts(
            List<Long> siteActivityIds,
            OrderInfo orderInfo
    ) {
        if(!OrderTypeEnum.REAL_TIME.getType().equals(orderInfo.getOrderType())
            || CollectionUtils.isEmpty(siteActivityIds)){
            return;
        }

        siteActivityIds.forEach(siteActivityId-> saveSiteDiscount(
                siteActivityId,
                orderInfo
        ));
    }

    private List<OrderDiscount> getSiteDiscounts(
            List<Long> siteActivityIds,
            OrderInfo orderInfo
    ) {
        if (!OrderTypeEnum.REAL_TIME.getType().equals(orderInfo.getOrderType())
                || CollectionUtils.isEmpty(siteActivityIds)) {
            return new ArrayList<>();
        }

        return siteActivityIds.stream()
                .map(siteActivityId -> getSiteDiscounts(
                        siteActivityId,
                        orderInfo
                ))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private OrderDiscount getSiteDiscounts(
            Long siteActivityId,
            OrderInfo orderInfo
    ) {
        if(siteActivityId == null){
            return null;
        }

        SiteDiscountActivity siteActivity = getSiteActivity(
                siteActivityId
        );

        SiteDiscountTemplate siteTemplate = checkSiteActivity(
                siteActivity,
                orderInfo
        );

        return getSiteDiscount(
                orderInfo,
                siteActivity,
                siteTemplate
        );
    }

    private List<OrderDiscount> getCouponDiscounts(
            List<Long> couponIds,
            OrderInfo orderInfo
    ) {
        if (!OrderTypeEnum.REAL_TIME.getType().equals(orderInfo.getOrderType())
                || CollectionUtils.isEmpty(couponIds)) {
            return new ArrayList<>();
        }

        return couponIds.stream()
                .map(couponId -> getCouponDiscount(
                        couponId,
                        orderInfo
                ))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private void saveCouponDiscounts(
            List<Long> couponIds,
            OrderInfo orderInfo
    ) {
        if(!OrderTypeEnum.REAL_TIME.getType().equals(orderInfo.getOrderType())
                || CollectionUtils.isEmpty(couponIds)){
            return;
        }

        couponIds.forEach(couponId-> saveCouponDiscount(
                couponId,
                orderInfo
        ));
    }

    private OrderDiscount getCouponDiscount(
            Long couponId,
            OrderInfo orderInfo
    ) {
        if(couponId == null){
            return null;
        }

        Coupon coupon = getCoupon(
                couponId
        );
        CouponTemplate siteTemplate = checkCoupon(
                coupon,
                orderInfo
        );

       return getCouponDiscount(
                orderInfo,
               coupon,
                siteTemplate
        );
    }

    private void saveCouponDiscount(
            Long couponId,
            OrderInfo orderInfo
    ) {
        if(couponId == null){
            return;
        }

        Coupon coupon = getCoupon(
                couponId
        );
        CouponTemplate siteTemplate = checkCoupon(
                coupon,
                orderInfo
        );

        saveCouponDiscount(
                orderInfo,
                coupon,
                siteTemplate
        );
    }

    private void saveCouponDiscount(
            OrderInfo orderInfo,
            Coupon coupon,
            CouponTemplate template
    ) {
        OrderDiscount orderDiscount = getCouponDiscount(
                orderInfo,
                coupon,
                template
        );
        baseMapper.insert(orderDiscount);

        updateCouponStatus(coupon.getId());
    }

    private OrderDiscount getCouponDiscount(
            OrderInfo orderInfo,
            Coupon coupon,
            CouponTemplate template
    ) {
        String config = getCouponConfig(
                coupon.getId(),
                template
        );

        Integer discountType = DiscountTypeEnum.getDiscountTypeByCouponType(
                template.getCouponType()
        );
        Integer deductionType = template.getDeductionType();

        BigDecimal discountAmount = getDiscountAmount(
                orderInfo,
                discountType,
                deductionType,
                template.buildDiscount()
        );

        return new OrderDiscount()
                .setOrderId(orderInfo.getId())
                .setActivityId(coupon.getActivityId())
                .setCouponId(coupon.getId())
                .setTemplateId(coupon.getTemplateId())
                .setDiscountClass(DiscountClassEnum.COUPON.getType())
                .setConfig(config)
                .setDiscountType(discountType)
                .setDeductionType(deductionType)
                .setDiscountAmount(discountAmount);
    }

    private void updateCouponStatus(Long couponId) {
        Coupon coupon = new Coupon()
                .setId(couponId)
                .setStatus(CouponStatusEnum.USED.getType())
                .setUserTime(LocalDateTime.now());

        couponService.updateById(coupon);
    }

    private void saveSiteDiscount(
            OrderInfo orderInfo,
            SiteDiscountActivity siteActivity,
            SiteDiscountTemplate template
    ) {
        OrderDiscount orderDiscount = getSiteDiscount(
                orderInfo,
                siteActivity,
                template
        );
        baseMapper.insert(orderDiscount);
    }

    private OrderDiscount getSiteDiscount(
            OrderInfo orderInfo,
            SiteDiscountActivity siteActivity,
            SiteDiscountTemplate template
    ) {
        String config = getSiteConfig(
                siteActivity.getId(),
                template
        );

        Integer discountType = DiscountTypeEnum.getDiscountTypeByActivityType(
                template.getActivityType()
        );
        Integer deductionType = DeductionTypeEnum.getDeductionType(
                template.getActivityType()
        );
        BigDecimal discountAmount = getDiscountAmount(
                orderInfo,
                discountType,
                deductionType,
                template.getDiscount()
        );

        return new OrderDiscount()
                .setOrderId(orderInfo.getId())
                .setActivityId(siteActivity.getId())
                .setTemplateId(template.getId())
                .setDiscountClass(DiscountClassEnum.SITE.getType())
                .setConfig(config)
                .setDiscountType(discountType)
                .setDeductionType(deductionType)
                .setDiscountAmount(discountAmount);
    }

    private String getCouponConfig(
            Long couponId,
            CouponTemplate template
    ) {
        CouponDiscountVo couponDiscountVo = new CouponDiscountVo()
                .setCouponId(couponId)
                .setTemplateId(template.getId())
                .setCouponType(template.getCouponType())
                .setFaceValue(template.getFaceValue())
                .setRate(template.getRate())
                .setDeductionType(template.getDeductionType())
                .setFeeLimit(template.getFeeLimit());

        return JSON.toJSONString(couponDiscountVo);
    }

    private String getSiteConfig(
            Long siteActivityId,
            SiteDiscountTemplate template
    ) {
        SiteDiscountVo siteDiscountVo = new SiteDiscountVo()
                .setActivityId(siteActivityId)
                .setTemplateId(template.getId())
                .setActivityType(template.getActivityType())
                .setDiscount(template.getDiscount());

        return JSON.toJSONString(siteDiscountVo);
    }

    private BigDecimal getDiscountAmount(
            OrderInfo orderInfo,
            Integer discountType,
            Integer deductionType,
            BigDecimal discount
    ) {
        BigDecimal baseAmount = getBaseAmount(
                orderInfo,
                deductionType
        );

        if(DiscountTypeEnum.DEDUCTION.getType().equals(discountType)){
            return discount;
        }

        if(DiscountTypeEnum.DISCOUNT.getType().equals(discountType)){
            return baseAmount.multiply(new BigDecimal("100.0").subtract(discount))
                    .divide(
                            new BigDecimal("100.0"),
                            2,
                            RoundingMode.HALF_DOWN
                    );
        }

        BigDecimal discountAmount = baseAmount.subtract(discount);
        if (discountAmount.doubleValue() < 0) {
            return new BigDecimal(0);
        }
        return discountAmount;
    }

    private BigDecimal getBaseAmount(
            OrderInfo orderInfo,
            Integer deductionType
    ) {
        if (DeductionTypeEnum.TOTAL_COST.getType().equals(deductionType)) {
            return orderInfo.getPayAmount();
        }

        return orderInfo.getServiceFee();
    }

    private void saveSiteDiscount(
            Long siteActivityId,
            OrderInfo orderInfo
    ) {
        if(siteActivityId == null){
            return;
        }

        SiteDiscountActivity siteActivity = getSiteActivity(
                siteActivityId
        );

        SiteDiscountTemplate siteTemplate = checkSiteActivity(
                siteActivity,
                orderInfo
        );

        saveSiteDiscount(
                orderInfo,
                siteActivity,
                siteTemplate
        );
    }

    private CouponTemplate checkCoupon(
            Coupon coupon,
            OrderInfo orderInfo
    ) {
        if(coupon == null){
            throw new ServiceException(ErrorCodeConstants.COUPON_NOT_FOUND);
        }

        if(CouponStatusEnum.USED.getType().equals(coupon.getStatus())){
            throw new ServiceException(ErrorCodeConstants.COUPON_HAVE_BEEN_USED);
        }

        LocalDateTime settlementTime = orderInfo.getSettlementTime();

        if(!coupon.getStartTime().isBefore(settlementTime)
                || !coupon.getEndTime().isAfter(settlementTime)
        ){
            throw new ServiceException(ErrorCodeConstants.NOT_WITHIN_COUPON_TIME_RANGE);
        }

        return checkCouponTemplate(
                orderInfo,
                coupon.getTemplate()
        );
    }

    private CouponTemplate checkCouponTemplate(
            OrderInfo orderInfo,
            CouponTemplate template
    ) {
        if(template == null || template.getFeeLimit() == null){
            throw new ServiceException(ErrorCodeConstants.COUPON_DATA_ERROR);
        }

        BigDecimal baseAmount = getBaseAmount(
                orderInfo,
                template.getDeductionType()
        );

        BigDecimal feeLimit = template.getFeeLimit();

        if(baseAmount.compareTo(feeLimit) < 0){
            throw new ServiceException(ErrorCodeConstants.COUPON_CONDITIONS_NOT_MET);
        }

        return template;
    }

    private SiteDiscountTemplate checkSiteActivity(
            SiteDiscountActivity siteActivity,
            OrderInfo orderInfo
    ) {
        if(siteActivity == null){
            throw new ServiceException(ErrorCodeConstants.SITE_ACTIVITY_NOT_FOUND);
        }

        if (ActivityStatusEnum.ENDED_MANUAL_DISABLE.getType().equals(siteActivity.getStatus())) {
            throw new ServiceException(ErrorCodeConstants.SITE_ACTIVITY_OVER);
        }

        LocalDateTime settlementTime = orderInfo.getSettlementTime();

        if(!siteActivity.getStartTime().atStartOfDay().isBefore(settlementTime)
                || !siteActivity.getEndTime().atTime(LocalTime.MAX).isAfter(settlementTime)
        ){
            throw new ServiceException(ErrorCodeConstants.NOT_WITHIN_SITE_ACTIVITY_TIME_RANGE);
        }

        return checkSiteTemplate(
                settlementTime.toLocalTime().toString(),
                siteActivity.getTemplates()
        );
    }

    private Coupon getCoupon(Long couponId) {
        R<Coupon> activityR = remoteCouponService.selectCoupon(
                couponId
        );

        return activityR.getData();
    }

    private SiteDiscountActivity getSiteActivity(Long siteActivityId) {
        R<SiteDiscountActivity> activityR = remoteSiteDiscountActivityService.getActivity(
                siteActivityId
        );

        return activityR.getData();
    }

    private SiteDiscountTemplate checkSiteTemplate(
            String settlementTime,
            List<SiteDiscountTemplate> templates
    ) {
        if( CollectionUtils.isEmpty(templates)){
            throw new ServiceException(ErrorCodeConstants.NOT_WITHIN_SITE_ACTIVITY_TIME_RANGE);
        }

        SiteDiscountTemplate discountTemplate = templates.stream()
                .filter(template -> template.getStartTime().compareTo(settlementTime) <= 0 && template.getEndTime().compareTo(settlementTime) > 0)
                .findAny()
                .orElse(null);
        if(discountTemplate == null){
            throw new ServiceException(ErrorCodeConstants.NOT_WITHIN_SITE_ACTIVITY_TIME_RANGE);
        }
        return discountTemplate;
    }
}
