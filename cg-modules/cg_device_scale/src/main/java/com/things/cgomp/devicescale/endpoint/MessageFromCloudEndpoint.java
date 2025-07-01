package com.things.cgomp.devicescale.endpoint;

import cn.hutool.core.codec.BCD;
import com.alibaba.fastjson2.JSON;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.devicescale.annotation.Endpoint;
import com.things.cgomp.devicescale.annotation.Mapping;
import com.things.cgomp.devicescale.config.ProtocolConfig;
import com.things.cgomp.devicescale.domain.dto.DeviceInfo;
import com.things.cgomp.devicescale.message.Message;
import com.things.cgomp.devicescale.message.req.*;
import com.things.cgomp.devicescale.message.resp.*;
import com.things.cgomp.devicescale.service.BlockResponseService;
import com.things.cgomp.devicescale.service.DeviceScaleService;
import com.things.cgomp.devicescale.session.SessionContext;
import com.things.cgomp.devicescale.session.State;
import com.things.cgomp.devicescale.utils.AES128Decryptor;
import com.things.cgomp.devicescale.utils.ByteUtil;
import com.things.cgomp.devicescale.utils.CrcChecksumUtil;
import com.things.cgomp.devicescale.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.things.cgomp.devicescale.message.CmdId.*;
import static com.things.cgomp.devicescale.service.DeviceScaleService.QUEUE_ORDER_SUB;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */

@Endpoint
@Component
@Slf4j
public class MessageFromCloudEndpoint {
    @Autowired
    BlockResponseService blockResponseService;

    @Lazy
    @Autowired
    private DeviceScaleService deviceScaleService;


    @Autowired
    private ProtocolConfig serverConfig;

    @Autowired
    private RedisService redisService;


    @Mapping(types = 充电桩登录认证响应, desc = "充电桩登录认证响应")
    public void login(Message<AuthResp> message, SessionContext sessionContext) {

        log.info("收到了消息,body={}", message.getBody());
        //  blockResponseService.putData(sessionContext.getSn() + message.getSeq(), message.getBody());
        sessionContext.setAuthPass(true);
        // 缓存是否存在数据
        SessionContext cacheSession = getCacheSession(sessionContext.getSn());
        if (cacheSession == null) {
            //统一恢复初始化
            sessionContext.setPriceMode(null);
            sessionContext.getCgEfSessions().clear();
            DeviceInfo deviceInfo = deviceScaleService.getDeviceInfoBySn(sessionContext.getSn());
            if (null == deviceInfo) {
                log.error("桩信息不存在,sn={}", sessionContext.getSn());
                return;
            }
            //获取该桩下面的枪信息
            List<DeviceInfo> devices = deviceScaleService.getListByParentId(deviceInfo.getDeviceId());
            if (devices != null) {
                Map<String /** 枪 **/, State> map = sessionContext.getCgEfSessions();
                devices.forEach(d -> {
                    //初始化都为空闲
                    State state = new State();
                    state.setStatus(0x02);
                    map.put(d.getAliasSn(), state);
                });

            }
            req0X05(sessionContext);
        } else {
            filledSession(sessionContext, cacheSession);
        }
    }

    private void filledSession(SessionContext sessionContext, SessionContext cacheSession) {
        sessionContext.setPriceMode(cacheSession.getPriceMode());
        sessionContext.setSeq(cacheSession.getSeq());
        sessionContext.setCgEfSessions(cacheSession.getCgEfSessions());
        sessionContext.setSyncHex(cacheSession.getSyncHex());
    }

    private SessionContext getCacheSession(String sn){
        String key = "device_scale:" + sn;
        String sessionJson = redisService.getCacheObject(key);
        if (sessionJson == null) {
            return null;
        }
        return JSON.parseObject(sessionJson, SessionContext.class);
    }


    @Mapping(types = 交易记录召唤, desc = "交易记录召唤")
    public void M04DReq(Message<Req0x4D> message, SessionContext sessionContext) throws Exception {
        log.info("收到了【交易记录召唤】消息,body={}", message.getBody());
        //  blockResponseService.putData(sessionContext.getSn() + message.getSeq(), message.getBody());
        if (!sessionContext.getAuthPass()) {
            log.warn("没有状态数据,没有认证,请重新认证,body={}", message);
        }
        //统一恢复初始化
        String order = message.getBody().getOrderNo();
        String aliSn = message.getBody().getAliSn();
        State state = sessionContext.getCgEfSessions().get(aliSn);
        //没有交易记录
        byte[] b4 = new byte[]{0x00};
        byte[] b5 = new byte[]{0x00};
        if (state == null) {
            b4 = new byte[]{0x01};
            b5 = new byte[]{0x01};
        } else if (!order.equals(state.getOrderNo())) {
            b4 = new byte[]{0x01};
            b5 = new byte[]{0x01};
        } else if (state.getStatus() == 0x03) {
            b4 = new byte[]{0x01};
            b5 = new byte[]{0x02};
        }
        log.info("将发送【交易记录召唤_应答】消息,sessionContext={}", sessionContext);
        byte[] 消息头 = new byte[]{0x68};
        byte[] 序列号 = sessionContext.nextSeq();
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = null;
        byte[] 响应CMD = new byte[]{(byte) 交易记录召唤_响应};
        byte[] b1 = BCD.strToBcd(message.getBody().getOrderNo());//
        byte[] b2 = BCD.strToBcd(message.getBody().getSn());//
        byte[] b3 = BCD.strToBcd(message.getBody().getAliSn());//

        byte[] body = ByteUtil.mergeArrays(b1, b2, b3, b4, b5);
        if (message.getEncryptionType()) {
            加密标志 = new byte[]{0x01};
            log.info("回复Body(加密前),hex={}", body);
            String aesKey = sessionContext.getAesKey();
            body = AES128Decryptor.encrypt(body, aesKey);
        } else {
            加密标志 = new byte[]{0x00};
            log.info("回复Body,hex={}", body);
        }

        byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
        byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
        int crcInt = CrcChecksumUtil.calculateCrc(datas);
        byte[] CRC = ByteUtil.CRCByte2H(crcInt);
        byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
        deviceScaleService.writeMessageToCloud(sessionContext, req, "发送【交易记录召唤_应答】消息,hex=");
        //成功,马上上报一下交易记录
        if ((int) b4[0] == 0) {
            //清除缓存中的数据
            deviceScaleService.upLoadOrderInfo(state.getOrderNo(), state.getVin(), state.getStartTime(), LocalDateTime.now(), sessionContext, aliSn);
        }


    }

    @Mapping(types = 交易记录_应答, desc = "交易记录_应答")
    public void M040Resp(Message<M40OrderResp> message, SessionContext sessionContext) {
        log.info("收到了【交易记录_应答】消息,body={}", message.getBody());
        //  blockResponseService.putData(sessionContext.getSn() + message.getSeq(), message.getBody());
        if (!sessionContext.getAuthPass()) {
            log.warn("没有状态数据,没有认证,请重新认证,body={}", message);
        }
        //统一恢复初始化
        String order = message.getBody().getOrderNo();
        int code = message.getBody().getCode();
        if (code == 0) {
            //清除缓存中的数据
            sessionContext.delSyncQueueData(QUEUE_ORDER_SUB + order);
        }


    }

    @Mapping(types = 远程账户余额更新, desc = "远程账户余额更新")
    public void M0X42Req(Message<Req0x42> message, SessionContext sessionContext) throws Exception {
        log.info("收到了【交易记录_应答】消息,body={}", message.getBody());
        //  blockResponseService.putData(sessionContext.getSn() + message.getSeq(), message.getBody());
        if (!sessionContext.getAuthPass()) {
            log.warn("没有状态数据,没有认证,请重新认证,body={}", message);
        }
        log.info("将发送【计费模型请求_A09】消息,sessionContext={}", sessionContext);
        byte[] 消息头 = new byte[]{0x68};
        byte[] 序列号 = sessionContext.nextSeq();
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = null;
        byte[] 响应CMD = new byte[]{(byte) 远程账户余额更新_答应};
        byte[] b1 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
        byte[] b2 = BCD.strToBcd(message.getBody().getCardNo());
        byte[] b3 = new byte[]{0x00};
        byte[] body = ByteUtil.mergeArrays(b1, b2, b3);
        if (message.getEncryptionType()) {
            加密标志 = new byte[]{0x01};
            log.info("回复Body(加密前),hex={}", body);
            String aesKey = sessionContext.getAesKey();
            body = AES128Decryptor.encrypt(body, aesKey);
        } else {
            加密标志 = new byte[]{0x00};
            log.info("回复Body,hex={}", body);
        }

        byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
        byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
        int crcInt = CrcChecksumUtil.calculateCrc(datas);
        byte[] CRC = ByteUtil.CRCByte2H(crcInt);
        byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
        deviceScaleService.writeMessageToCloud(sessionContext, req, "发送【远程账户余额更新回复】消息,hex=");

    }

    @Mapping(types = 对时设置, desc = "对时设置")
    public void M0X56Req(Message<Req0x56> message, SessionContext sessionContext) throws Exception {
        log.info("收到了【对时设置】消息,body={}", message.getBody());
        //  blockResponseService.putData(sessionContext.getSn() + message.getSeq(), message.getBody());
        if (!sessionContext.getAuthPass()) {
            log.warn("没有状态数据,没有认证,请重新认证,body={}", message);
        }
        log.info("将发送【对时设置响应】消息,sessionContext={}", sessionContext);
        byte[] 消息头 = new byte[]{0x68};
        byte[] 序列号 = sessionContext.nextSeq();
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = null;
        byte[] 响应CMD = new byte[]{(byte) 对时设置_响应};
        byte[] b1 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
        byte[] b2 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] body = ByteUtil.mergeArrays(b1, b2);
        if (message.getEncryptionType()) {
            加密标志 = new byte[]{0x01};
            log.info("回复Body(加密前),hex={}", body);
            String aesKey = sessionContext.getAesKey();
            body = AES128Decryptor.encrypt(body, aesKey);
        } else {
            加密标志 = new byte[]{0x00};
            log.info("回复Body,hex={}", body);
        }

        byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
        byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
        int crcInt = CrcChecksumUtil.calculateCrc(datas);
        byte[] CRC = ByteUtil.CRCByte2H(crcInt);
        byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
        deviceScaleService.writeMessageToCloud(sessionContext, req, "发送【对时设置响应】消息,hex=");

    }

    @Mapping(types = 参数设置, desc = "参数设置")
    public void M0X5FReq(Message<Req0x5F> message, SessionContext sessionContext) throws Exception {
        log.info("收到了【参数设置】消息,body={}", message.getBody());
        //  blockResponseService.putData(sessionContext.getSn() + message.getSeq(), message.getBody());
        if (!sessionContext.getAuthPass()) {
            log.warn("没有状态数据,没有认证,请重新认证,body={}", message);
        }
        log.info("将发送【参数设置_应答】消息,sessionContext={}", sessionContext);
        byte[] 消息头 = new byte[]{0x68};
        byte[] 序列号 = sessionContext.nextSeq();
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = null;
        byte[] 响应CMD = new byte[]{(byte) 参数设置_应答};
        byte[] b1 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
        byte[] b2 = BCD.strToBcd(message.getBody().getAliSn());//1 桩编号
        byte[] b3 = new byte[]{0x01};
        byte[] body = ByteUtil.mergeArrays(b1, b2, b3);
        if (message.getEncryptionType()) {
            加密标志 = new byte[]{0x01};
            log.info("回复Body(加密前),hex={}", body);
            String aesKey = sessionContext.getAesKey();
            body = AES128Decryptor.encrypt(body, aesKey);
        } else {
            加密标志 = new byte[]{0x00};
            log.info("回复Body,hex={}", body);
        }

        byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
        byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
        int crcInt = CrcChecksumUtil.calculateCrc(datas);
        byte[] CRC = ByteUtil.CRCByte2H(crcInt);
        byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
        deviceScaleService.writeMessageToCloud(sessionContext, req, "发送【参数设置_应答】消息,hex=");

    }

    @Mapping(types = 插枪_充电桩上报vin码_响应, desc = "插枪_充电桩上报vin码_响应")
    public void A9AuthResp(Message<A9AuthResp> message, SessionContext sessionContext) {
        log.info("收到了【插枪_充电桩上报vin码_响应】消息,body={}", message.getBody());
        //  blockResponseService.putData(sessionContext.getSn() + message.getSeq(), message.getBody());
        if (!sessionContext.getAuthPass()) {
            log.warn("没有状态数据,没有认证,请重新认证,body={}", message);
        }
        //插枪认证成功
        if (message.getBody().getAuth() == 1) {
            //统一恢复初始化
            String sn = message.getBody().getAliSn();
            State state = sessionContext.getCgEfSessions().get(sn);
            if (state == null) {
                log.warn("没有找到该枪编号的没有认证信息,认证那里出问题了,桩={},枪={}", message.getBody().getSn(), message.getBody().getAliSn());
                return;
            }
            String orderNo = message.getBody().getOrderNo();
            state.setStatus(0x02);
            state.setOrderNo(orderNo);
            call(sessionContext.getSn(), sn, state);
        } else {
            log.warn("插枪认证失败,请重新插枪,body={}", message);
        }


    }

    public void call(String sn, String aliSn, State state) {
        SessionContext sessionContext = DeviceScaleService.sessions.get(sn);
        if (null == sessionContext) {
            return;
        }
        //0x00 无
        //0x01 设备编号不匹配
        //0x02 枪已在充电
        //0x03 设备故障
        //0x04 设备离线
        //0x05 未插枪
        //0x06 已插枪
        try {
            deviceScaleService.realDataSync(sessionContext, aliSn, state);
        } catch (Exception e) {
            log.error("realDataSync error", e);
        }
    }

    /**
     * 请求计费模式验证
     *
     * @param
     */
    private void req0X05(SessionContext sessionContext) {
        try {
            String sn = sessionContext.getSn();
            byte[] h1Start = new byte[]{0x68};
            byte[] h3seq = sessionContext.nextSeq();
            byte[] h4ts = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
            byte[] h5AES = new byte[]{0x00};
            byte[] h6CMD = new byte[]{计费模型验证请求};
            byte[] b1 = BCD.strToBcd(StringUtils.leftPad(sn, 14, "0"));//1 桩编号
            byte[] b2 = BCD.strToBcd("0000");//计费模型编号
            byte[] message = ByteUtil.mergeArrays(
                    b1,
                    b2
            );
            if (serverConfig.isCrypt()) {
                h5AES = new byte[]{0x01};
                message = AES128Decryptor.encrypt(message, sessionContext.getAesKey());

            }

            byte[] datas = ByteUtil.mergeArrays(h3seq, h4ts, h5AES, h6CMD, message);
            int crcInt = CrcChecksumUtil.calculateCrc(datas);
            byte[] h7CRC = ByteUtil.CRCByte2H(crcInt);
            byte[] h2Length = ByteUtil.intToByte2H(11 + message.length);
            byte[] reqData = ByteUtil.mergeArrays(h1Start, h2Length, datas, h7CRC);
            deviceScaleService.writeMessageToCloud(sessionContext, reqData, "发送【计费模式请求】消息,hex=");
        } catch (Exception e) {
            log.error("req0X05.error", e);
        }


    }

    @Mapping(types = 计费模型验证请求_响应, desc = "计费模型验证请求_响应")
    public void priceModeVerify(Message<PriceModeResp> message, SessionContext sessionContext) {
        //默认就是不一致,请求"充电桩计费模型请求"

        try {
            log.info("收到了【计费模型验证请求_响应】消息,body={}", message.getBody());
            log.info("将发送【计费模型验证请求响应】消息,sessionContext={}", sessionContext);
            byte[] 消息头 = new byte[]{0x68};
            byte[] 序列号 = sessionContext.nextSeq();
            byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
            byte[] 加密标志 = null;
            byte[] 响应CMD = new byte[]{(byte) 充电桩计费模型请求};
            byte[] body = BCD.strToBcd(message.getBody().getSn());//1 桩编号
            if (message.getEncryptionType()) {
                加密标志 = new byte[]{0x01};
                log.info("回复Body(加密前),hex={}", body);
                String aesKey = sessionContext.getAesKey();
                body = AES128Decryptor.encrypt(body, aesKey);
            } else {
                加密标志 = new byte[]{0x00};
                log.info("回复Body,hex={}", body);
            }
            byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
            byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
            int crcInt = CrcChecksumUtil.calculateCrc(datas);
            byte[] CRC = ByteUtil.CRCByte2H(crcInt);
            byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
            deviceScaleService.writeMessageToCloud(sessionContext, req, "发送【计费模型请求_A09】消息,hex=");
        } catch (Exception e) {
            log.error("startCharge.error", e);
        }


    }

    @Mapping(types = 心跳响应, desc = "心跳响应")
    public void heartBeat(Message<HeartBeatResponse> message, SessionContext sessionContext) {
        log.info("收到了【心跳响应】消息,body={}", message.getBody());
    }

    @Mapping(types = 二维码设置, desc = "二维码设置")
    public void setQr(Message<Req0x5B> message, SessionContext sessionContext) {
        log.info("收到了【二维码设置】消息,body={}", message.getBody());
        try {
            int seq = message.getSeq();
            byte[] 消息头 = new byte[]{0x68};
            byte[] 序列号 = ByteUtil.intToByte2L(seq);
            byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
            byte[] 加密标志 = null;
            byte[] 响应CMD = new byte[]{二维码设置应答};
            //---------------------------------start body -----------------------------

            byte[] 消息内容_桩编号 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
            byte[] 消息内容_枪号 = BCD.strToBcd(message.getBody().getAliSn());//1 枪号
            byte[] 消息内容_结果 = new byte[]{0x01};//成功
            byte[] body = ByteUtil.mergeArrays(消息内容_桩编号, 消息内容_枪号, 消息内容_结果);
            if (message.getEncryptionType()) {
                加密标志 = new byte[]{0x01};
                log.info("回复Body(加密前),hex={}", body);
                String aesKey = sessionContext.getAesKey();
                body = AES128Decryptor.encrypt(body, aesKey);
            } else {
                加密标志 = new byte[]{0x00};
                log.info("回复Body,hex={}", body);
            }
            //------------------------------- end body  -----------------------------------
            byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
            byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
            int crcInt = CrcChecksumUtil.calculateCrc(datas);
            byte[] CRC = ByteUtil.CRCByte2H(crcInt);
            byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
            deviceScaleService.writeMessageToCloud(sessionContext, req, "回复【二维码设置】响应消息,HEX=");
        } catch (Exception e) {
            log.error("【二维码设置】响应消息.error", e);
        }
    }


    @Mapping(types = 运营平台远程控制启机, desc = "运营平台远程控制启机")
    public void startCharge(Message<StartChargeA8Req> message, SessionContext sessionContext) {
        try {
            log.info("收到了【运营平台远程控制启机】消息,body={}", message.getBody());
            int seq = message.getSeq();
            byte[] 消息头 = new byte[]{0x68};
            byte[] 序列号 = ByteUtil.intToByte2L(seq);
            byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
            byte[] 加密标志 = null;
            byte[] 响应CMD = new byte[]{(byte) 运营平台远程控制启机_响应};
            //---------------------------------start body -----------------------------
            byte[] 消息内容_交流流水 = BCD.strToBcd(message.getBody().getOrderNo());//交易流水
            byte[] 消息内容_桩编号 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
            byte[] 消息内容_枪号 = BCD.strToBcd(message.getBody().getAliSn());//1 枪号
            State state = sessionContext.getCgEfSessions().get(message.getBody().getAliSn());
            byte[] 消息内容_启动结果 = null;
            byte[] 消息内容_失败原因 = null;
            //插抢状态
            if (null == state) {
                消息内容_启动结果 = new byte[]{0x00};
                消息内容_失败原因 = new byte[]{0x01};
            } else if (state.getStatus() == 0) {
                消息内容_启动结果 = new byte[]{0x00};
                消息内容_失败原因 = new byte[]{0x04};
            } else if (state.getStatus() == 1) {
                消息内容_启动结果 = new byte[]{0x00};
                消息内容_失败原因 = new byte[]{0x03};

            } else if (state.getStatus() == 3) {
                消息内容_启动结果 = new byte[]{0x00};
                消息内容_失败原因 = new byte[]{0x02};

            } else {
                消息内容_启动结果 = new byte[]{0x01};
                消息内容_失败原因 = new byte[]{0x00};
            }

            byte[] body = ByteUtil.mergeArrays(消息内容_交流流水, 消息内容_桩编号, 消息内容_枪号, 消息内容_启动结果, 消息内容_失败原因);
            if (message.getEncryptionType()) {
                加密标志 = new byte[]{0x01};
                log.info("回复Body(加密前),hex={}", body);
                String aesKey = sessionContext.getAesKey();
                body = AES128Decryptor.encrypt(body, aesKey);
            } else {
                加密标志 = new byte[]{0x00};
                log.info("回复Body,hex={}", body);
            }
            //------------------------------- end body  -----------------------------------
            byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
            byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
            int crcInt = CrcChecksumUtil.calculateCrc(datas);
            byte[] CRC = ByteUtil.CRCByte2H(crcInt);
            byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
            if (!deviceScaleService.writeMessageToCloud(sessionContext, req, "发送【运营平台远程控制启机响应】消息,hex=")) {
                throw new ServiceException("运营平台远程控制启机响应失败");
            }
            //成功
            if (消息内容_启动结果[0] == 0x01) {
                //设置成充电状态
                state.setStatus(0x03);
                state.setStartTime(LocalDateTime.now());
                state.setOrderNo(message.getBody().getOrderNo());
                req0x15(message, sessionContext, state);
            }
            log.info("回复【运营平台远程控制启机】响应消息,hex={}", req);
        } catch (
                Exception e) {
            log.error("startCharge.error", e);
        } finally {
            //上报
            State state = sessionContext.getCgEfSessions().get(message.getBody().getAliSn());
            if (null != state) {
                call(sessionContext.getSn(), message.getBody().getAliSn(), state);
            }

        }

    }

    private void req0x15(Message<StartChargeA8Req> message, SessionContext sessionContext, State state) throws Exception {

        byte[] 消息头 = new byte[]{0x68};
        byte[] 序列号 = sessionContext.nextSeq();
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = null;
        byte[] 响应CMD = new byte[]{(byte) 充电握手};
        //---------------------------------start body -----------------------------
        byte[] b1 = BCD.strToBcd(message.getBody().getOrderNo());//交易流水
        byte[] b2 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
        byte[] b3 = BCD.strToBcd(message.getBody().getAliSn());//1 枪号
        //插抢状态
        byte[] b4 = new byte[]{0x00, 0x01, 0x01};//
        byte[] b5 = new byte[]{0x01};//
        byte[] b6 = ByteUtil.intToByte2L(87);//
        byte[] b7 = ByteUtil.intToByte2L(57);//
        byte[] b8 = RandomUtils.nextByte(4);//
        byte[] b9 = RandomUtils.nextByte(4);//
        byte[] b10 = new byte[]{(byte) (2025 - 1985)};//
        byte[] b11 = new byte[]{0x01};//
        byte[] b12 = new byte[]{0x01};//
        byte[] b13 = new byte[]{0x00, 0x00, 0x00};//
        byte[] b14 = new byte[]{0x01};//
        byte[] b15 = new byte[]{0x01};//
        byte[] b16 = state.getVin().getBytes(StandardCharsets.US_ASCII);//
        byte[] b17 = new byte[]{0x01, 0x01, 0x01, 0x07, (byte) 0xDF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] body = ByteUtil.mergeArrays(b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12, b13, b14, b15, b16, b17);
        if (message.getEncryptionType()) {
            加密标志 = new byte[]{0x01};
            log.info("回复Body(加密前),hex={}", body);
            String aesKey = sessionContext.getAesKey();
            body = AES128Decryptor.encrypt(body, aesKey);
        } else {
            加密标志 = new byte[]{0x00};
            log.info("回复Body,hex={}", body);
        }
        //------------------------------- end body  -----------------------------------
        byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
        byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
        int crcInt = CrcChecksumUtil.calculateCrc(datas);
        byte[] CRC = ByteUtil.CRCByte2H(crcInt);
        byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
        deviceScaleService.writeMessageToCloud(sessionContext, req, "发送【充电握手】消息,hex=");

    }


    @Mapping(types = 运营平台远程停机, desc = "运营平台远程控制停机")
    public void stopCharge(Message<StopChargeA36Req> message, SessionContext sessionContext) {
        try {
            log.info("收到了【运营平台远程控制停机】消息,body={}", message.getBody());
            //获取枪的状态信息
            String aliSn = message.getBody().getAliSn();
            int seq = message.getSeq();
            byte[] 消息头 = new byte[]{0x68};
            byte[] 序列号 = ByteUtil.intToByte2L(seq);
            byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
            byte[] 加密标志 = null;
            byte[] 响应CMD = new byte[]{(byte) 运营平台远程停机_响应};

            byte[] 消息内容_桩编号 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
            byte[] 消息内容_枪号 = BCD.strToBcd(message.getBody().getAliSn());//1 枪号
            //    //0x00 无
            //    //0x01 设备编号不匹配
            //    //0x02 枪已在充电
            //    //0x03 设备故障
            //    //0x04 设备离线
            //    //0x05 未插枪
            State state = sessionContext.getCgEfSessions().get(aliSn);
            byte[] 消息内容_失败原因 = null;
            byte[] 消息内容_启动结果 = null;
            if (state == null || state.getStatus() != 3) {
                消息内容_启动结果 = new byte[]{0x00};
                消息内容_失败原因 = new byte[]{0x02};
            } else {
                消息内容_启动结果 = new byte[]{0x01};
                消息内容_失败原因 = new byte[]{0x03};
            }
            byte[] body = ByteUtil.mergeArrays(消息内容_桩编号, 消息内容_枪号, 消息内容_启动结果, 消息内容_失败原因);
            if (message.getEncryptionType()) {
                加密标志 = new byte[]{0x01};
                log.info("回复Body(加密前),hex={}", body);
                String aesKey = sessionContext.getAesKey();
                body = AES128Decryptor.encrypt(body, aesKey);
            } else {
                加密标志 = new byte[]{0x00};
                log.info("回复Body,hex={}", body);
            }
            byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
            byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
            int crcInt = CrcChecksumUtil.calculateCrc(datas);
            byte[] CRC = ByteUtil.CRCByte2H(crcInt);
            byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
            if (deviceScaleService.writeMessageToCloud(sessionContext, req, "回复【运营平台远程控制停机】响应消息,hex=")) {
                log.info("回复【运营平台远程控制停机】响应消息成功,hex={}", req);
                if (null != state) {
                    deviceScaleService.stopCharging(sessionContext, aliSn);
                    state.setStatus(2);
                    // state.clearOrderInfo();
                }
            }

        } catch (Exception e) {
            log.error("stopCharge.error", e);
        }
    }


    @Mapping(types = {充电桩计费模型响应, 计费模型设置}, desc = "充电桩计费模型响应&&计费模型设置")
    public void getModePriceFromCloud(Message<M0AResp> message, SessionContext sessionContext) {
        int cmdId = message.getCmd();
        if (cmdId == 充电桩计费模型响应) {
            log.info("收到了【充电桩计费模型响应】消息,body={}", message.getBody());
            M0AResp body = message.getBody();
            sessionContext.setPriceMode(body);
        } else {
            log.info("收到了【充电桩计费模型设置】消息,body={}", message.getBody());
            try {
                int seq = message.getSeq();
                byte[] 消息头 = new byte[]{0x68};
                byte[] 序列号 = ByteUtil.intToByte2L(seq);
                byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
                byte[] 加密标志 = null;
                byte[] 响应CMD = new byte[]{计费模型应答};
                //---------------------------------start body -----------------------------

                byte[] 消息内容_桩编号 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
                byte[] 消息内容_结果 = new byte[]{0x00};//成功
                byte[] body = ByteUtil.mergeArrays(消息内容_桩编号, 消息内容_结果);
                if (message.getEncryptionType()) {
                    加密标志 = new byte[]{0x01};
                    log.info("回复Body(加密前),hex={}", body);
                    String aesKey = sessionContext.getAesKey();
                    body = AES128Decryptor.encrypt(body, aesKey);
                } else {
                    加密标志 = new byte[]{0x00};
                    log.info("回复Body,hex={}", body);
                }
                //------------------------------- end body  -----------------------------------
                byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
                byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
                int crcInt = CrcChecksumUtil.calculateCrc(datas);
                byte[] CRC = ByteUtil.CRCByte2H(crcInt);
                byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
                deviceScaleService.writeMessageToCloud(sessionContext, req, "回复【充电桩计费模型设置】响应消息,hex=");
            } catch (Exception e) {
                log.info("【充电桩计费模型设置】响应消息,error,message", message, e);

            }
        }
    }

    @Mapping(types = 默认最大功率下发, desc = "默认最大功率下发")
    public void setKvFormCloud(Message<Req0x60> message, SessionContext sessionContext) {
        log.info("收到了【默认最大功率下发】消息,body={}", message.getBody());
        try {
            int seq = message.getSeq();
            byte[] 消息头 = new byte[]{0x68};
            byte[] 序列号 = ByteUtil.intToByte2L(seq);
            byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
            byte[] 加密标志 = null;
            byte[] 响应CMD = new byte[]{默认最大功率下发_应答};
            //---------------------------------start body -----------------------------

            byte[] 消息内容_桩编号 = BCD.strToBcd(message.getBody().getSn());//1 桩编号
            byte[] 消息内容_枪号 = BCD.strToBcd(message.getBody().getAliSn());//1 枪号
            byte[] 消息内容_结果 = new byte[]{0x00};//成功
            byte[] body = ByteUtil.mergeArrays(消息内容_桩编号, 消息内容_枪号, 消息内容_结果);
            if (message.getEncryptionType()) {
                加密标志 = new byte[]{0x01};
                log.info("回复Body(加密前),hex={}", body);
                String aesKey = sessionContext.getAesKey();
                body = AES128Decryptor.encrypt(body, aesKey);
            } else {
                加密标志 = new byte[]{0x00};
                log.info("回复Body,hex={}", body);
            }
            //------------------------------- end body  -----------------------------------
            byte[] 数据长度 = ByteUtil.intToByte2H(11 + body.length);
            byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, body);
            int crcInt = CrcChecksumUtil.calculateCrc(datas);
            byte[] CRC = ByteUtil.CRCByte2H(crcInt);
            byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
            deviceScaleService.writeMessageToCloud(sessionContext, req, "回复【默认最大功率下发】响应消息,hex=");
        } catch (Exception e) {
            log.info("【默认最大功率下发】响应消息,error,message", message, e);

        }

    }


}
