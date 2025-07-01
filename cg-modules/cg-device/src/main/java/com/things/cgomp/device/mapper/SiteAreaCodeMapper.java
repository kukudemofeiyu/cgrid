package com.things.cgomp.device.mapper;

import com.things.cgomp.device.domain.SiteAreaCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.device.vo.AreaTreeNode;

import java.util.List;

/**
 * <p>
 * 地区编码表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
public interface SiteAreaCodeMapper extends BaseMapper<SiteAreaCode> {

    List<AreaTreeNode> getAreaCodes();

}
