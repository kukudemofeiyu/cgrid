package com.things.cgomp.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.common.core.utils.DateUtils;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.order.api.enums.OccupyTypeEnum;
import com.things.cgomp.order.domain.SiteOccupyFee;
import com.things.cgomp.order.dto.SiteOccupyFeeDTO;
import com.things.cgomp.order.mapper.SiteOccupyFeeMapper;
import com.things.cgomp.order.service.ISiteOccupyFeeService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 站点占位费表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-02
 */
@Service
public class SiteOccupyFeeServiceImpl extends ServiceImpl<SiteOccupyFeeMapper, SiteOccupyFee>
        implements ISiteOccupyFeeService {

    private void saveOccupyFee(SiteOccupyFee siteOccupyFee) {
        SiteOccupyFee siteOccupyFeeDb = baseMapper.selectFee(
                siteOccupyFee.getSiteId(),
                siteOccupyFee.getType()
        );
        if(siteOccupyFeeDb == null){
            siteOccupyFee.setCreateBy(SecurityUtils.getUserId());
            baseMapper.insert(siteOccupyFee);
        }else {
            siteOccupyFee.setId(siteOccupyFeeDb.getId())
                    .setUpdateBy(SecurityUtils.getUserId());
            baseMapper.updateById(siteOccupyFee);
        }
    }

    @Override
    public SiteOccupyFeeDTO selectOccupyFee(Long siteId) {
        List<SiteOccupyFee> siteOccupyFees = baseMapper.selectFees(siteId);
        if(siteOccupyFees.isEmpty()){
            return null;
        }

        SiteOccupyFeeDTO siteOccupyFeeDTO = new SiteOccupyFeeDTO()
                .setSiteId(siteId);

        for (SiteOccupyFee siteOccupyFee : siteOccupyFees) {
            if(OccupyTypeEnum.BEFORE_CHARGING.getType().equals(siteOccupyFee.getType())){
                siteOccupyFeeDTO.setPreviousCappedAmount(siteOccupyFee.getCappedAmount())
                        .setPreviousStatus(siteOccupyFee.getStatus())
                        .setPreviousUnitPrice(siteOccupyFee.getUnitPrice())
                        .setPreviousFreeDuration(siteOccupyFee.getFreeDuration());
            }else {
                siteOccupyFeeDTO.setPostCappedAmount(siteOccupyFee.getCappedAmount())
                        .setPostStatus(siteOccupyFee.getStatus())
                        .setPostUnitPrice(siteOccupyFee.getUnitPrice())
                        .setPostFreeDuration(siteOccupyFee.getFreeDuration());
            }
        }

        return siteOccupyFeeDTO;
    }

    @Override
    public Long saveOccupyFee(SiteOccupyFeeDTO siteOccupyFee) {
        SiteOccupyFee previousOccupyFee = new SiteOccupyFee()
                .setSiteId(siteOccupyFee.getSiteId())
                .setType(OccupyTypeEnum.BEFORE_CHARGING.getType())
                .setCappedAmount(siteOccupyFee.getPreviousCappedAmount())
                .setFreeDuration(siteOccupyFee.getPreviousFreeDuration())
                .setUnitPrice(siteOccupyFee.getPreviousUnitPrice())
                .setStatus(siteOccupyFee.getPreviousStatus());
        saveOccupyFee(previousOccupyFee);

        SiteOccupyFee postOccupyFee = new SiteOccupyFee()
                .setSiteId(siteOccupyFee.getSiteId())
                .setType(OccupyTypeEnum.AFTER_CHARGING.getType())
                .setCappedAmount(siteOccupyFee.getPostCappedAmount())
                .setFreeDuration(siteOccupyFee.getPostFreeDuration())
                .setUnitPrice(siteOccupyFee.getPostUnitPrice())
                .setStatus(siteOccupyFee.getPostStatus());
        saveOccupyFee(postOccupyFee);

        return siteOccupyFee.getSiteId();
    }

    @Override
    public BigDecimal calculateFee(
            Long siteId,
            Integer type,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        SiteOccupyFee siteOccupyFee = baseMapper.selectEnableFee(
                siteId,
                type
        );

        if (siteOccupyFee == null
                || startTime == null
                || endTime == null
        ) {
            return null;
        }

        return calculateFee(
                startTime,
                endTime,
                siteOccupyFee
        );
    }

    private BigDecimal calculateFee(
            LocalDateTime startTime,
            LocalDateTime endTime,
            SiteOccupyFee siteOccupyFee
    ) {
        BigDecimal totalFee = getTotalFee(
                startTime,
                endTime,
                siteOccupyFee
        );

        if(totalFee == null){
            return null;
        }

        if(totalFee.compareTo(siteOccupyFee.getCappedAmount()) >0){
            return siteOccupyFee.getCappedAmount();
        }

        return totalFee;
    }

    private BigDecimal getTotalFee(
            LocalDateTime startTime,
            LocalDateTime endTime,
            SiteOccupyFee siteOccupyFee
    ) {
        double chargeableTime = getChargeableTime(
                startTime,
                endTime,
                siteOccupyFee
        );

        if(chargeableTime < 0){
            return null;
        }

        return siteOccupyFee.getUnitPrice()
                .multiply(new BigDecimal(chargeableTime))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private Double getChargeableTime(
            LocalDateTime startTime,
            LocalDateTime endTime,
            SiteOccupyFee siteOccupyFee
    ) {
        Double totalOccupyTime = getIntervalTime(
                startTime,
                endTime
        );

        return totalOccupyTime - siteOccupyFee.getFreeDuration();
    }

    private Double getIntervalTime(
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {
        BigDecimal intervalTime = DateUtils.calIntervalTime(
                startTime,
                endTime
        );

        return intervalTime.doubleValue() * 60;
    }
}
