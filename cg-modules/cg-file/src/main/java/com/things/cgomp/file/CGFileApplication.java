package com.things.cgomp.file;

import com.things.cgomp.common.security.annotation.EnableCustomConfig;
import com.things.cgomp.common.security.annotation.EnableRyFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 文件服务
 * 
 * @author things
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CGFileApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(CGFileApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  文件服务模块启动成功");
    }
}
