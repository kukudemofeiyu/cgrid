package com.things.cgomp.device.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.device.domain.ProductFactory;
import com.things.cgomp.device.dto.product.ProductFactoryDTO;

import java.util.List;

/**
 * <p>
 * 充电桩厂商表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
public interface ProductFactoryMapper extends BaseMapper<ProductFactory> {

    List<ProductFactory> selectProductFactorys(ProductFactoryDTO productFactoryDTO);
}
