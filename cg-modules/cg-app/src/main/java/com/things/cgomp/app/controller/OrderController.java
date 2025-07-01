package com.things.cgomp.app.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.domain.dto.OrderPageDTO;
import com.things.cgomp.order.api.vo.OrderAppLogVO;
import com.things.cgomp.app.service.OrderService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.vo.OrderAppDetailVO;
import com.things.cgomp.order.api.vo.OrderAppVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private  OrderService orderService;

    /**
     * 分页订单查询
     */
    @GetMapping(value = "page", name = "订单分页列表")
    public R<PageInfo<OrderAppVO>> selectPage(
            OrderPageDTO pageDTO
    ) {
        PageInfo<OrderAppVO> page = orderService.selectPage(pageDTO);
        return R.ok(page);
    }

    /**
     * 订单详情
     */
    @GetMapping(value = "", name = "订单详情")
    public R<OrderAppDetailVO> selectOrderDetail(
            @RequestParam Long id
    ) {
        OrderAppDetailVO orderDetailVo = orderService.selectOrderDetail(id);
        return R.ok(orderDetailVo);
    }

    /**
     * 订单状态跟踪
     */
    @GetMapping(value = "log", name = "订单状态跟踪")
    public R<List<OrderAppLogVO>> selectOrderLogs(
            @RequestParam Long orderId
    ) {
        List<OrderAppLogVO> orderStatusVO = orderService.selectOrderLogs(orderId);
        return R.ok(orderStatusVO);
    }
}
