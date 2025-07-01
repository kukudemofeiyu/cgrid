package com.things.cgomp.common.device.dao.node.mapper;


import com.things.cgomp.common.device.dao.node.domain.BrokerServiceRunInfo;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrokerServiceRunInfoDao extends BaseMapperX<BrokerServiceRunInfo> {

    List<BrokerServiceRunInfo> selectListByServiceId(String serviceId);

    Integer deleteByServiceId(String serviceId);

    List<String> findLastActiveServiceIdByBrokerId(Integer brokerId);

}
