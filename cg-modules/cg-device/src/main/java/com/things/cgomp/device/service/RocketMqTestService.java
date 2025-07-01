package com.things.cgomp.device.service;

import com.things.cgomp.device.dto.rocket.test.BaseRocketMqTestDTO;

public interface RocketMqTestService {

    void syncSend(BaseRocketMqTestDTO rocketMqTestDTO);
}
