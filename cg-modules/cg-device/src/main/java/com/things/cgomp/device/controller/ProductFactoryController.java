package com.things.cgomp.device.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.domain.Product;
import com.things.cgomp.device.domain.ProductFactory;
import com.things.cgomp.device.dto.product.ProductDTO;
import com.things.cgomp.device.dto.product.ProductFactoryDTO;
import com.things.cgomp.device.service.IProductFactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productFactory")
public class ProductFactoryController {

    @Autowired
    private IProductFactoryService productFactoryService;

    @RequiresPermissions("device:productFactory:list")
    @GetMapping(value = "list", name = "设备厂商列表")
    public R<List<ProductFactory>> selectProductFactory(
            ProductFactoryDTO productFactoryDTO
    ) {
        List<ProductFactory> products = productFactoryService.selectProductFactorys(productFactoryDTO);
        return R.ok(products);
    }
}
