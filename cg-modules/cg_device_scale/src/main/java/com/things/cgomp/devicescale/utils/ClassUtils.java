package com.things.cgomp.devicescale.utils;

import com.things.cgomp.devicescale.annotation.Endpoint;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.*;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Slf4j
public class ClassUtils {

    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class.getSimpleName());


    public static List<Class<?>> getClassList(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = getClassList(packageName);
        Iterator<Class<?>> iterator = classList.iterator();
        while (iterator.hasNext()) {
            Class<?> next = iterator.next();
            if (!next.isAnnotationPresent(annotationClass))
                iterator.remove();
        }
        return classList;
    }

    public static List<Class<?>> getClassList(String packageName) {
        List<Class<?>> classList = new LinkedList();
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String packagePath = url.getPath().replaceAll("%20", " ");
                    addClass(classList, packagePath, packageName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return classList;
    }

    private static void addClass(List<Class<?>> classList, String packagePath, String packageName) {
        try {
            File[] files = new File(packagePath).listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
                }
            });
            if (files != null)
                for (File file : files) {
                    String fileName = file.getName();
                    if (file.isFile()) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        if (!isNil(packageName)) {
                            className = packageName + "." + className;
                        }
                        doAddClass(classList, className);
                    } else {
                        String subPackagePath = fileName;
                        if (!isNil(packagePath)) {
                            subPackagePath = packagePath + "/" + subPackagePath;
                        }
                        String subPackageName = fileName;
                        if (!isNil(packageName)) {
                            subPackageName = packageName + "." + subPackageName;
                        }
                        addClass(classList, subPackagePath, subPackageName);
                    }
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void doAddClass(List<Class<?>> classList, String className) {
        Class<?> cls = loadClass(className, false);
        classList.add(cls);
    }

    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> cls;
        try {
            cls = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static boolean isNil(String st) {

        return (null == st || "".equals(st.trim()));
    }

    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static List<Class<?>> getClassList(String packageName, Class<Endpoint> endpointClass, ResourcePatternResolver resolver, MetadataReaderFactory metadataReaderFactory) {
        List<Class<?>> classs = new ArrayList<Class<?>>();

        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                    .concat(org.springframework.util.ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(packageName))
                            .concat("/**/*.class"));
            Resource[] resources = resolver.getResources(packageSearchPath);
            MetadataReader metadataReader = null;
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    try {
                        if (metadataReader.getClassMetadata().isConcrete()) {// 当类型不是抽象类或接口在添加到集合
                            classs.add(Class.forName(metadataReader.getClassMetadata().getClassName()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classs;

    }
}