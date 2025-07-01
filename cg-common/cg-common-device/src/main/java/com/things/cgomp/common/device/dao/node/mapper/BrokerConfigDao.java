package com.things.cgomp.common.device.dao.node.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.things.cgomp.common.device.dao.node.domain.BrokerConfigInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrokerConfigDao extends BaseMapper<BrokerConfigInfo> {

    default BrokerConfigInfo getByProtocolCode(String protocolCode){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("protocol_code", protocolCode);
        return this.selectOne(queryWrapper);
    }

}
