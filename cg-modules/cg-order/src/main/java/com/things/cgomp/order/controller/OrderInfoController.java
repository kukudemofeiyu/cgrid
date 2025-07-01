package com.things.cgomp.order.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.mq.message.OrderPaySuccessReqMsg;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.order.api.dto.*;
import com.things.cgomp.order.api.vo.*;
import com.things.cgomp.order.convert.OrderInfoConvert;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.dto.OrderAppPageDTO;
import com.things.cgomp.order.dto.OrderPageDTO;
import com.things.cgomp.order.dto.OrderProcessReqDTO;
import com.things.cgomp.order.enums.ErrorCodeConstants;
import com.things.cgomp.order.service.IOrderInfoService;
import com.things.cgomp.order.service.IOrderStepService;
import com.things.cgomp.order.vo.OrderVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.things.cgomp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * <p>
 * 充电订单表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-03-03
 */
@RestController
@RequestMapping("/orderInfo")
public class OrderInfoController extends BaseController {

    @Resource
    private IOrderInfoService orderInfoService;
    @Resource
    private IOrderStepService orderStepService;

    @RequiresPermissions("order:info:list")
    @GetMapping(value = "page", name = "订单分页列表")
    public R<PageInfo<OrderVo>> selectPage(
            OrderPageDTO pageDTO
    ) {
        PageInfo<OrderVo> page = orderInfoService.selectPage(pageDTO);
        return R.ok(page);
    }
    @InnerAuth
    @GetMapping(value = "app/page", name = "APP订单分页列表")
    public R<PageInfo<OrderAppVO>> selectAppPage(
            OrderAppPageDTO pageDTO
    ) {
        PageInfo<OrderAppVO> page = orderInfoService.selectAppPage(pageDTO);
        return R.ok(page);
    }

    @PostMapping(value = "orderSn", name = "生成订单号")
    public R<String> generateOrderSn(
            @RequestParam("portId") Long portId,
            @RequestParam("insertTime") Long insertTime
    ) {
        String sn = orderInfoService.generateSn(
                portId,
                insertTime
        );
        return R.ok(sn);
    }

    @GetMapping(value = "orderSn", name = "查询订单号")
    public R<String> selectOrderSn(
            @RequestParam("portId") Long portId
    ) {
        String sn = orderInfoService.selectOrderSn(
                portId
        );
        return R.ok(sn);
    }

    @GetMapping(value = "ordersBySns", name = "查询订单列表")
    public R<List<OrderInfo>> selectOrders(
            @RequestParam(value = "sns") List<String> sns
    ) {
        List<OrderInfo> orderInfos = orderInfoService.selectOrdersBySns(
                sns
        );
        return R.ok(orderInfos);
    }

    @GetMapping(value = "order", name = "查询订单")
    public R<OrderInfo> selectOrders(
            @RequestParam(value = "id") Long id
    ) {
        OrderInfo orderInfo = orderInfoService.getById(
                id
        );
        return R.ok(orderInfo);
    }

    @RequiresPermissions(value = {
            "order:info:abnormal:detail",
            "order:info:refund:detail",
            "order:info:history:detail",
            "order:info:realtime:detail",
            "order:info:seat:detail",
    })
    @GetMapping(value = "", name = "订单详情")
    public R<OrderDetailVo> selectOrderDetail(
          @RequestParam Long id
    ) {
        OrderDetailVo orderDetailVo = orderInfoService.selectOrderDetail(id);
        return R.ok(orderDetailVo);
    }
    @InnerAuth
    @GetMapping(value = "app/detail", name = "App订单详情")
    public R<OrderAppDetailVO> selectAppOrderDetail(
          @RequestParam Long id
    ) {
        OrderAppDetailVO orderDetailVo = orderInfoService.selectAppOrderDetail(id);
        return R.ok(orderDetailVo);
    }

    @PostMapping(value = "", name = "创建订单")
    public R<Long> addOrder(
            @RequestBody AddOrderDTO addOrderDTO
    ) {
        Long orderId = orderInfoService.addOrder(addOrderDTO);
        return R.ok(orderId);
    }

    @PutMapping(value = "updatePayStatus", name = "修改支付状态")
    public R<?> updatePayStatus(
            @RequestBody OrderPaySuccessReqMsg orderPaySuccessReqMsg
    ) {
        orderInfoService.updateOrderPayStatus(
                orderPaySuccessReqMsg
        );
        return R.ok();
    }

    @PutMapping(value = "payOrderId", name = "保存支付订单号")
    public R<?> savePayOrderId(
            @RequestParam("orderIds") List<Long> orderIds,
            @RequestParam("payOrderId") String payOrderId
    ) {
        orderInfoService.savePayOrderId(
                orderIds,
                payOrderId
        );
        return R.ok();
    }

    @PostMapping(value = "payWalletMoney", name = "钱包支付")
    public R<?> payWalletMoney(
           @Validated @RequestBody PayWalletMoneyDTO payWalletMoneyDTO
    ) {
        orderInfoService.payWalletMoney(
                payWalletMoneyDTO
        );
        return R.ok();
    }

    @GetMapping(value = "nonPaymentOrderDiscount", name = "获取未付款订单优惠信息")
    public R<OrderDiscountVo> getNonPaymentOrderDiscount(
           @Validated OrderDiscountDTO orderDiscountDTO
    ) {
        OrderDiscountVo orderDiscount = orderInfoService.getOrderDiscount(
                orderDiscountDTO
        );
        return R.ok(orderDiscount);
    }

    @GetMapping(value = "appOrderDiscount", name = "获取app订单优惠")
    public R<AppOrderDiscountVo> getAppOrderDiscount(
            AppOrderDiscountDTO orderDiscountDTO
    ) {
        AppOrderDiscountVo orderDiscount = orderInfoService.getAppOrderDiscount(
                orderDiscountDTO
        );
        return R.ok(orderDiscount);
    }

    /**
     * 根据用户查询是否存在正在充电的订单
     */
    @InnerAuth
    @GetMapping(value = "user/isCharging", name = "根据用户查询是否存在正在充电的订单")
    public R<Boolean> isChargingOrderByUserId(
            @RequestParam Long userId
    ) {
        Boolean is = orderInfoService.isChargingOrderByUserId(userId);
        return R.ok(is);
    }
    /**
     * 根据用户查询是否存在待支付订单
     */
    @InnerAuth
    @GetMapping(value = "user/isPaying", name = "根据用户查询是否存在待支付订单")
    public R<Boolean> isPayingOrderByUserId(
            @RequestParam Long userId
    ) {
        Boolean is = orderInfoService.isPayingOrderByUserId(userId);
        return R.ok(is);
    }
    /**
     * 根据订单号查询订单日志
     */
    @InnerAuth
    @GetMapping(value = "app/log", name = "app订单状态跟踪")
    public R<List<OrderAppLogVO>> selectAppOrderLogs(
            @RequestParam Long orderId
    ) {
        List<OrderAppLogVO> orderLogs = orderInfoService.selectAppOrderLogs(orderId);
        return R.ok(orderLogs);
    }

    /**
     * 根据充电枪和订单号查询未结算的订单
     */
    @InnerAuth
    @GetMapping(value = "/getUnsettledOrder")
    public R<UnSettledOrderInfo> getUnsettledOrder(@RequestParam("portId") Long portId,
                                                   @RequestParam("orderSn") String orderSn){
        OrderInfo orderInfo = orderInfoService.selectUnsettledOrder(portId, orderSn);
        return R.ok(OrderInfoConvert.INSTANCE.convertUnSettled(orderInfo));
    }
    /**
     * 根据充电订单获取子订单
     */
    @InnerAuth
    @GetMapping(value = "/getSubOrder")
    public R<List<OrderInfo>> getSubOrder(@RequestParam("orderId") Long orderId){
        List<OrderInfo> orderInfos = orderInfoService.selectSubOrder(orderId);
        return R.ok(orderInfos);
    }
    /**
     * 根据交易流水号查询充电订单
     */
    @InnerAuth
    @GetMapping(value = "/getChargeOrderByTradeNo")
    public R<OrderInfo> getChargeOrderByTradeNo(@RequestParam("tradeNo") String tradeNo){
        OrderInfo orderInfo = orderInfoService.selectChargeOrderByTradeNo(tradeNo);
        return R.ok(orderInfo);
    }

    /**
     * 根据订单号查询充电订单
     */
    @InnerAuth
    @GetMapping(value = "/getChargeOrderByOrderNo")
    public R<OrderInfo> getChargeOrderByOrderNo(@RequestParam("orderNo") String orderNo){
        OrderInfo orderInfo = orderInfoService.selectChargeOrderByOrderNo(orderNo);
        return R.ok(orderInfo);
    }

    @RequiresPermissions("order:info:process")
    @PostMapping(value = "/process", name = "处理订单流程")
    public R<Long> processOrderStep(@Validated @RequestBody OrderProcessReqDTO reqDTO){
        boolean success = orderStepService.checkAndProcessOrderStep(reqDTO.getOrderId());
        if (!success) {
            throw exception(ErrorCodeConstants.ORDER_PROCESS_FAIL);
        }
        return R.ok(reqDTO.getOrderId());
    }
}
