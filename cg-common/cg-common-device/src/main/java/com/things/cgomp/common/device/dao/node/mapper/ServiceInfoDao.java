package com.things.cgomp.common.device.dao.node.mapper;

import com.things.cgomp.common.device.dao.node.domain.ServiceInfo;
import com.things.cgomp.common.mybatisplus.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ServiceInfoDao extends BaseMapperX<ServiceInfo> {

    ServiceInfo findServiceInfoByServiceId(@Param("serviceId") String serviceId);

    Integer save(ServiceInfo serviceInfo);

    Integer update(ServiceInfo serviceInfo);
}
