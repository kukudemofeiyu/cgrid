package com.things.cgomp.order.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.order.service.impl.OrderSerialCodeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 订单序列号控制器
 *
 * @author things
 */
@Log(title = "订单序列号")
@RestController
@RequestMapping("/serialCode")
public class OrderCodeController {

    @Resource
    private OrderSerialCodeService orderSerialCodeService;

    /**
     * 内部接口 生成订单序列号
     * @param origin 订单来源  默认APP
     * @see com.things.cgomp.order.api.enums.ChargeOrigin
     * @return 订单号
     */
    @InnerAuth
    @PostMapping()
    public R<String> genSerialCode(@RequestParam(value = "origin", required = false) String origin){
        return R.ok(orderSerialCodeService.genSerialCode(null, null));
    }
}
