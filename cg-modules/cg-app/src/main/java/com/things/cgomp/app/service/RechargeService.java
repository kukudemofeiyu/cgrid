package com.things.cgomp.app.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.api.domain.AppRechargeTrendData;
import com.things.cgomp.app.domain.dto.WithdrawOrRechargeDTO;
import com.things.cgomp.system.api.dto.AppRechargeRecordDTO;
import com.things.cgomp.system.api.dto.AppRefundRecordDTO;
import com.things.cgomp.system.api.vo.AppRechargeRecordVO;
import com.things.cgomp.system.api.vo.AppRefundRecordVO;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface RechargeService {

    PrepayWithRequestPaymentResponse wechatRecharge(String orderNo, WithdrawOrRechargeDTO rechargeDTO);


    Integer wechatOrderQuery(String orderNo);

    void withdraw(WithdrawOrRechargeDTO withdrawDTO);

    PageInfo<AppRefundRecordVO> selectRefundRecordList(AppRefundRecordDTO req);

    PageInfo<AppRechargeRecordVO> appRechargeRecordPage(AppRechargeRecordDTO req);

    Transaction OrderQueryByWechat(String orderNo);

    Boolean refund(String OrderNo);

    Refund refundQueryByWechat(String orderNo);
    /**
     * 支付成功
     */
    void handleWxpayCallback(String body, HttpServletRequest request);

    /**
     * 查询APP用户充值记录趋势数据
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return  List<AppRechargeTrendData>
     */
    List<AppRechargeTrendData> selectRechargeTrendData(String beginDate, String endDate);
}
