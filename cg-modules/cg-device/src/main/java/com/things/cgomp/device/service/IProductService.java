package com.things.cgomp.device.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.device.domain.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.things.cgomp.device.dto.product.ProductDTO;

import java.util.List;

/**
 * <p>
 * 充电桩厂商表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
public interface IProductService extends IService<Product> {

    List<Product> selectProducts(
            ProductDTO productDTO
    );

    PageInfo<Product> selectProductsPage(ProductDTO productDTO);

    void editProduct(ProductDTO productDTO);

    Integer addProduct(ProductDTO productDTO);

    void deleteProduct(Long id);
}
