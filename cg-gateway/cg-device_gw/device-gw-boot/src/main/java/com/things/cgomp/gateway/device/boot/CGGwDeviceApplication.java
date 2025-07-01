package com.things.cgomp.gateway.device.boot;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 设备接入网关
 * 
 * @author things
 */
@EnableFeignClients(basePackages = "com.things.cgomp")
@Slf4j
@SpringBootApplication(scanBasePackages = "com.things.cgomp")
@MapperScan(basePackages = {"com.things.cgomp.**.mapper"})
public class CGGwDeviceApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGGwDeviceApplication.class, args);
        log.warn("(♥◠‿◠)ﾉﾞ  设备接入网关启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
