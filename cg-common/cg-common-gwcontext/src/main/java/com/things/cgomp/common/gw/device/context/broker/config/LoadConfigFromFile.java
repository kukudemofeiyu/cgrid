package com.things.cgomp.common.gw.device.context.broker.config;

import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

import javax.sql.rowset.serial.SerialException;
import java.util.Map;



@ConditionalOnExpression("'${node.broker.readFor}'=='file'")
@Configuration
public class LoadConfigFromFile implements ILoadConfig {

    @Override
    public Map<Integer, HubBrokerConfig> read() throws ServiceException {
        throw new ServiceException(ErrorCodeConstants.NO_CONFIG_BROKER_FILE);
    }


}