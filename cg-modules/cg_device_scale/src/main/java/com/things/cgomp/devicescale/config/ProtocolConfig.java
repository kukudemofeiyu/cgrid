package com.things.cgomp.devicescale.config;

import com.things.cgomp.devicescale.mapping.HandlerMapper;
import com.things.cgomp.devicescale.mapping.SpringHandlerMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Configuration
public class ProtocolConfig {

    @Value(value = "${deviceGw.serverIP}")
    private String serverIp;

    @Value(value = "${deviceGw.serverPort}")
    private Integer serverPort;

    @Value(value = "${deviceGw.crypt:false}")
    private boolean crypt;


    @Bean
    public HandlerMapper handlerMapper() {
        return new SpringHandlerMapper("com.things.cgomp.devicescale.endpoint");
    }


    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public boolean isCrypt() {
        return crypt;
    }

    public void setCrypt(boolean crypt) {
        this.crypt = crypt;
    }
}
