package com.things.cgomp.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.device.domain.Product;
import com.things.cgomp.device.domain.ProductFactory;
import com.things.cgomp.device.dto.product.ProductDTO;
import com.things.cgomp.device.dto.product.ProductFactoryDTO;
import com.things.cgomp.device.mapper.ProductFactoryMapper;
import com.things.cgomp.device.mapper.ProductMapper;
import com.things.cgomp.device.service.IProductFactoryService;
import com.things.cgomp.device.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;

/**
 * <p>
 * 充电桩厂商表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
@Service
public class ProductFactoryServiceImpl extends ServiceImpl<ProductFactoryMapper, ProductFactory> implements IProductFactoryService {

    @Autowired
    private ProductFactoryMapper productFactoryMapper;

    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    @Override
    public List<ProductFactory> selectProductFactorys(ProductFactoryDTO productFactoryDTO) {
        return productFactoryMapper.selectProductFactorys(productFactoryDTO);
    }
}
