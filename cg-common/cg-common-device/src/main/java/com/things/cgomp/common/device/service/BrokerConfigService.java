package com.things.cgomp.common.device.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.things.cgomp.common.device.dao.node.domain.BrokerConfigInfo;
import com.things.cgomp.common.device.dao.node.mapper.BrokerConfigDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BrokerConfigService {

    @Autowired
    private BrokerConfigDao brokerConfigDao;

    public List<BrokerConfigInfo> queryAll() {
        QueryWrapper<BrokerConfigInfo> wrapper = Wrappers.query();
        wrapper.eq("status", 1);
        return brokerConfigDao.selectList(wrapper);
    }
}
