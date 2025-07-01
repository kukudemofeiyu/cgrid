package com.things.cgomp.pay;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 支付模块
 * 
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class CGPayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGPayApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  支付模块启动成功   ლ(´ڡ`ლ)ﾞ)");
    }
}
