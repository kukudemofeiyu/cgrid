package com.things.cgomp.device.data.utils;

import com.things.cgomp.common.core.constant.OrderConstant;

/**
 * @author things
 */
public class HistoryDataUtil {

    public static String buildDt(String orderSn){
        if (orderSn == null || OrderConstant.ORDER_SN_LENTH != orderSn.length()) {
            return null;
        }
        return orderSn.substring(18, 26);
    }
}
