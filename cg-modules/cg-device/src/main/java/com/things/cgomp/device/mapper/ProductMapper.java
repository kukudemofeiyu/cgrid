package com.things.cgomp.device.mapper;

import com.things.cgomp.device.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.device.dto.product.ProductDTO;

import java.util.List;

/**
 * <p>
 * 充电桩厂商表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
public interface ProductMapper extends BaseMapper<Product> {

    List<Product> selectProducts(ProductDTO productDTO);

}
