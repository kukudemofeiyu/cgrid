package com.things.cgomp.devicescale.controller;

import cn.hutool.core.util.HexUtil;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.devicescale.domain.dto.DeviceInfo;
import com.things.cgomp.devicescale.service.DeviceScaleService;
import com.things.cgomp.devicescale.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scale")
@Slf4j
public class ScaleDeviceController extends BaseController {

    @Autowired
    private DeviceScaleService deviceScaleService;

    @GetMapping("/list")
    public R<List<DeviceInfo>> list(DeviceInfo device) {
        startPage();
        List<DeviceInfo> appBanners = deviceScaleService.getList(device);
        return R.ok(appBanners);
    }

    /**
     * @param id   id 桩ID 或 枪ID
     *             type为1时,id为桩ID
     * @param type 0 停止模拟桩
     *             1 启动模拟桩
     *             2 插抢
     *             3 枪主动停止充电
     *             4 拔枪
     * @return
     */
    @GetMapping("/opt/{id}")
    public R<List<DeviceInfo>> list(@PathVariable Long id, Integer type) throws Exception {

        if (type == null) {
            return R.fail(1000, "type不能为空");
        }
        DeviceInfo device = deviceScaleService.getById(id);
        if (device == null) {
            if (type == 1 || type == 0) {
                return R.fail(1000, "桩信息不存在");
            } else {
                return R.fail(1000, "枪信息不存在");
            }

        }
        deviceScaleService.optDevice(device, type);
        return R.ok();
    }


}
