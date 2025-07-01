/**
 * Copyright © 2016-2023 The Thingsboard Authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.things.cgomp.common.gw.device.context.api;


import com.alibaba.fastjson.JSON;
import com.things.cgomp.common.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("syncMsgRedisTemplate")
public  class SyncSendMsgRedisTemplate implements SyncMsgTemplate {

    @Autowired
    private RedisService redisService;

    private final String sync_flag = "Sync_";

    @Override
    public Object sendMsg(String deviceNo, Integer frameSerialNo, Integer timeout) throws Exception {
        return redisService.leftPop(sync_flag+ deviceNo + "_" + frameSerialNo, timeout, TimeUnit.MILLISECONDS);
    }

    public void backMsg(String deviceNo, Integer frameSerialNo, Object resultData)  throws Exception{
        redisService.leftPush(sync_flag+deviceNo+"_" + frameSerialNo, JSON.toJSONString(resultData), 30l);
    }

}
