package com.things.cgomp.devicescale.mapping;



import com.things.cgomp.devicescale.annotation.Endpoint;
import com.things.cgomp.devicescale.annotation.Mapping;
import com.things.cgomp.devicescale.utils.BeanUtils;
import com.things.cgomp.devicescale.utils.ClassUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class DefaultHandlerMapper implements HandlerMapper {

    private Map<Integer, Handler> handlerMap = new HashMap(55);

    public DefaultHandlerMapper(String... packageNames) {
        for (String packageName : packageNames) {
            addPackage(packageName);
        }
    }

    private void addPackage(String packageName) {
        List<Class<?>> handlerClassList = ClassUtils.getClassList(packageName, Endpoint.class);

        for (Class<?> handlerClass : handlerClassList) {
            Method[] methods = handlerClass.getDeclaredMethods();
            if (methods != null) {
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Mapping.class)) {
                        Mapping annotation = method.getAnnotation(Mapping.class);
                        String desc = annotation.desc();
                        int[] types = annotation.types();
                        Handler value = new Handler(BeanUtils.newInstance(handlerClass), method, desc);
                        for (int type : types) {
                            handlerMap.put(type, value);
                        }
                    }
                }
            }
        }
    }

    public Handler getHandler(Integer key) {
        return handlerMap.get(key);
    }

}