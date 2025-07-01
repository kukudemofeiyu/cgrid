package com.things.cgomp.app;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 小程序API模块
 * 
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class CGAppApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGAppApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  小程序API模块启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
