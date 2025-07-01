package com.things.cgomp.app.controller;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.domain.dto.ChargingRealDataDTO;
import com.things.cgomp.app.domain.dto.PlotInfoReq;
import com.things.cgomp.app.domain.vo.ChargingRealDataVO;
import com.things.cgomp.app.domain.vo.ChargingRuleVO;
import com.things.cgomp.app.domain.vo.PortAppDetailVO;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;
import com.things.cgomp.common.mq.message.DeviceChargeDataMsg;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import com.things.cgomp.app.service.ChargingPileService;
import com.things.cgomp.common.core.domain.R;
import com.things.cgomp.device.api.model.vo.SiteAppVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/charging")
@Slf4j
public class ChargingController {
    @Resource
    private ChargingPileService chargingPileService;
    /**
     * 分页查询充电桩站点列表
     */

    @GetMapping(value = "/page",name = "分页查询充电桩站点列表")
    public R<PageInfo<SiteAppVO>> getPlotInfoPage(PlotInfoReq plotInfoReqVO) {

        PageInfo<SiteAppVO> pageInfo = chargingPileService.getPlotInfo(plotInfoReqVO);
        return R.ok(pageInfo);
    }
    /**
     * 充电桩站点列表
     */
    @GetMapping(value = "/list",name = "分页查询充电桩站点列表")
    public R<List<SiteAppVO>> getSiteInfoList(PlotInfoReq plotInfoReqVO) {
        List<SiteAppVO> siteAppVO = chargingPileService.getSiteInfoList(plotInfoReqVO);
        return R.ok(siteAppVO);
    }



    /**
     * 充电桩电站详情
     * @param siteId
     * @param lng
     * @param lat
     * @return
     */

    @GetMapping(value = "/info",name = "充电桩电站详情")
    public R<SiteAppInfoVO> getSiteInfoVO(@RequestParam("siteId") Long siteId, @RequestParam(value ="lng", required = false) Float lng, @RequestParam(value = "lat", required = false) Float lat) {
        SiteAppInfoVO siteInfoVO = chargingPileService.getSiteInfoVO(siteId,lng,lat);
        return R.ok(siteInfoVO);
    }
    /**
     * 获取桩计费规则详情
     */
    @GetMapping(value = "/rule/detail",name = "充电桩计费规则详情")
    public R<List<ChargingRuleVO>> getChargingPileRuleDetail(@RequestParam("ruleId") Long ruleId) {
        List<ChargingRuleVO> chargingRule = chargingPileService.getChargingPileRuleDetail(ruleId);
        return R.ok(chargingRule);
    }
    /**
     * 充电枪详情
     */
    @GetMapping(value = "/port/detail",name = "充电枪详情(开始充电界面)")
    public R<PortAppDetailVO> getChargingPortIdDetail(@RequestParam("portId") Long portId) {
        PortAppDetailVO siteAppVO = chargingPileService.getChargingPileGunDetail(portId);
        return R.ok(siteAppVO);
    }
    /**
     * 充电枪详情(开始充电界面)通过桩编码
     */
    @GetMapping(value = "/port/detail/byPile",name = "充电枪详情(开始充电界面)通过桩编码")
    public R<PortAppDetailVO> getChargingPortDetailByPile(@RequestParam("portSn") String portSn) {
        PortAppDetailVO siteAppVO = chargingPileService.getChargingPileGunDetailBySn(portSn);
        return R.ok(siteAppVO);
    }
     /**
     * 开启充电
      * @param portId 端口ID
//      * @param chargePeriod 充电时长， 0是自动充满
     */
    @GetMapping(value = "/start",name = "开启充电")
    public R<DeviceCommandVO> startCharging(@RequestParam("portId") Long portId) {
        DeviceCommandVO commandVO = chargingPileService.startCharging(portId);
        return R.ok(commandVO);
    }
    /**
     * 充电详情
     */
    @GetMapping(value = "/detail",name = "充电详情")
    public R<ChargingRealDataVO> getChargingDetail(@RequestParam(value = "portId") Long portId,
                                                   @RequestParam(value = "tradeSn") String tradeSn) {
        ChargingRealDataVO chargingRealDataVO = chargingPileService.getChargingDetail(portId, tradeSn);
        return R.ok(chargingRealDataVO);
    }


    /**
     * 停止充电
     */
    @GetMapping(value = "/stop",name = "停止充电")
    public R<DeviceCommandVO> stopCharging(@RequestParam("portId") Long portId) {
        DeviceCommandVO commandVO = chargingPileService.stopCharging(portId);
        return R.ok(commandVO);
    }
    /**
     * 模拟设备充电信息上报
     */
    @PostMapping(value = "/push",name = "模拟设备充电信息上报")
    public R<Boolean> pushChargingInfo(@RequestBody ChargingRealDataDTO deviceChargeDataMsg) {
        Boolean aBoolean = chargingPileService.pushChargingInfo(deviceChargeDataMsg);
        return R.ok(aBoolean);

    }
}
