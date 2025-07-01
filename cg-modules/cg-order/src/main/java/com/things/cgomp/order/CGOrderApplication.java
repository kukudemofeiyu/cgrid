package com.things.cgomp.order;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 订单模块
 * 
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class CGOrderApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGOrderApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  订单模块启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
