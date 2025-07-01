package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.pay.api.domain.CouponTemplate;
import com.things.cgomp.pay.dto.coupon.CouponTemplateListDTO;
import com.things.cgomp.pay.dto.coupon.CouponTemplatePageDTO;
import com.things.cgomp.pay.enums.ErrorCodeConstants;
import com.things.cgomp.pay.mapper.CouponTemplateMapper;
import com.things.cgomp.pay.service.ICouponTemplateService;
import com.things.cgomp.pay.service.ICouponTemplateSiteService;
import com.things.cgomp.pay.vo.coupon.CouponTemplateListVo;
import com.things.cgomp.pay.vo.coupon.CouponTemplateVo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 优惠券模板表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-25
 */
@Service
public class CouponTemplateServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplate>
        implements ICouponTemplateService {

    @Resource
    private ICouponTemplateSiteService templateSiteService;

    @Override
    public Map<Long, String> getNameMap(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new HashMap<>();
        }
        List<CouponTemplate> activities = baseMapper.selectBatchIds(ids);
        return activities.stream()
                .collect(Collectors.toMap(
                        CouponTemplate::getId,
                        CouponTemplate::getName,
                        (a,b)->a
                ));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveTemplate(
            CouponTemplate template
    ) {
        template.setOperatorId(SecurityUtils.getOperatorId())
                .setAvailableNumber(template.getTotalNumber())
                .setCreateBy(SecurityUtils.getUserId());

        baseMapper.insert(template);

        templateSiteService.saveSiteIds(
                template.getId(),
                template.getSiteIds()
        );

        return template.getId();
    }

    @Override
    public CouponTemplate selectTemplate(
            Long id
    ) {
        CouponTemplate template = baseMapper.selectById(id);
        if(template == null){
            return null;
        }

        List<Long> siteIds = templateSiteService.getSiteIds(id);
        template.setSiteIds(siteIds);

        return template;
    }

    @Override
    public void editTemplate(
            CouponTemplate template
    ) {
        CouponTemplate templateFromDb = baseMapper.selectById(template.getId());
        if(templateFromDb == null){
            throw new ServiceException(ErrorCodeConstants.COUPON_TEMPLATE_NOT_FOUND);
        }

        CouponTemplate updateTemplate = new CouponTemplate()
                .setId(template.getId())
                .setVersion(templateFromDb.getVersion())
                .setUpdateBy(SecurityUtils.getUserId())
                .setRemark(template.getRemark());

        int successNum = baseMapper.updateById(updateTemplate);

        if(successNum == 0){
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }
    }

    @Override
    public void switchTemplate(
            CouponTemplate template
    ) {
        CouponTemplate templateFromDb = baseMapper.selectById(template.getId());
        if(templateFromDb == null){
            throw new ServiceException(ErrorCodeConstants.COUPON_TEMPLATE_NOT_FOUND);
        }

        CouponTemplate updateTemplate = new CouponTemplate()
                .setId(template.getId())
                .setVersion(templateFromDb.getVersion())
                .setStatus(template.getStatus());
        int successNum = baseMapper.updateById(updateTemplate);

        if(successNum == 0){
            throw new ServiceException(ErrorCodeConstants.VERSION_NUMBER_CONFLICT);
        }
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<CouponTemplateVo> selectPage(
            CouponTemplatePageDTO pageDTO
    ) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<CouponTemplateVo> templates = baseMapper.selectTemplates(pageDTO);
            templates.forEach(CouponTemplateVo::setUseCondition);
            return new PageInfo<>(templates);
        }
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public List<CouponTemplateListVo> selectTemplates(
            CouponTemplateListDTO templateListDTO
    ) {
        templateListDTO.formatTime();
        return baseMapper.selectPullDownTemplates(
                templateListDTO
        );
    }

    @Override
    public Map<Long, CouponTemplate> selectTemplateMap(
            List<Long> templateIds
    ) {
        List<CouponTemplate> templates = selectCouponTemplates(
                templateIds
        );

        return templates.stream()
                .collect(Collectors.toMap(
                        CouponTemplate::getId,
                        a->a,
                        (a,b)->a
                ));
    }

    @NotNull
    private List<CouponTemplate> selectCouponTemplates(
            List<Long> templateIds
    ) {
        if(CollectionUtils.isEmpty(templateIds)){
            return new ArrayList<>();
        }

        Map<Long, List<Long>> siteIdsMap = templateSiteService.getSiteIdsMap(templateIds);
        List<CouponTemplate> templates = baseMapper.selectBatchIds(templateIds);
        templates.forEach(template -> {
            List<Long> siteIds = siteIdsMap.get(template.getId());
            template.setSiteIds(siteIds);
        });
        return templates;
    }
}
