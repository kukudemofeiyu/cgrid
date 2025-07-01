package com.things.cgomp.device.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.web.controller.BaseController;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.enums.ComponentEnum;
import com.things.cgomp.common.device.pojo.device.*;
import com.things.cgomp.common.security.annotation.InnerAuth;
import com.things.cgomp.common.security.annotation.RequiresPermissions;
import com.things.cgomp.device.api.dto.UpdateChargeGridRulesDTO;
import com.things.cgomp.device.dto.device.ChargeGridDTO;
import com.things.cgomp.device.dto.device.UpdateChargeGridDTO;
import com.things.cgomp.device.dto.device.UpdateChargeGridRuleDTO;
import com.things.cgomp.device.dto.statistics.ChargeGridStatisticsTotalDataResp;
import com.things.cgomp.device.service.IDeviceInfoService;
import com.things.cgomp.device.service.IDeviceStatisticsService;
import com.things.cgomp.device.vo.device.ChargeGridVo;
import com.things.cgomp.device.vo.device.DeviceGridDetailVO;
import com.things.cgomp.device.vo.device.DeviceSimpleTreeVO;
import com.things.cgomp.order.api.RemoteOrderStatisticsService;
import com.things.cgomp.order.api.domain.DeviceOrderStatisticsData;
import com.things.cgomp.order.api.dto.OrderStatisticsReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 充电桩设备表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2025-02-28
 */
@Slf4j
@RestController
@RequestMapping("/deviceGrid")
public class ChargeGridController extends BaseController {

    @Autowired
    private IDeviceInfoService deviceInfoService;

    @Autowired
    private RemoteOrderStatisticsService  orderStatisticsService;
    @Resource
    private IDeviceStatisticsService deviceStatisticsService;

    @GetMapping(value = "/deviceMap", name = "查询设备map")
    public R<Map<Long,DeviceInfo>> selectDeviceMap(
           @RequestParam List<Long> deviceIds
    ) {
        Map<Long,DeviceInfo> deviceMap = deviceInfoService.selectDeviceMap(deviceIds);
        return R.ok(deviceMap);
    }

    @GetMapping(value = "/device", name = "查询设备")
    public R<DeviceInfo> selectDevice(
            @RequestParam Long deviceId
    ) {
        DeviceInfo device = deviceInfoService.getById(deviceId);
        return R.ok(device);
    }

    @RequiresPermissions("device:grid:list")
    @GetMapping(value = "/page", name = "充电桩分页列表")
    public R<PageInfo<DeviceGridVo>> getChargeGridPage(
            DeviceGridDTO deviceListDTO) {
        startPage();
        PageInfo<DeviceGridVo> page = deviceInfoService.selectCgPage(deviceListDTO);

        //设置总耗电量
        setTotalElec(page);

        return R.ok(page);
    }

    private void setTotalElec(PageInfo<DeviceGridVo> page) {
        try{
            List<DeviceGridVo> list = page.getList();
            if (!CollectionUtils.isEmpty(list)) {
                List<Long> ids = list.stream().map(DeviceGridVo::getDeviceId).collect(Collectors.toList());

                R<List<DeviceOrderStatisticsData>> deviceOrderStatistics = orderStatisticsService.getDeviceOrderStatistics(ids);
                if (deviceOrderStatistics != null && R.isSuccess(deviceOrderStatistics) && deviceOrderStatistics.getData() != null) {
                    Map<Long, DeviceOrderStatisticsData> data = deviceOrderStatistics.getData().stream().collect(Collectors.toMap(DeviceOrderStatisticsData::getDeviceId, Function.identity()));
                    list.stream().forEach(deviceGridVo -> {
                        DeviceOrderStatisticsData deviceOrderStatisticsData = data.get(deviceGridVo.getDeviceId());
                        if (deviceOrderStatisticsData != null) {
                            deviceGridVo.setTotalElec(String.valueOf(deviceOrderStatisticsData.getConsumeElectricity()));
                        }
                    });
                }
            }
        }catch (Exception e){
            log.error("获取总耗电量失败,", e);
        }

    }

    @GetMapping(value = "/list", name = "充电桩列表")
    public R<List<SimpleDeviceVo>> getChargeGridList(
            DeviceGridDTO deviceListDTO
    ) {
        List<SimpleDeviceVo> devices = deviceInfoService.selectDevices(deviceListDTO);
        return R.ok(devices);
    }

    @RequiresPermissions("device:grid:add")
    @PostMapping(name = "新增充电桩设备")
    public R<Long> addChargeGridDTO(
            @RequestBody  ChargeGridDTO chargeGridDTO) {
        Long deviceId = deviceInfoService.addChargeGrid(chargeGridDTO);
        return R.ok(deviceId);
    }

    @RequiresPermissions("device:grid:edit")
    @PutMapping(name = "编辑充电桩设备信息")
    public R<Boolean> updateChargeGrid(
            @RequestBody UpdateChargeGridDTO chargeGridDTO
    ) {
        Boolean success = deviceInfoService.updateChargeGrid(chargeGridDTO);
        return R.ok(success);
    }

    @RequiresPermissions("device:grid:remove")
    @DeleteMapping(name = "删除充电桩设备")
    public R<?> deleteChargeGrid(
            @RequestBody UpdateChargeGridDTO chargeGridDTO
    ) {
        deviceInfoService.deleteChargeGrid(chargeGridDTO.getDeviceId());
        return R.ok();
    }

    @RequiresPermissions("device:grid:payRule")
    @PutMapping(value = "rule",name = "编辑规则")
    public R<?> updateRule(
            @RequestBody UpdateChargeGridRuleDTO chargeGridDTO
    ) {
        deviceInfoService.updateRule(chargeGridDTO);
        return R.ok();
    }

    @PutMapping(value = "rules",name = "批量修改规则")
    public R<?> updateRules(
            @RequestBody UpdateChargeGridRulesDTO chargeGridDTO
    ) {
        deviceInfoService.updateRules(chargeGridDTO);
        return R.ok();
    }

    @RequiresPermissions("device:grid:query")
    @GetMapping(name = "查看充电桩设备详情")
    public R<ChargeGridVo> getChargeGridDetail(
            @RequestParam Long deviceId
    ) {
        ChargeGridVo chargeGridVo = deviceInfoService.getChargeGridDetail(deviceId);
        return R.ok(chargeGridVo);
    }

    @InnerAuth
    @GetMapping(value = "/deviceCount", name = "获取充电桩设备数量")
    public R<DeviceCount> getDeviceCount(@RequestParam(value = "operatorId", required = false) Long operatorId,
                                         @RequestParam(value = "siteId", required = false) Long siteId,
                                         @RequestParam(value = "sn", required = false) String sn){
        DeviceCountReqDTO reqDTO = DeviceCountReqDTO.builder()
                .operatorId(operatorId)
                .siteId(siteId)
                .sn(sn)
                .component(ComponentEnum.PILE.getType())
                .build();
        return R.ok(deviceInfoService.selectDeviceCount(reqDTO));
    }

    @RequiresPermissions("device:grid:detail")
    @GetMapping(value = "/detail", name = "充电桩完整的详情信息，包含枪口信息")
    public R<DeviceGridDetailVO> getSpecificDetail(@RequestParam("deviceId") Long deviceId){
        return R.ok(deviceInfoService.selectDeviceGridSpecificDetail(deviceId));
    }

    @GetMapping(value = "/tree", name = "获取充电桩设备树数据")
    public R<List<DeviceSimpleTreeVO>> getDeviceSimpleTree(ChargeGridTreeReqDTO req){
        return R.ok(deviceInfoService.selectDeviceTreeList(req));
    }

    @RequiresPermissions("device:grid:detail:manage")
    @GetMapping(value = "/getManageData", name = "查询充电桩经营数据")
    public R<ChargeGridStatisticsTotalDataResp> getManageData(OrderStatisticsReq req){
        return R.ok(deviceStatisticsService.selectGridManageData(req));
    }
}
