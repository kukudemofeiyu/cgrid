package com.things.cgomp.devicescale.utils;


import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.message.Cache;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */

public abstract class PropertyUtils {

    private static final Cache<Class<?>, PropertyDescriptor[]> propertyDescriptorCache = new Cache(32);

    public static PropertyDescriptor[] getPropertyDescriptor(Class<?> key) {
        return propertyDescriptorCache.get(key, () -> {
            BeanInfo beanInfo = BeanUtils.getBeanInfo(key);
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            List<PropertyDescriptor> result = new ArrayList(pds.length);

            for (PropertyDescriptor pd : pds)
                if (pd.getReadMethod().isAnnotationPresent(Property.class))
                    result.add(pd);

            Collections.sort(result, Comparator.comparingInt(pd -> pd.getReadMethod().getAnnotation(Property.class).index()));
            return result.toArray(new PropertyDescriptor[result.size()]);
        });
    }

    public static int getLength(Object obj, Property prop) {
        int length = prop.length();
        if (length == -1) {
            if ("".equals(prop.lengthName()))
                length = prop.type().length;
            else
                length = (int) BeanUtils.getValue(obj, prop.lengthName(), 0);
        }

        return length;
    }


}