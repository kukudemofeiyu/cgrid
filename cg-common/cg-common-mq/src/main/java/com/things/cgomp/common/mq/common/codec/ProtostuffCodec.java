package com.things.cgomp.common.mq.common.codec;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author things
 */
public class ProtostuffCodec {

    /**
     * 线程局部变量
     */
    private final ThreadLocal<LinkedBuffer> BUFFERS = new ThreadLocal();

    /**
     * 序列化/反序列化包装类 Schema 对象
     */
    private final Schema<SerializeDeserializeWrapper> WRAPPER_SCHEMA = RuntimeSchema.getSchema(SerializeDeserializeWrapper.class);


    /**
     * 序列化对象
     *
     * @param obj 需要序列化的对象
     * @return 序列化后的二进制数组
     */
    @SuppressWarnings("unchecked")
    public byte[] serialize(Object obj) {
        Class<?> clazz = (Class<?>) obj.getClass();
        LinkedBuffer buffer = BUFFERS.get();
        if (buffer == null) {//存储buffer到线程局部变量中，避免每次序列化操作都分配内存提高序列化性能
            buffer = LinkedBuffer.allocate(512);
            BUFFERS.set(buffer);
        }
        try {
            Object serializeObject = obj;
            Schema schema = WRAPPER_SCHEMA;
            if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)
                    || Map.class.isAssignableFrom(clazz) || Set.class.isAssignableFrom(clazz)) {//Protostuff 不支持序列化/反序列化数组、集合等对象,特殊处理
                serializeObject = SerializeDeserializeWrapper.builder(obj);
            } else {
                schema = RuntimeSchema.getSchema(clazz);
            }
            return ProtostuffIOUtil.toByteArray(serializeObject, schema, buffer);
        } finally {
            buffer.clear();
        }
    }

    /**
     * 反序列化对象
     *
     * @param data  需要反序列化的二进制数组
     * @param clazz 反序列化后的对象class
     * @param <T>   反序列化后的对象类型
     * @return 反序列化后的实例对象
     */
    public <T> T deserialize(Class<T> clazz, byte[] data) {
        if (clazz.isArray() || Collection.class.isAssignableFrom(clazz)
                || Map.class.isAssignableFrom(clazz) || Set.class.isAssignableFrom(clazz)) {//Protostuff 不支持序列化/反序列化数组、集合等对象,特殊处理
            SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<>();
            ProtostuffIOUtil.mergeFrom(data, wrapper, WRAPPER_SCHEMA);
            return wrapper.getData();
        } else {
            Schema<T> schema = RuntimeSchema.getSchema(clazz);
            T message = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        }


    }

    /**
     * <p>
     * 序列化/反序列化对象包装类 专为基于 Protostuff 进行序列化/反序列化而定义。 Protostuff
     * 是基于POJO进行序列化和反序列化操作。 如果需要进行序列化/反序列化的对象不知道其类型，不能进行序列化/反序列化；
     * 比如Map、List、String、Enum等是不能进行正确的序列化/反序列化。
     * 因此需要映入一个包装类，把这些需要序列化/反序列化的对象放到这个包装类中。 这样每次 Protostuff
     * 都是对这个类进行序列化/反序列化,不会出现不能/不正常的操作出现
     * </p>
     *
     * @author butioy
     */
    private static class SerializeDeserializeWrapper<T> {

        private T data;

        public static <T> SerializeDeserializeWrapper<T> builder(T data) {
            SerializeDeserializeWrapper<T> wrapper = new SerializeDeserializeWrapper<>();
            wrapper.setData(data);
            return wrapper;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }


}


