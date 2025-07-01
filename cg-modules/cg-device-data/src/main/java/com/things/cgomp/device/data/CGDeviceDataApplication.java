package com.things.cgomp.device.data;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 设备数据模块
 *
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class CGDeviceDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(CGDeviceDataApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  设备数据模块启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
