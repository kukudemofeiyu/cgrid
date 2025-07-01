package com.things.cgomp.order.api;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.constant.SecurityConstants;
import com.things.cgomp.common.core.constant.ServiceNameConstants;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.dto.*;
import com.things.cgomp.order.api.factory.RemoteOrderFallbackFactory;
import com.things.cgomp.order.api.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单服务
 *
 * @author things
 */
@FeignClient(contextId = "remoteOrderService", value = ServiceNameConstants.ORDER_SERVICE,
//        url = "http://localhost:9013",
        fallbackFactory = RemoteOrderFallbackFactory.class)
public interface RemoteOrderService {

    /**
     * 生成订单号
     *
     * @param origin 订单来源
     * @return 订单号
     * @see com.things.cgomp.order.api.enums.ChargeOrigin
     */
    @PostMapping(value = "/serialCode")
    R<String> genSerialCode(@RequestParam("origin") String origin, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @PostMapping(value = "/orderInfo/orderSn", name = "生成订单号")
    R<String> generateOrderSn (
            @RequestParam("portId") Long portId,
            @RequestParam("insertTime") Long insertTime
    );

    @GetMapping(value = "/orderInfo/appOrderDiscount", name = "获取app订单优惠")
    R<AppOrderDiscountVo> getAppOrderDiscount(
            @SpringQueryMap AppOrderDiscountDTO orderDiscountDTO
    );

    @GetMapping(value = "/orderInfo/orderSn", name = "查询订单号")
    R<String> selectOrderSn(
            @RequestParam("portId") Long portId
    );

    @GetMapping(value = "/orderInfo/ordersBySns", name = "查询订单列表")
    R<List<OrderInfo>> selectOrders(
            @RequestParam(value = "sns") List<String> sns
    );

    @GetMapping(value = "/orderInfo/order", name = "查询订单")
    R<OrderInfo> selectOrder(
            @RequestParam(value = "id") Long id
    );

    @PostMapping(value = "/orderInfo", name = "创建订单")
    R<Long> addOrder(
            @RequestBody AddOrderDTO addOrderDTO
    );

    @GetMapping(value = "/orderInfo", name = "订单详情")
    R<OrderDetailVo> selectOrderDetail(
            @RequestParam("id") Long id
    );

    @GetMapping(value = "/orderInfo/user/isCharging", name = "根据用户查询是否存在正在充电的订单")
    R<Boolean> isChargingOrderByUserId(
            @RequestParam("userId") Long userId,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );
    @GetMapping(value = "/orderInfo/user/isPaying", name = "根据用户查询是否存在待支付订单")
    R<Boolean> isPayingOrderByUserId(
            @RequestParam("userId") Long userId,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    @GetMapping(value = "/orderInfo/app/page", name = "APP订单分页列表")
    R<PageInfo<OrderAppVO>> selectAppPage(
            @RequestParam("userId") Long userId,
            @RequestParam("orderType") Integer orderType,
            @RequestParam("status") Integer status,
            @RequestParam("current") Integer current,
            @RequestParam("pageSize") Integer pageSize,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    @GetMapping(value = "/orderInfo/app/detail", name = "App订单详情")
    R<OrderAppDetailVO> selectAppOrderDetail(
            @RequestParam("id") Long id,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    @GetMapping(value = "/orderInfo/app/log", name = "app订单状态跟踪")
    R<List<OrderAppLogVO>> selectAppOrderLogs(
            @RequestParam("orderId") Long orderId,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source
    );

    @PutMapping(value = "/orderInfo/updatePayStatus", name = "修改支付状态")
    R<?> updatePayStatus(
            @RequestBody OrderPaySuccessDTO orderPaySuccessDTO
    );

    @PutMapping(value = "/orderInfo/payOrderId", name = "保存支付订单号")
    R<?> savePayOrderId(
            @RequestParam("orderIds") List<Long> orderIds,
            @RequestParam("payOrderId") String payOrderId
    );

    @PostMapping(value = "/orderInfo/payWalletMoney", name = "钱包支付")
    R<?> payWalletMoney(
            @RequestBody PayWalletMoneyDTO payWalletMoneyDTO
    );

    @GetMapping(value = "/orderInfo/nonPaymentOrderDiscount", name = "获取未付款订单优惠信息")
    R<OrderDiscountVo> getNonPaymentOrderDiscount(
            @RequestParam("orderIds") List<Long> orderIds,
            @RequestParam("siteActivityIds") List<Long> siteActivityIds,
            @RequestParam("couponIds") List<Long> couponIds
    );

    @GetMapping(value = "/orderInfo/getUnsettledOrder", name = "根据充电枪ID获取未结算完成的订单")
    R<UnSettledOrderInfo> getUnsettledOrder(@RequestParam("portId") Long portId,
                                            @RequestParam("orderSn") String orderSn,
                                            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping(value = "/orderInfo/getSubOrder" ,name = "根据订单号获取子订单")
     R<List<OrderInfo>> getSubOrder(@RequestParam("orderId") Long orderId,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    @GetMapping(value = "/orderInfo/getChargeOrderByTradeNo")
     R<OrderInfo> getChargeOrderByTradeNo(@RequestParam("tradeNo") String tradeNo,@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping(value = "/orderInfo/getChargeOrderByOrderNo", name = "根据订单号查询充电订单")
    R<OrderInfo> getChargeOrderByOrderNo(
            @RequestParam("orderNo") String orderNo,
            @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
