package com.things.cgomp.app.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.domain.dto.WithdrawOrRechargeDTO;
import com.things.cgomp.app.service.RechargeService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.security.utils.SecurityUtils;
import com.things.cgomp.system.api.dto.AppRechargeRecordDTO;
import com.things.cgomp.system.api.dto.AppRefundRecordDTO;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/recharge")
public class RechargeController {
    @Resource
    private RechargeService rechargeService;

    /**
     * 微信充值
     *
     * @return 预支付订单信息
     */
    @PostMapping("/wechat")
    public R<PrepayWithRequestPaymentResponse> wechatRecharge(@RequestBody WithdrawOrRechargeDTO rechargeDTO) {
        String orderNo = generateOrderNo(); // 生成唯一订单号
        PrepayWithRequestPaymentResponse info = rechargeService.wechatRecharge(orderNo, rechargeDTO);
        return R.ok(info);
    }
    /**
     * 回调接口
     * @param body
     * @return
     */
    @PostMapping("/wxpay/callback")
    public R wxpayCallback(@RequestBody String body, HttpServletRequest request) {
        rechargeService.handleWxpayCallback(body,request);
        return R.ok();
    }
    /**
     * 支付订单查询接口
     */
    @GetMapping("/order")
    public R<Integer> wechatOrderQuery(@RequestParam String orderNo) {
        Integer info = rechargeService.wechatOrderQuery(orderNo);
        return R.ok(info);
    }
    /**
     * 提现接口
     */
    @PostMapping("/withdraw")
    public R withdraw(@RequestBody WithdrawOrRechargeDTO withdrawDTO) {
        rechargeService.withdraw(withdrawDTO);
        return R.ok();
    }
    /**
     * app提现记录分页列表
     */
    @GetMapping("/refundRecord/page")
    public R<PageInfo<AppRefundRecordVO>> refundPage(AppRefundRecordDTO req) {
        PageInfo<AppRefundRecordVO> recordList = rechargeService.selectRefundRecordList(req);
        return R.ok(recordList);
    }
    /**
     * app账单列表分页
     */
    @GetMapping("/appRechargeRecord/page")
    public R<PageInfo<AppRechargeRecordVO>> appRechargeRecordPage(AppRechargeRecordDTO req) {
        PageInfo<AppRechargeRecordVO> recordList = rechargeService.appRechargeRecordPage(req);
        return R.ok(recordList);
    }
    /**
     * 从微信支付平台查询订单 根据商户订单号
     */
    @GetMapping("/queryWxOrder")
    public R<Transaction> queryOrder(String orderNo) {
        Transaction info = rechargeService.OrderQueryByWechat(orderNo);
        return R.ok(info);
    }
    /**
     * 微信退款接口
     */
    @PostMapping("/refund")
    public R<Boolean> refund(@RequestParam String OrderNo) {
        Boolean info = rechargeService.refund(OrderNo);
        return R.ok(info);
    }
    /**
     * 查询微信退款订单
     */
    @GetMapping("/refund/query")
    public R<Refund> queryRefund(@RequestParam String OrderNo) {
        Refund info = rechargeService.refundQueryByWechat(OrderNo);
        return R.ok(info);
    }

    /**
     * 生成唯一订单号
     */
    private String generateOrderNo() {
        Long userId = SecurityUtils.getUserId();
//          Long userId = 1l;
        // 格式化用户ID为11位，不足部分前面补零
        String formattedUserId = String.format("%011d", userId);
        // 生成随机数（0到999）
        int randomNum = (int) (Math.random() * 1000);
        // 拼接订单号
        return "RC_WX" + formattedUserId + System.currentTimeMillis()  + randomNum;
    }

}
