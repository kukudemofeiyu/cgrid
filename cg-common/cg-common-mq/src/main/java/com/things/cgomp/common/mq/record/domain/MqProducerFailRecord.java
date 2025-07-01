package com.things.cgomp.common.mq.record.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.type.TypeReference;
import com.things.cgomp.common.core.utils.JacksonUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * @author things
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName(value = "mq_producer_fail_record", autoResultMap = true)
public class MqProducerFailRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**
     * 消息主题
     */
    private String topic;
    /**
     * 消息标签
     */
    private String tag;
    /**
     * 重发次数
     */
    private Integer retryCount;
    /**
     * 所属模块
     */
    private String modules;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /**
     * 格式化后消息体
     */
    private String messageFormat;
    /**
     * 消息体
     */
    @TableField(typeHandler = MqMessageTypeHandler.class)
    private byte[] message;

    public static class MqMessageTypeHandler extends AbstractJsonTypeHandler<byte[]> {

        @Override
        public byte[] parse(String json) {
            try {
                return JacksonUtils.parseObjectQuietly(json, new TypeReference<byte[]>() {});
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public String toJson(byte[] obj) {
            return Arrays.toString(obj);
        }
    }
}
