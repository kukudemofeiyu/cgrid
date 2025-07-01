package com.things.cgomp.device.data.common;

import lombok.Data;

/**
 * @author things
 */
@Data
public class PersistConsumer {

    /**
     * 启动多少个消费者线程则对应多少个分区
     */
    private Integer maxThread = 10;
    /**
     * 每个分区最磊的容量,超过则会阻塞
     */
    private Integer queueMaxNum = 1000;
    /**
     * 单次最大提交数量
     */
    private Integer commitBatchSize = 10;
    /**
     * 从分区中获取消息超时时间 毫秒
     */
    private Integer maxDelay = 2000;
    /**
     * 持久化服务名
     */
    private String persist;
}