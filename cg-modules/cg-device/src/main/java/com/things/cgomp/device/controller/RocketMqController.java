package com.things.cgomp.device.controller;

import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.log.annotation.Log;
import com.things.cgomp.device.dto.rocket.test.CompleteChargingMqTestDTO;
import com.things.cgomp.device.dto.rocket.test.DrawGunMqTestDTO;
import com.things.cgomp.device.dto.rocket.test.EndChargingMqTestDTO;
import com.things.cgomp.device.dto.rocket.test.OrderPaySuccessTestDTO;
import com.things.cgomp.device.service.RocketMqTestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 站点表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Log(title = "rocketMq")
@RestController
@RequestMapping("/rocketMq")
public class RocketMqController {

    @Resource
    private RocketMqTestService rocketMqTestService;

    @PostMapping(value = "endCharging", name = "结束充电")
    public R<?> sendTestMsg(
            @RequestBody EndChargingMqTestDTO rocketMqTestDTO
    ) {
        rocketMqTestService.syncSend(rocketMqTestDTO);
        return R.ok();
    }

    @PostMapping(value = "tradingRecordConfirm", name = "确认交易记录")
    public R<?> confirmTradingRecord(
            @RequestBody CompleteChargingMqTestDTO rocketMqTestDTO
    ) {
        rocketMqTestService.syncSend(rocketMqTestDTO);
        return R.ok();
    }

    @PostMapping(value = "drawGun", name = "拔枪")
    public R<?> drawGun(
            @RequestBody DrawGunMqTestDTO rocketMqTestDTO
    ) {
        rocketMqTestService.syncSend(rocketMqTestDTO);
        return R.ok();
    }

    @PostMapping(value = "updateOrderPayStatus", name = "更改支付状态")
    public R<?> updateOrderPayStatus(
            @RequestBody OrderPaySuccessTestDTO rocketMqTestDTO
    ) {
        rocketMqTestService.syncSend(rocketMqTestDTO);
        return R.ok();
    }

}
