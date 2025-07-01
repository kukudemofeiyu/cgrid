package com.things.cgomp.order.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.order.service.IOrderStepService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author things
 */
@RestController
@RequestMapping("/orderStep")
public class OrderStepController {

    @Resource
    private IOrderStepService orderStepService;

    @InnerAuth
    @PostMapping(value = "/checkAndProcess", name = "检查未完成的订单步骤")
    public R<Boolean> checkAndProcessOrderStep(){
        return R.ok(orderStepService.checkAndProcessOrderStep());
    }
}
