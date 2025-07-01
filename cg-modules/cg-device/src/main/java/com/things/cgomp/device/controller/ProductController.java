package com.things.cgomp.device.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.device.pojo.device.DeviceGridVo;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.domain.Product;
import com.things.cgomp.device.dto.product.ProductDTO;
import com.things.cgomp.device.service.IProductService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 充电桩厂商表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-06
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Resource
    private IProductService productService;

    @GetMapping(value = "list", name = "产品列表")
    public R<List<Product>> selectProducts(
            ProductDTO productDTO
    ) {
        List<Product> products = productService.selectProducts(productDTO);
        return R.ok(products);
    }

    @RequiresPermissions("device:product:list")
    @GetMapping(value = "page", name = "产品列表(分页)")
    public R<PageInfo<Product>> selectProductsPage(
            ProductDTO productDTO
    ) {
        PageInfo<Product> products = productService.selectProductsPage(productDTO);
        return R.ok(products);
    }

    @RequiresPermissions("device:product:add")
    @PostMapping(name = "新增产品")
    public R<Integer> addProduct(
            @RequestBody ProductDTO productDTO
    ) {
        Integer id = productService.addProduct(productDTO);
        return R.ok(id);
    }

    @RequiresPermissions("device:product:edit")
    @PutMapping(name = "编辑产品")
    public R<?> editProduct(
            @RequestBody ProductDTO productDTO
    ) {
        productService.editProduct(productDTO);
        return R.ok();
    }

    @RequiresPermissions("device:product:delete")
    @DeleteMapping(name = "删除产品")
    public R<?> deleteProduct(
            @RequestParam(value = "id") Long id
    ) {
        productService.deleteProduct(id);
        return R.ok();
    }

}
