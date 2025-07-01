package com.things.cgomp.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.device.domain.Product;
import com.things.cgomp.device.domain.ProductFactory;
import com.things.cgomp.device.dto.product.ProductDTO;
import com.things.cgomp.device.dto.product.ProductFactoryDTO;

import java.util.List;

/**
 * <p>
 * 充电桩厂商表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
public interface IProductFactoryService extends IService<ProductFactory> {

    List<ProductFactory> selectProductFactorys(ProductFactoryDTO productFactoryDTO);
}
