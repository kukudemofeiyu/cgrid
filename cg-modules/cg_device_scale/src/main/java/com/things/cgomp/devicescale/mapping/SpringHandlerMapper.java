package com.things.cgomp.devicescale.mapping;

import com.things.cgomp.devicescale.annotation.Mapping;
import com.things.cgomp.devicescale.mapping.Handler;
import com.things.cgomp.devicescale.mapping.HandlerMapper;
import com.things.cgomp.devicescale.utils.ClassUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.things.cgomp.devicescale.annotation.Endpoint;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class SpringHandlerMapper implements HandlerMapper, ApplicationContextAware, ResourceLoaderAware {

    protected String[] packageNames;
    private Map<Integer, Handler> handlerMap = new HashMap(55);
    private ResourceLoader resourceLoader;
    private ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    private MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourceLoader);

    public SpringHandlerMapper(String... packageNames) {
        this.packageNames = packageNames;
    }

    public Handler getHandler(Integer key) {
        return handlerMap.get(key);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        for (String packageName : packageNames) {
            List<Class<?>> handlerClassList = ClassUtils.getClassList(packageName, Endpoint.class, resolver, metadataReaderFactory);

            for (Class<?> handlerClass : handlerClassList) {
                Method[] methods = handlerClass.getDeclaredMethods();
                if (methods != null) {
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(Mapping.class)) {
                            Mapping annotation = method.getAnnotation(Mapping.class);
                            String desc = annotation.desc();
                            int[] types = annotation.types();
                            Handler value = new Handler(applicationContext.getBean(handlerClass), method, desc);
                            for (int type : types) {
                                handlerMap.put(type, value);
                            }
                        }
                    }
                }
            }
        }
    }


    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;

    }
}