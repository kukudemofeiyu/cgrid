package com.things.cgomp.devicescale;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统模块
 *
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class CGDeviceScaleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CGDeviceScaleApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  设备测试仿真模块启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
