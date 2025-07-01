package com.things.cgomp.common.mq.message;

import com.things.cgomp.common.mq.common.AbstractBody;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 离线消息请求对象
 * @author things
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class DeviceOffLineReqMsg extends AbstractBody {

    private Long sessionCountTime;//会话时长
    private Long sessionStartTime;//会话开始时间
    private Integer causeType;
    private String cause;//离线原因
}
