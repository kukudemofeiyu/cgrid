package com.things.cgomp.order.api.factory;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.order.api.RemoteOrderService;
import com.things.cgomp.order.api.domain.OrderInfo;
import com.things.cgomp.order.api.dto.*;
import com.things.cgomp.order.api.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订单服务降级处理
 *
 * @author things
 */
@Slf4j
@Component
public class RemoteOrderFallbackFactory implements FallbackFactory<RemoteOrderService> {

    @Override
    public RemoteOrderService create(Throwable throwable) {
        log.error("订单服务调用失败:{}", throwable.getMessage());
        return new RemoteOrderService() {
            @Override
            public R<String> genSerialCode(String origin, String source) {
                return R.fail("生成订单号失败:" + throwable.getMessage());
            }

            @Override
            public R<String> generateOrderSn(Long portId, Long insertTime) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<AppOrderDiscountVo> getAppOrderDiscount(AppOrderDiscountDTO orderDiscountDTO) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<String> selectOrderSn(Long portId) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<List<OrderInfo>> selectOrders(List<String> sns) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<OrderInfo> selectOrder(Long id) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<Long> addOrder(AddOrderDTO addOrderDTO) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<OrderDetailVo> selectOrderDetail(Long id) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<Boolean> isChargingOrderByUserId(Long userId, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<Boolean> isPayingOrderByUserId(Long userId, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<PageInfo<OrderAppVO>> selectAppPage(Long userId,Integer orderType, Integer status, Integer current, Integer pageSize, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<OrderAppDetailVO> selectAppOrderDetail(Long id, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<List<OrderAppLogVO>> selectAppOrderLogs(Long orderId, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<?> updatePayStatus(
                    OrderPaySuccessDTO orderPaySuccessDTO
            ) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<?> savePayOrderId(
                    List<Long> orderIds,
                    String payOrderId
            ) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<UnSettledOrderInfo> getUnsettledOrder(Long portId, String orderSn, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<List<OrderInfo>> getSubOrder(Long orderId, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<OrderInfo> getChargeOrderByTradeNo(String tradeNo, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<OrderInfo> getChargeOrderByOrderNo(String tradeNo, String source) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<OrderDiscountVo> getNonPaymentOrderDiscount(
                    List<Long> orderIds,
                    List<Long> siteActivityIds,
                    List<Long> couponIds
            ) {
                return R.fail(throwable.getMessage());
            }

            @Override
            public R<?> payWalletMoney(PayWalletMoneyDTO payWalletMoneyDTO) {
                return R.fail(throwable.getMessage());
            }
        };
    }
}
