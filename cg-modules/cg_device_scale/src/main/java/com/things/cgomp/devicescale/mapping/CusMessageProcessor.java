package com.things.cgomp.devicescale.mapping;

import com.things.cgomp.devicescale.message.Message;
import com.things.cgomp.devicescale.service.DeviceScaleService;
import com.things.cgomp.devicescale.session.Attachment;
import lombok.extern.slf4j.Slf4j;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.extension.processor.AbstractMessageProcessor;
import org.smartboot.socket.transport.AioSession;
import com.things.cgomp.devicescale.session.SessionContext;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Slf4j
public class CusMessageProcessor extends AbstractMessageProcessor<Message> {


    private SessionContext sessionContext;

    public CusMessageProcessor(SessionContext sessionContext) {
        super();
        this.sessionContext = sessionContext;
    }

    @Override
    public void process0(AioSession aioSession, Message message) {
        log.info("response mesg:{}", message);
        try {
            message.getHandler().invoke(message, sessionContext);
        } catch (Exception e) {
            log.error("CusMessageProcessor.process0 error", message);
        }

    }

    @Override
    public void stateEvent0(AioSession aioSession, StateMachineEnum stateMachineEnum, Throwable throwable) {
        log.info("sessionContext :{}", sessionContext);
        if (null != throwable) {
            log.error("stateEvent0 error", throwable);
        }
        Attachment attachment = aioSession.getAttachment();
        if (null != attachment) {
            String sn = attachment.getSn();
            SessionContext sc = DeviceScaleService.sessions.get(sn);
            if (sc != null) {
                sc.close();
                DeviceScaleService.sessions.remove(sn);
            }
        }
    }


}
