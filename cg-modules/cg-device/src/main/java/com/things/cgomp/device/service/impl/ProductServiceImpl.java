package com.things.cgomp.device.service.impl;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.datascope.annotation.DataScope;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.device.domain.Product;
import com.things.cgomp.device.dto.product.ProductDTO;
import com.things.cgomp.device.mapper.ProductMapper;
import com.things.cgomp.device.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.things.cgomp.common.core.utils.PageUtils.startPage;
import static com.things.cgomp.device.enums.ErrorCodeConstants.*;

/**
 * <p>
 * 充电桩厂商表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public List<Product> selectProducts(ProductDTO productDTO) {
        return baseMapper.selectProducts(productDTO);
    }

    @Override
    @DataScope(orgAlias = "op", userOperatorAlias = "uop")
    public PageInfo<Product> selectProductsPage(ProductDTO productDTO) {
        startPage();
        List<Product> products = baseMapper.selectProducts(productDTO);
        return PageInfo.of(products);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void editProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setFactoryId(productDTO.getFactoryId());
        product.setRemark(productDTO.getRemark());
        product.setOperatorId(productDTO.getOperatorId());
        product.setModel(productDTO.getModel());
        baseMapper.updateById(product);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer addProduct(ProductDTO productDTO) {
        Long operatorId = productDTO.getOperatorId();
        if(operatorId == null){
            throw new ServiceException(ADD_PRODUCT_OPERATORID_NOT_NULL);
        }

        Product product = new Product();
        product.setModel(productDTO.getModel());
        product.setFactoryId(productDTO.getFactoryId());
        product.setRemark(productDTO.getRemark());
        product.setCreateBy(SecurityUtils.getUserId());
        product.setCreateTime(LocalDateTime.now());
        product.setOperatorId(operatorId);
        Integer insertId = baseMapper.insert(product);
        return insertId;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteProduct(Long id) {
        Product product = baseMapper.selectById(id);
        if(product.getOperatorId() == null){
            throw new ServiceException(DEFAULT_PRODUCT_NOT_DELETE);
        }

        baseMapper.deleteById(id);
    }
}
