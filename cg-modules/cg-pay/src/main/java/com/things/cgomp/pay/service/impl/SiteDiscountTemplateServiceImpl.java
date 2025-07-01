package com.things.cgomp.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.enums.DelFlagEnum;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.pay.api.domain.SiteDiscountActivity;
import com.things.cgomp.pay.api.domain.SiteDiscountTemplate;
import com.things.cgomp.pay.dto.sitediscount.SiteDiscountTemplatePageDTO;
import com.things.cgomp.pay.enums.ErrorCodeConstants;
import com.things.cgomp.pay.mapper.SiteDiscountTemplateMapper;
import com.things.cgomp.pay.service.ISiteDiscountActivityService;
import com.things.cgomp.pay.service.ISiteDiscountTemplateService;
import com.things.cgomp.pay.vo.sitediscount.SiteDiscountTemplateVo;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 站点折扣模板表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-21
 */
@Service
public class SiteDiscountTemplateServiceImpl extends ServiceImpl<SiteDiscountTemplateMapper, SiteDiscountTemplate>
        implements ISiteDiscountTemplateService {

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public Map<Long, String> getTemplateNameMap(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new HashMap<>();
        }
        List<SiteDiscountTemplate> templates = baseMapper.selectBatchIds(ids);
        return templates.stream()
                .collect(Collectors.toMap(
                        SiteDiscountTemplate::getId,
                        SiteDiscountTemplate::getName,
                        (a,b)->a
                ));
    }

    @Override
    public void checkTemplates(
            List<Long> templateIds
    ) {
        List<SiteDiscountTemplate> templates = baseMapper.selectBatchIds(templateIds);
        if(templates.size() != templateIds.size()){
            throw new ServiceException(ErrorCodeConstants.DISCOUNT_TEMPLATE_DATA_INCORRECT);
        }

        if (isExistDeleted(templates)) {
            throw new ServiceException(ErrorCodeConstants.DISCOUNT_TEMPLATE_BEEN_REMOVE);
        }

        if(hasOverlap(templates)){
            throw new ServiceException(ErrorCodeConstants.DISCOUNT_TEMPLATE_TIME_INTERLEAVED);
        }
    }

    private boolean hasOverlap(
            List<SiteDiscountTemplate> templates
    ) {
        if (templates == null || templates.size() < 2) {
            return false;
        }

        templates.sort(Comparator.comparing(SiteDiscountTemplate::getStartTime));

        for (int i = 1; i < templates.size(); i++) {
            SiteDiscountTemplate prev = templates.get(i - 1);
            SiteDiscountTemplate curr = templates.get(i);
            if (prev.getEndTime().compareTo(curr.getStartTime()) > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistDeleted(
            List<SiteDiscountTemplate> templates
    ) {
        return templates.stream()
                .anyMatch(template -> DelFlagEnum.DELETE.getType().equals(template.getDelFlag()));
    }

    @Override
    public Long saveTemplate(
            SiteDiscountTemplate siteDiscountTemplate
    ) {
        siteDiscountTemplate.setOperatorId(SecurityUtils.getOperatorId())
                .setCreateBy(SecurityUtils.getUserId())
                .formatTime();

        baseMapper.insert(siteDiscountTemplate);
        return siteDiscountTemplate.getId();
    }

    @Override
    public SiteDiscountTemplate getTemplate(
            Long id
    ) {
        return baseMapper.selectById(id);
    }

    @Override
    public void editTemplate(SiteDiscountTemplate siteDiscountTemplate) {
        siteDiscountTemplate.setUpdateBy(SecurityUtils.getUserId())
                .formatTime();

        baseMapper.updateById(siteDiscountTemplate);
    }

    @Override
    public void deleteTemplate(
            Long id
    ) {
        ISiteDiscountActivityService activityService = applicationContext.getBean(ISiteDiscountActivityService.class);
        List<SiteDiscountActivity> activities = activityService.selectActivities(id);
        if(!activities.isEmpty()){
            throw new ServiceException(ErrorCodeConstants.TEMPLATE_HAS_BEEN_ASSOCIATED_ACTIVITY);
        }

        SiteDiscountTemplate template = new SiteDiscountTemplate()
                .setId(id)
                .setUpdateBy(SecurityUtils.getUserId())
                .setDelFlag(DelFlagEnum.DELETE.getType());
        baseMapper.updateById(template);
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<SiteDiscountTemplateVo> selectPage(
            SiteDiscountTemplatePageDTO pageDTO
    ) {
        try (Page<Object> ignored =
                     PageHelper.startPage(
                             pageDTO.getCurrent(),
                             pageDTO.getPageSize()
                     )
        ) {
            List<SiteDiscountTemplateVo> sites = baseMapper.selectTemplates(pageDTO);

            return new PageInfo<>(sites);
        }
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public List<SiteDiscountTemplateVo> selectTemplates(
            SiteDiscountTemplatePageDTO pageDTO
    ) {
        return baseMapper.selectTemplates(pageDTO);
    }

    @Override
    public Map<Long, SiteDiscountTemplate> getTemplateMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        List<SiteDiscountTemplate> templates = baseMapper.selectBatchIds(ids);
        return templates.stream()
                .collect(Collectors.toMap(
                        SiteDiscountTemplate::getId,
                        a -> a,
                        (a, b) -> a
                ));
    }
}
