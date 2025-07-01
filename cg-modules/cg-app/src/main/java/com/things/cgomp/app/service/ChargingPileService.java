package com.things.cgomp.app.service;

import com.github.pagehelper.PageInfo;
import com.things.cgomp.app.domain.dto.ChargingRealDataDTO;
import com.things.cgomp.app.domain.dto.PlotInfoReq;
import com.things.cgomp.app.domain.vo.ChargingRealDataVO;
import com.things.cgomp.app.domain.vo.ChargingRuleVO;
import com.things.cgomp.app.domain.vo.PortAppDetailVO;
import com.things.cgomp.common.device.pojo.device.DeviceCommandVO;
import com.things.cgomp.device.api.model.vo.SiteAppInfoVO;
import com.things.cgomp.device.api.model.vo.SiteAppVO;

import java.util.List;

public interface ChargingPileService {
    PageInfo<SiteAppVO> getPlotInfo(PlotInfoReq plotInfoReqVO);

    SiteAppInfoVO getSiteInfoVO(Long siteId, Float lng, Float lat);

    List<ChargingRuleVO> getChargingPileRuleDetail(Long ruleId);

    PortAppDetailVO getChargingPileGunDetail(Long portId);

    DeviceCommandVO startCharging(Long portId);

    DeviceCommandVO stopCharging(Long gunId);

    Boolean pushChargingInfo(ChargingRealDataDTO deviceChargeDataMsg);

    ChargingRealDataVO getChargingDetail(Long portId, String tradeSn);

    PortAppDetailVO getChargingPileGunDetailBySn(String portSn);

    void sendStopChargingMessage(Long id);

    List<SiteAppVO> getSiteInfoList(PlotInfoReq plotInfoReqVO);
}
