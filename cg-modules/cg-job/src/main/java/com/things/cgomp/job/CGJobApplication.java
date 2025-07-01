package com.things.cgomp.job;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 定时任务服务
 * 
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication()
public class CGJobApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGJobApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  定时任务服务模块启动成功");
    }
}
