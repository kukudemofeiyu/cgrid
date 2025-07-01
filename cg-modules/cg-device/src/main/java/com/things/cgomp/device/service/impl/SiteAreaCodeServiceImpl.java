package com.things.cgomp.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.things.cgomp.device.domain.SiteAreaCode;
import com.things.cgomp.device.mapper.SiteAreaCodeMapper;
import com.things.cgomp.device.service.ISiteAreaCodeService;
import com.things.cgomp.device.service.impl.sitearea.SiteAreaTreeBuild;
import com.things.cgomp.device.vo.AreaTreeNode;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 地区编码表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Service
public class SiteAreaCodeServiceImpl extends ServiceImpl<SiteAreaCodeMapper, SiteAreaCode> implements ISiteAreaCodeService {

    @Override
    public List<AreaTreeNode> getAreaCodes() {
        List<AreaTreeNode> treeNodeList = baseMapper.getAreaCodes();
        // 创建树形结构（数据集合作为参数）
        SiteAreaTreeBuild treeBuild = new SiteAreaTreeBuild(treeNodeList);
        // 原查询结果转换树形结构
        treeNodeList = treeBuild.buildTree();
        return treeNodeList;
    }

    @Override
    public Map<Long, String> getNameMap(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            return new HashMap<>();
        }

        List<SiteAreaCode> siteAreaCodes = baseMapper.selectBatchIds(ids);

       return siteAreaCodes.stream()
                .collect(Collectors.toMap(
                        SiteAreaCode::getId,
                        SiteAreaCode::getName,
                        (a,b)->a
                ));
    }
}
