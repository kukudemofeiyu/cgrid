package com.things.cgomp.gateway.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 网关启动程序
 * 
 * @author things
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CGGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGGatewayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  应用服务网关启动成功   ლ(´ڡ`ლ)ﾞ");
    }
}
