package com.things.cgomp.device.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.device.pojo.device.DeviceDTO;
import com.things.cgomp.common.device.pojo.device.DevicePortDTO;
import com.things.cgomp.common.device.pojo.device.DevicePortVo;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.api.model.vo.ChargingAppPortDetailVO;
import com.things.cgomp.device.dto.device.ChargePortDTO;
import com.things.cgomp.device.service.IDeviceInfoService;
import com.things.cgomp.device.vo.device.DevicePortEnergyDataVO;
import com.things.cgomp.device.vo.device.DevicePortRealDataVO;
import com.things.cgomp.device.vo.device.DevicePortTrendDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 充电桩设备表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@RestController
@RequestMapping("/devicePort")
public class ChargePortController extends BaseController {

    @Autowired
    private IDeviceInfoService deviceInfoService;

    @PostMapping(value = "insertGun",name = "插枪")
    public R<String> insertGun(
            @RequestParam("portId") Long portId,
            @RequestParam("insertTime") Long insertTime,
            @RequestParam("vin") String vin
    ) {
        String orderSn = deviceInfoService.insertGun(
                portId,
                insertTime,
                vin
        );

        return R.ok(orderSn);
    }

    @RequiresPermissions("device:port:list")
    @GetMapping(value = "/page", name = "查看端口列表")
    public R<PageInfo<DevicePortVo>> getChargePage(
            DevicePortDTO deviceListDTO
    ) {
        startPage();
        PageInfo<DevicePortVo> page = deviceInfoService.selectPortPage(deviceListDTO);
        return R.ok(page);
    }

    @RequiresPermissions("device:port:add")
    @PostMapping( name = "新增枪口")
    public R<Long> addChargePort(
            @RequestBody ChargePortDTO addChargePort) {
        Long deviceId = deviceInfoService.addChargePort(addChargePort);
        return R.ok(deviceId);
    }

    @RequiresPermissions("device:port:edit")
    @PutMapping( name = "编辑枪口")
    public R<?> editChargePort(
            @RequestBody ChargePortDTO chargePortDTO
    ) {
        deviceInfoService.editChargePort(chargePortDTO);
        return R.ok();
    }

    @RequiresPermissions("device:port:remove")
    @DeleteMapping( name = "删除枪口")
    public R<?> deleteChargePort(
            @RequestBody ChargePortDTO chargePortDTO
    ) {
        deviceInfoService.deleteChargePort(chargePortDTO.getDeviceId());
        return R.ok();
    }

    @RequiresPermissions("device:port:query")
    @GetMapping(name = "查看枪口详情")
    public R<ChargePortDTO> getChargePort(
            @RequestParam Long deviceId
    ) {
        ChargePortDTO chargePort = deviceInfoService.getChargePort(deviceId);
        return R.ok(chargePort);
    }
    @InnerAuth
    @GetMapping(value = "/appPort", name = "查看app枪口带桩带站点详情")
    public R<ChargingAppPortDetailVO> getAppChargePort(
            @RequestParam(value = "portId") Long portId) {
        ChargingAppPortDetailVO chargePortDetailDTO = deviceInfoService.getAppChargePort(portId);
        return R.ok(chargePortDetailDTO);
    }
    /**
     * 根据桩编码查找站点详情
     */
    @InnerAuth
    @GetMapping(value = "/appPortBySn", name = "查看app枪口带桩带站点详情-根据桩编码")
    public R<ChargingAppPortDetailVO> getAppChargePortBySn(
            @RequestParam(value = "portSn") String portSn) {
        ChargingAppPortDetailVO chargePortDetailDTO = deviceInfoService.getAppChargePortBySn(portSn);
        return R.ok(chargePortDetailDTO);
    }

    @RequiresPermissions(value = {
            "device:grid:detail",
            "device:port:detail"
    })
    @GetMapping(value = "/detail", name = "充电枪完整的详情信息")
    public R<DeviceDTO> getSpecificDetail(@RequestParam("deviceId") Long deviceId){
        return R.ok(deviceInfoService.selectDevicePortSpecificDetail(deviceId));
    }

    @RequiresPermissions(value = {
            "device:grid:detail:realData",
            "device:port:detail:realData"
    })
    @GetMapping(value = "/realData", name = "查询充电枪实时监测数据")
    public R<DevicePortRealDataVO> getRealData(@RequestParam("deviceId") Long deviceId){
        return R.ok(deviceInfoService.selectDevicePortRealData(deviceId));
    }

    @RequiresPermissions(value = {
            "device:grid:detail:energyData",
            "device:port:detail:energyData"
    })
    @GetMapping(value = "/energyData", name = "查询充电枪电能数据")
    public R<DevicePortEnergyDataVO> getEnergyData(@RequestParam("deviceId") Long deviceId){
        return R.ok(deviceInfoService.selectDevicePortEnergyData(deviceId));
    }

    @RequiresPermissions(value = {
            "device:grid:detail:trendData",
            "device:port:detail:trendData"
    })
    @GetMapping(value = "/trendData", name = "查询充电枪趋势曲线图数据")
    public R<DevicePortTrendDataVO> getTrendData(@RequestParam("deviceId") Long deviceId){
        return R.ok(deviceInfoService.selectDevicePortTrendData(deviceId));
    }
}
