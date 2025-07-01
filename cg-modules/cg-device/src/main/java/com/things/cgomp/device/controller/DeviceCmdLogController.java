package com.things.cgomp.device.controller;


import com.github.pagehelper.PageInfo;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.common.core.utils.CollectionUtils;
import com.things.cgomp.common.core.web.page.PageDomain;
import com.things.cgomp.common.core.web.page.TableSupport;
import com.things.cgomp.common.device.dao.device.domain.DeviceInfo;
import com.things.cgomp.common.device.pojo.device.DeviceCmdLogVo;
import com.things.cgomp.common.device.pojo.device.DeviceGridDTO;
import com.things.cgomp.common.device.pojo.device.DeviceGridVo;
import com.things.cgomp.device.data.api.RemoteDeviceCmdLogService;
import com.things.cgomp.device.service.IDeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/deviceCmdLog")
public class DeviceCmdLogController {


    @Autowired
    private RemoteDeviceCmdLogService remoteDeviceCmdLogService;
    @Autowired
    private IDeviceInfoService deviceInfoService;

    @GetMapping(value = "/page", name = "查询设备交互日志（分页）")
    public R<PageInfo<DeviceCmdLogVo>> getChargeGridPage(
            @RequestParam("sn") String sn,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime,
            @RequestParam(value = "cmd", required = false) String cmd) {

        DeviceInfo deviceInfo = deviceInfoService.findDeviceInfo(new DeviceInfo().setSn(sn));
        if(deviceInfo == null){
            return R.ok(PageInfo.emptyPageInfo());
        }


        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        R<PageInfo<DeviceCmdLogVo>> cmdLogPage = remoteDeviceCmdLogService.getCmdLogPage(
                deviceInfo.getDeviceId(),
                startTime,
                endTime, cmd,
                pageNum,pageSize);

        if(cmdLogPage!=null && R.isSuccess(cmdLogPage) && cmdLogPage.getData() !=null){

            PageInfo<DeviceCmdLogVo> data = cmdLogPage.getData();
            List<DeviceCmdLogVo> list = data.getList();
            if(list.size() >0){
                List<Long> deviceIds = list.stream().map(DeviceCmdLogVo::getDeviceId).collect(Collectors.toList());

                List<DeviceGridVo> deviceInfos = deviceInfoService.selectDeviceByIds(deviceIds);
                if(!CollectionUtils.isAnyEmpty(deviceInfos)){
                    Map<Long, DeviceGridVo> deviceInfoMap =
                            deviceInfos.stream().collect(Collectors.toMap(DeviceGridVo::getDeviceId, Function.identity()));

                    list.forEach(deviceCmdLogVo -> {

                        DeviceGridVo deviceInfo1 = deviceInfoMap.get(deviceCmdLogVo.getDeviceId());
                        if(deviceInfo1!=null){
                            deviceCmdLogVo.setSn(deviceInfo1.getSn());
                            deviceCmdLogVo.setOperatorName(deviceInfo1.getOperatorName());
                        }

                    });
                }



                return R.ok(data);
            }
        }


        return R.ok(PageInfo.emptyPageInfo());
    }

}
