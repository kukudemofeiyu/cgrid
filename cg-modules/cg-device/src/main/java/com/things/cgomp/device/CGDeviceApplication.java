package com.things.cgomp.device;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 设备管理模块
 * 
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class CGDeviceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGDeviceApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  设备管理模块启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
