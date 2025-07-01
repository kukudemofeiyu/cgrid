package com.things.cgomp.pay.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.pay.api.domain.CouponTemplate;
import com.things.cgomp.pay.dto.coupon.CouponTemplateListDTO;
import com.things.cgomp.pay.dto.coupon.CouponTemplatePageDTO;
import com.things.cgomp.pay.vo.coupon.CouponTemplateListVo;
import com.things.cgomp.pay.vo.coupon.CouponTemplateVo;

import java.util.List;

/**
 * <p>
 * 优惠券模板表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface CouponTemplateMapper extends BaseMapper<CouponTemplate> {

    List<CouponTemplateVo> selectTemplates(
            CouponTemplatePageDTO pageDTO
    );

    List<CouponTemplateListVo> selectPullDownTemplates(
            CouponTemplateListDTO templateListDTO
    );
}
