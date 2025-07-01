package com.things.cgomp.common.mq.common;

import lombok.*;

import java.io.Serializable;

/**
 * @author things
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class QueueMsg<Body extends AbstractBody> implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Body body;
    protected Metadata metadata;
}
