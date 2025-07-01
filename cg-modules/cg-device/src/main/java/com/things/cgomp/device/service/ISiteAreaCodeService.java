package com.things.cgomp.device.service;

import com.things.cgomp.device.domain.SiteAreaCode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.device.vo.AreaTreeNode;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 地区编码表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
public interface ISiteAreaCodeService extends IService<SiteAreaCode> {

    List<AreaTreeNode> getAreaCodes();

    Map<Long,String> getNameMap(List<Long> ids);

}
