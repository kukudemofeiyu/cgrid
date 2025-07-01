package com.things.cgomp.app.controller;

import com.things.cgomp.app.domain.dto.OrderPayDTO;
import com.things.cgomp.app.service.PayService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.dto.AppOrderDiscountDTO;
import com.things.cgomp.order.api.dto.PayWalletMoneyDTO;
import com.things.cgomp.order.api.vo.AppOrderDiscountVo;
import com.things.cgomp.pay.api.RemoteCouponService;
import com.things.cgomp.pay.api.vo.DiscountCouponVo;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/pay")
public class PayController {
    @Resource
    private PayService payService;
    @Resource
    private RemoteCouponService remoteCouponService;
    @Resource
    private RemoteOrderService remoteOrderService;

    /**
     * 微信支付
     *
     * @return 预支付订单信息
     */
    @PostMapping("")
    public R<?> Pay(@RequestBody OrderPayDTO orderPayDTO) {
        Object info = payService.Pay(orderPayDTO);
        return R.ok(info);
    }
    /**
     * 回调接口
     * @param body
     * @return
     */
    @PostMapping("/wxpay/callback")
    public R wxpayCallback(@RequestBody String body, HttpServletRequest request) {
        payService.handleWxpayCallback(body,request);
        return R.ok();
    }
    /**
     * 支付订单查询接口
     */
    @GetMapping("/order")
    public R<Integer> wechatOrderQuery(@RequestParam String orderNo) {
        Integer info = payService.wechatOrderQuery(orderNo);
        return R.ok(info);
    }
    /**
     * 钱包支付
     */
    @PostMapping("/wallet")
    public R walletPay(@RequestBody PayWalletMoneyDTO orderPayDTO) {
        return  payService.walletPay(orderPayDTO);
    }

    @GetMapping(value = "/coupon/siteActivityAndCoupons", name = "查询站点活动和优惠券")
    public R<List<DiscountCouponVo>> selectSiteActivityAndCoupons(
            @RequestParam(value = "orderId") Long orderId
    ) {
        return remoteCouponService.selectSiteActivityAndCoupons(
                orderId
        );
    }

    @GetMapping(value = "/orderInfo/appOrderDiscount", name = "获取app订单优惠")
    R<AppOrderDiscountVo> getAppOrderDiscount(
            @SpringQueryMap AppOrderDiscountDTO orderDiscountDTO
    ){
        return remoteOrderService.getAppOrderDiscount(orderDiscountDTO);
    }

    /**
     * 微信退款接口
     */
    @PostMapping("/refund")
    public R<Boolean> refund(@RequestParam String OrderNo) {
        Boolean info = payService.refund(OrderNo);
        return R.ok(info);
    }

}
