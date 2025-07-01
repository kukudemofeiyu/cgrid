package com.things.cgomp.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.pay.api.domain.CouponTemplate;
import com.things.cgomp.pay.dto.coupon.CouponTemplateListDTO;
import com.things.cgomp.pay.dto.coupon.CouponTemplatePageDTO;
import com.things.cgomp.pay.vo.coupon.CouponTemplateListVo;
import com.things.cgomp.pay.vo.coupon.CouponTemplateVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 优惠券模板表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
public interface ICouponTemplateService extends IService<CouponTemplate> {

    Map<Long, String> getNameMap(
            List<Long> ids
    );

    Long saveTemplate(
            CouponTemplate template
    );

    CouponTemplate selectTemplate(
            Long id
    );

    void editTemplate(
            CouponTemplate template
    );

    void switchTemplate(
            CouponTemplate template
    );

    PageInfo<CouponTemplateVo> selectPage(
            CouponTemplatePageDTO pageDTO
    );

    List<CouponTemplateListVo> selectTemplates(
            CouponTemplateListDTO templateListDTO
    );

    Map<Long, CouponTemplate> selectTemplateMap(
            List<Long> templateIds
    );

}
