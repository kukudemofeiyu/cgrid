package com.things.cgomp.alarm;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 告警模块
 * 
 * @author things
 */
@Slf4j
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class CGAlarmApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGAlarmApplication.class, args);
        log.info("(♥◠‿◠)ﾉﾞ  告警模块启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
