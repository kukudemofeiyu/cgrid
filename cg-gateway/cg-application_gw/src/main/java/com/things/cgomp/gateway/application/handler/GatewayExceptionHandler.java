package com.things.cgomp.gateway.application.handler;

import com.things.cgomp.common.core.constant.Constants;
import com.things.cgomp.common.core.exception.ErrorCode;
import com.things.cgomp.common.core.utils.ServletUtils;
import com.things.cgomp.gateway.application.enums.ErrorCodeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关统一异常处理
 *
 * @author things
 */
@Order(-1)
@Configuration
public class GatewayExceptionHandler implements ErrorWebExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GatewayExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex)
    {
        ServerHttpResponse response = exchange.getResponse();

        if (exchange.getResponse().isCommitted())
        {
            return Mono.error(ex);
        }

        ErrorCode errorCode;

        if (ex instanceof NotFoundException)
        {
            errorCode = ErrorCodeConstants.GW_SERVICE_NOT_FOUNT;
        }
        else if (ex instanceof ResponseStatusException)
        {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            errorCode = new ErrorCode(Constants.FAIL, responseStatusException.getMessage());
        }
        else
        {
            errorCode = ErrorCodeConstants.GW_INNER_SERVER_ERROR;
        }

        log.error("[网关异常处理]请求路径:{},异常信息:{}", exchange.getRequest().getPath(), ex.getMessage());

        return ServletUtils.webFluxResponseWriter(response, errorCode);
    }
}