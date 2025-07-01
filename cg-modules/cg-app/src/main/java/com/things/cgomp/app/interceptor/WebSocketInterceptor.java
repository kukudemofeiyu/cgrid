package com.things.cgomp.app.interceptor;

import com.things.cgomp.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class WebSocketInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            @NotNull ServerHttpRequest request,
            @NotNull ServerHttpResponse serverHttpResponse,
            @NotNull org.springframework.web.socket.WebSocketHandler webSocketHandler,
            @NotNull Map<String, Object> attributes
    ) {
        if (request instanceof ServletServerHttpRequest) {
            Map<String, String> queryParams = getQueryParams(request);
            String token = getToken(request);
            Long userId = SecurityUtils.getUserId();
            String username = SecurityUtils.getLoginUser().getUsername();
            attributes.put("userId", userId);
            attributes.put("username", username);
            attributes.putAll(queryParams);
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    private Map<String, String> getQueryParams(@NotNull ServerHttpRequest request) {
        URI uri = request.getURI();
        return getQueryParams(uri);
    }

    @NotNull
    private Map<String, String> getQueryParams(URI uri) {
        String query = uri.getQuery();
        if(StringUtils.isBlank(query)){
            return new HashMap<>();
        }

        return getQueryParams(query);
    }

    @NotNull
    private Map<String, String> getQueryParams(String query) {
        Map<String, String> paramsMap = new HashMap<>();
        String[] paramsList = query.split("&");
        for (String paramsStr : paramsList) {
            String[] keyValues = paramsStr.split("=");
            if (keyValues.length == 2) {
                paramsMap.put(keyValues[0], keyValues[1]);
            }
        }
        return paramsMap;
    }

    private String getToken(ServerHttpRequest request) {
        String path = getPath(request);

        if(StringUtils.isBlank(path)){
            return null;
        }
        return path.substring(path.lastIndexOf("/")+1);
    }

    private String getPath(ServerHttpRequest request) {
        URI uri = request.getURI();
        return uri.getPath();
    }

    @Override
    public void afterHandshake(
            @NotNull ServerHttpRequest serverHttpRequest,
            @NotNull ServerHttpResponse serverHttpResponse,
            @NotNull org.springframework.web.socket.WebSocketHandler webSocketHandler,
            Exception e
    ) {
    }
}