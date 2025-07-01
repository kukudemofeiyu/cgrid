package com.things.cgomp.devicescale.service;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.HexUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.redis.service.RedisService;
import com.things.cgomp.devicescale.config.ProtocolConfig;
import com.things.cgomp.devicescale.domain.dto.DeviceInfo;
import com.things.cgomp.devicescale.encode.CusByteArrayProtocol;
import com.things.cgomp.devicescale.init.AfterStartUp;
import com.things.cgomp.devicescale.mapper.DeviceMapper;
import com.things.cgomp.devicescale.mapping.CusMessageProcessor;
import com.things.cgomp.devicescale.mapping.HandlerMapper;
import com.things.cgomp.devicescale.message.CmdId;
import com.things.cgomp.devicescale.message.resp.M0AResp;
import com.things.cgomp.devicescale.session.Attachment;
import com.things.cgomp.devicescale.session.SessionContext;
import com.things.cgomp.devicescale.session.State;
import com.things.cgomp.devicescale.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.smartboot.socket.transport.AioQuickClient;
import org.smartboot.socket.transport.AioSession;
import org.smartboot.socket.transport.WriteBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import static com.things.cgomp.devicescale.message.CmdId.*;


/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Slf4j
@Service
public class DeviceScaleService {

    public static final String QUEUE_ORDER_SUB = "ORDER_";

    public static Map<String /** 桩 **/, SessionContext> sessions = new ConcurrentHashMap();


    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private ProtocolConfig serverConfig;

    @Autowired
    private HandlerMapper handlerMapper;

    @Autowired
    private RedisService redisService;


    @AfterStartUp(order = AfterStartUp.MessageConsumer)
    public void init() throws Exception {

        new Thread(() -> {

            while (true) {
                try {
                    if (null != sessions) {
                        sessions.forEach((sn, session) -> {
                            //已认证通过,发心跳
                            if (session.getAuthPass()) {
                                AioSession clientSession = session.getSession();
                                if (!clientSession.isInvalid()) {
                                    heartbeat(sn, session);
                                    //定时上传实时数据
                                    realDataSync(session);
                                    //发送未成功的数据,简单搞一下
                                    Map<String /** Id **/, Pair<String, Long /**待发送到云的 hex，创建时间 **/>> data = session.getSyncHex();
                                    if (data != null) {
                                        Long nowTime = System.currentTimeMillis();
                                        data.forEach((k, v) -> {
                                            //大于10秒的数据才处理。成不成功都删除
                                            if (nowTime - v.getValue() > 10 * 1000) {
                                                writeMessageToCloud(session, HexUtil.decodeHex(v.getKey()), "数据补发HEX=");
                                                data.remove(k);
                                            }
                                        });
                                    }
                                    refreshSession(session);
                                }


                            }

                        });
                    }
                    Thread.sleep(15 * 1000);
                } catch (Exception e) {

                    log.error("session_manager", e);
                }
            }


        }, "session_manager").start();

    }

    private void realDataSync(SessionContext session) {
        try {
            Map<String /** 枪 **/, State> sessionContextMap = session.getCgEfSessions();
            if (null != sessionContextMap) {
                sessionContextMap.forEach((id, s) -> {
                    try {
                        realDataSync(session, id, s);

                    } catch (Exception e) {
                        log.error("realDataSync.send.error", e);
                    }

                });
            }
        } catch (Exception e) {
            log.error("realDataSync error", e);
        }
    }

    private void heartbeat(String deviceId, SessionContext session) {
        try {

            Map<String /** 枪 **/, State> sessionContextMap = session.getCgEfSessions();
            if (null != sessionContextMap) {
                sessionContextMap.forEach((id, s) -> {
                    byte[] h1Start = new byte[]{startHead};
                    byte[] h3seq = session.nextSeq();
                    byte[] h4ts = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
                    byte[] h5AES = new byte[]{N_AES};
                    byte[] h6CMD = new byte[]{0x03};
                    byte[] b1 = BCD.strToBcd(StringUtils.leftPad(deviceId, 14, "0"));//2 桩编号
                    byte[] b2 = BCD.strToBcd(id);//2抢编号
                    byte[] b3 = new byte[]{(byte) s.getStatus()}; //状态
                    byte[] message = ByteUtil.mergeArrays(
                            b1, // 1随机密钥
                            b2,         //2桩编码
                            b3//3桩类型
                    );
                    byte[] datas = ByteUtil.mergeArrays(h3seq, h4ts, h5AES, h6CMD, message);
                    int crcInt = CrcChecksumUtil.calculateCrc(datas);
                    byte[] h7CRC = ByteUtil.CRCByte2H(crcInt);
                    byte[] h2Length = ByteUtil.intToByte2H(11 + message.length);
                    byte[] reqData = ByteUtil.mergeArrays(h1Start, h2Length, datas, h7CRC);
                    writeMessageToCloud(session, reqData, "心跳请求HEX=");

                });

            }

        } catch (Exception e) {
            log.error("hearBeat error", e);
        }


    }

    public List<DeviceInfo> getList(DeviceInfo device) {

        return deviceMapper.selectCgPage(device);

    }

    public DeviceInfo getById(Long id) {
        QueryWrapper<DeviceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("device_id", id);
        return deviceMapper.selectOne(queryWrapper);
    }


    public void optDevice(DeviceInfo deviceInfo, Integer type) throws Exception {
        if (type == 1) {
            SessionContext sessionContext = checkCreateConnect(deviceInfo.getSn());
            if (sessionContext == null) {
                throw new ServiceException("创建连接失败");
            }
            if (!auth(deviceInfo.getSn(), sessionContext)) {
                throw new ServiceException("认证失败");
            }
        } else if (type == 0) {
            SessionContext sessionContext = sessions.get(deviceInfo.getSn());
            if (sessionContext == null) {
                return;
            }
            checkEfSessions(sessionContext);
            sessionContext.close();
            sessions.remove(deviceInfo.getSn());

        } else {
            QueryWrapper<DeviceInfo> deviceQueryWrapper = new QueryWrapper<>();
            deviceQueryWrapper.eq("device_id", deviceInfo.getParentId());
            DeviceInfo snDevice = deviceMapper.selectOne(deviceQueryWrapper);
            if (snDevice == null) {
                throw new ServiceException("没有找到枪对应的桩信息");
            }
            SessionContext sessionContext = sessions.get(snDevice.getSn());
            if (null == sessionContext) {
                throw new ServiceException("没有找到对应的连接信息,枪deviceId=" + deviceInfo.getDeviceId());
            }
            if (!sessionContext.getAuthPass()) {
                throw new ServiceException("请先上线桩,枪deviceId=" + snDevice.getDeviceId());
            }
            if (null == sessionContext.getSession() || sessionContext.getSession().isInvalid()) {
                throw new ServiceException("没有可用连接,请先上线桩,枪deviceId=" + snDevice.getDeviceId());
            }
            if (type == 2) {
                //插抢
                insertChargingGun(sessionContext, deviceInfo.getAliasSn());
            } else if (type == 3) {
                //桩主动停止充电
                stopCharging(sessionContext, deviceInfo.getAliasSn());
                State state = sessionContext.getCgEfSessions().get(deviceInfo.getAliasSn());
                if (state != null) {
                    state.setStatus(2);
                    //state.clearOrderInfo();
                }
            } else if (type == 4) {
                //拔枪
                pullChargingGun(sessionContext, deviceInfo.getAliasSn());
            } else {
                throw new ServiceException("没有找到对应的功能,type=" + type);
            }


        }


    }

    private void checkEfSessions(SessionContext session) {
        Map<String, State> cgEfSessions = session.getCgEfSessions();
        if (!CollectionUtils.isEmpty(cgEfSessions)) {
            // 若充电枪还在充电状态时，记录数据
            boolean existCharging = false;
            for (Map.Entry<String, State> entry : cgEfSessions.entrySet()) {
                if (entry.getValue().getStatus() == 0x03) {
                    existCharging = true;
                    break;
                }
            }
            if (existCharging) {
                refreshSession(session);
            } else {
                removeSession(session.getSn());
            }
        }
    }

    /**
     * 模拟拔枪
     *
     * @param sessionContext
     * @param
     */
    private void pullChargingGun(SessionContext sessionContext, String aliSn) throws Exception {
        State state = sessionContext.getCgEfSessions().get(aliSn);
        if (null == state) {
            throw new ServiceException("请确认枪编号是否正确,编号=" + aliSn);
        }
        //上报一次实时数据
        // realDataSync(sessionContext, aliSn, state);
        state.setStatus(0x02);
        state.setVin(null);
        realDataSync(sessionContext, aliSn, state);
        state.clearOrderInfo();


    }

    /**
     * 同步实时数据
     *
     * @param sessionContext
     * @param aliSn
     * @param state
     */
    public void realDataSync(SessionContext sessionContext, String aliSn, State state) throws Exception {
        if (state == null) {
            state = sessionContext.getCgEfSessions().get(aliSn);
            if (state == null) {
                return;
            }
        }
        byte[] h1Start = new byte[]{0x68};
        byte[] h3seq = sessionContext.nextSeq();
        byte[] h4ts = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] h5AES = new byte[]{0x00};
        byte[] h6CMD = new byte[]{(byte) 上传实时监测数据};
        String orderNo = state.getOrderNo() == null ? StringUtils.leftPad("0", 32, "0") : state.getOrderNo();
        byte[] b1 = BCD.strToBcd(orderNo);//订单号
        byte[] b2 = BCD.strToBcd(StringUtils.leftPad(sessionContext.getSn(), 14, "0"));//1 桩编号
        byte[] b3 = BCD.strToBcd(aliSn);//枪号
        int status = state.getStatus();
        byte[] b4 = new byte[]{(byte) status};

        byte[] b5 = new byte[]{0x00};
        byte[] b6 = null;
        //是否占位
        if (state.getVin() != null) {
            b6 = new byte[]{0x01};
        } else {
            b6 = new byte[]{0x00};
        }
        byte[] b7 = ByteUtil.intToByte2L(2205);
        byte[] b8 = ByteUtil.intToByte2L(1105);
        byte[] b9 = new byte[]{0x78};
        byte[] b10 = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] b11 = new byte[]{0x00};
        byte[] b12 = new byte[]{0x78};
        byte[] b13 = ByteUtil.intToByte2L(30);
        byte[] b14 = ByteUtil.intToByte2L(5);
        byte[] b15 = ByteUtil.intToByte4L(40000);
        byte[] b16 = ByteUtil.intToByte4L(1000);
        byte[] b17 = ByteUtil.intToByte4L(50000);
        byte[] b18 = ByteUtil.intToByte2L(0);
        byte[] b19 = new byte[]{0x78};
        byte[] b20 = new byte[]{0x00};
        byte[] b21 = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00};
        byte[] message = ByteUtil.mergeArrays(
                b1,
                b2,
                b3,
                b4,
                b5,
                b6,
                b7,
                b8,
                b9,
                b10,
                b11,
                b12,
                b13,
                b14,
                b15,
                b16,
                b17,
                b18,
                b19,
                b20,
                b21
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
        writeMessageToCloud(sessionContext, reqData, "上报实时数据请求HEX");
    }

    public boolean writeMessageToCloud(SessionContext sessionContext, byte[] buff, String desc) {
        synchronized (sessionContext.getSn().intern()) {
            try {
                log.info(desc + "{}", HexUtil.encodeHexStr(buff));
                AioSession session = sessionContext.getSession();
                if (!session.isInvalid()) {
                    WriteBuffer writeBuffer = session.writeBuffer();
                    writeBuffer.write(buff);
                    writeBuffer.flush();
                    return true;
                } else {
                    log.error("连接不可用,sn={}", sessionContext.getSn());
                    return false;
                }
            } catch (Exception e) {
                log.error("发送数据失败,sn={}", sessionContext.getSn(), e);
                return false;
            }


        }
    }

    public void stopCharging(SessionContext sessionContext, String aliSn) throws Exception {
        if (null == sessionContext || sessionContext.getSession().isInvalid()) {
            throw new ServiceException("连接不可用,请先启动模拟桩");
        }
        String sn = sessionContext.getSn();
        State state = sessionContext.getCgEfSessions().get(aliSn);
        if (null == state) {
            throw new ServiceException("请确认枪编号是否正确,编号=" + aliSn);
        }
        if (state.getStatus() != 0x03 || state.getOrderNo() == null) {
            throw new ServiceException("该枪请未处于充电状态,编号=" + aliSn);
        }
        byte[] h1Start = new byte[]{0x68};
        byte[] h3seq = sessionContext.nextSeq();
        byte[] h4ts = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] h5AES = new byte[]{0x00};
        byte[] h6CMD = new byte[]{(byte) 充电结束};
        byte[] b1 = BCD.strToBcd(state.getOrderNo());//订单号
        byte[] b2 = BCD.strToBcd(StringUtils.leftPad(sn, 14, "0"));//1 桩编号
        byte[] b3 = BCD.strToBcd(aliSn);//枪号
        byte[] b4 = new byte[]{0x58};
        byte[] b5 = ByteUtil.intToByte2L(2112);
        byte[] b6 = ByteUtil.intToByte2L(2013);
        byte[] b7 = new byte[]{0x5a};
        byte[] b8 = new byte[]{0x78};
        byte[] b9 = ByteUtil.intToByte2L(120);
        byte[] b10 = ByteUtil.intToByte2L(70);//
        byte[] b11 = ByteUtil.intToByte4L(40521);//
        byte[] message = ByteUtil.mergeArrays(
                b1,
                b2,
                b3,
                b4,
                b5,
                b6,
                b7,
                b8,
                b9,
                b10,
                b11
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
        writeMessageToCloud(sessionContext, reqData, "上报充电结束请求HEX");
        //开始上报订单
        upLoadOrderInfo(state.getOrderNo(), state.getVin(), state.getStartTime(), LocalDateTime.now(), sessionContext, aliSn);


    }

    /**
     * 上报订单
     *
     * @param
     * @param sessionContext
     */
    public void upLoadOrderInfo(String orderNo, String vin, LocalDateTime startTime, LocalDateTime endTime, SessionContext sessionContext, String aliSn) {

        //认证响应消息
        //消息头,开头符：68,数据长度：9f00,序列号:0100,发送时间:a6da0b10060319,加密标志:00,帧类型标志:01
        byte[] 消息头 = new byte[]{0x68};
        //要跟请求对应起来
        byte[] 序列号 = sessionContext.nextSeq();
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = new byte[]{0x00};
        byte[] 响应CMD = new byte[]{CmdId.交易记录};

        byte[] 消息内容_订单号 = BCD.strToBcd(orderNo);//订单号
        byte[] 消息内容_桩编号 = BCD.strToBcd(sessionContext.getSn());
        byte[] 消息内容_枪号 = BCD.strToBcd(aliSn);
        byte[] 消息内容_开始时间 = ByteUtil.encodeCP56Time2a(startTime);
        byte[] 消息内容_结束时间 = ByteUtil.encodeCP56Time2a(endTime);
        byte[] 消息内容_电表表号 = BCD.strToBcd(String.format("%0" + 12 + "d", 554201));
        byte[] 消息内容_电表密文 = RandomUtils.nextByte(34);
        byte[] 消息内容_电表协议版本号 = new byte[]{0x01, 0x00};
        byte[] 消息内容_加密方式 = new byte[]{0x00};
        byte[] 消息内容_电表总起值 = ByteUtil.mergeArrays(new byte[]{0x00}, ByteUtil.intToByte4L(1110000));
        byte[] 消息内容_电表总止值 = ByteUtil.mergeArrays(new byte[]{0x00}, ByteUtil.intToByte4L(1120000));
        //计费模型
        M0AResp priceMode = sessionContext.getPriceMode();


        byte[] 消息内容_总电量 = ByteUtil.intToByte4L(1120000);
        byte[] 消息内容_计损总电量 = ByteUtil.intToByte4L(0);
        //TODO 后面再算,先随便给个
        byte[] 消息内容_消费金额 = ByteUtil.intToByte4L(100000);
        byte[] 消息内容_电动汽车唯一标识 = vin.getBytes(StandardCharsets.US_ASCII);
        byte[] 消息内容_交易标识 = new byte[]{0x01};
        byte[] 消息内容_交易日期时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 消息内容_停止原因 = new byte[]{0x40};
        byte[] 消息内容_物理卡号 = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x02};
        int int_费率个数 = 48;
        byte[] 消息内容_费率个数 = new byte[]{(byte) int_费率个数};
        //一个占8个字节
        // byte[] 消息内容_费率 = new byte[int_费率个数 * 8];
        byte[][] 费率单价 = new byte[int_费率个数][4];
        byte[][] 费率电量 = new byte[int_费率个数][4];
        byte[][] 费率计损电量 = new byte[int_费率个数][4];
        byte[][] 费率金额 = new byte[int_费率个数][4];
        int pri = 2;
        for (int i = 0; i < int_费率个数; i++) {
            费率单价[i] = ByteUtil.intToByte4L(pri);
            费率电量[i] = ByteUtil.intToByte4L(pri);
            费率计损电量[i] = ByteUtil.intToByte4L(pri);
            费率金额[i] = ByteUtil.intToByte4L(pri);
            pri++;
        }

        byte[] 消息内容_费率单价 = ByteUtil.mergeArrays(费率单价);
        byte[] 消息内容_费率电量 = ByteUtil.mergeArrays(费率电量);
        byte[] 消息内容_费率计损电量 = ByteUtil.mergeArrays(费率计损电量);
        byte[] 消息内容_费率金额 = ByteUtil.mergeArrays(费率金额);

        byte[] 消息内容_费率 = ByteUtil.mergeArrays(消息内容_费率单价, 消息内容_费率电量, 消息内容_费率计损电量, 消息内容_费率金额);


        byte[] 响应消息内容 = ByteUtil.mergeArrays(
                消息内容_订单号, 消息内容_桩编号, 消息内容_枪号, 消息内容_开始时间, 消息内容_结束时间,
                消息内容_电表表号, 消息内容_电表密文, 消息内容_电表协议版本号, 消息内容_加密方式, 消息内容_电表总起值,
                消息内容_电表总止值, 消息内容_总电量, 消息内容_计损总电量, 消息内容_消费金额, 消息内容_电动汽车唯一标识,
                消息内容_交易标识, 消息内容_交易日期时间, 消息内容_停止原因, 消息内容_物理卡号, 消息内容_费率个数, 消息内容_费率);
        byte[] 数据长度 = ByteUtil.intToByte2H(11 + 响应消息内容.length);
        byte[] datas = ByteUtil.mergeArrays(序列号, 发送时间, 加密标志, 响应CMD, 响应消息内容);
        int crcInt = CrcChecksumUtil.calculateCrc(datas);
        byte[] CRC = ByteUtil.CRCByte2H(crcInt);
        byte[] req = ByteUtil.mergeArrays(消息头, 数据长度, datas, CRC);
        System.out.println("头{起始符}:" + HexUtil.encodeHexStr(消息头));
        System.out.println("头{数据长度}:" + HexUtil.encodeHexStr(数据长度));
        System.out.println("头{序列号}:" + HexUtil.encodeHexStr(序列号));
        System.out.println("头{发送时间}:" + HexUtil.encodeHexStr(发送时间));
        System.out.println("头{加密标志}:" + HexUtil.encodeHexStr(加密标志));
        System.out.println("头{命令字}:" + HexUtil.encodeHexStr(响应CMD));
        System.out.println("消息内容{桩SN}:" + HexUtil.encodeHexStr(消息内容_桩编号));
        System.out.println("消息内容{消息内容_费率个数}:" + HexUtil.encodeHexStr(消息内容_费率个数));
        System.out.println("消息内容{消息内容_费率}:" + HexUtil.encodeHexStr(消息内容_费率));

        System.out.println("消息内容{全部}:" + HexUtil.encodeHexStr(响应消息内容));
        System.out.println("CRC:" + HexUtil.encodeHexStr(CRC));
        String reqHex = HexUtil.encodeHexStr(req);
        System.out.println("REQ:" + reqHex);
        //把数据加入待发送队列,收到回复再删除
        sessionContext.addSyncQueue(QUEUE_ORDER_SUB + orderNo, Pair.of(reqHex, System.currentTimeMillis()));
        writeMessageToCloud(sessionContext, req, "上报交易记录HEX");


    }

    private void insertChargingGun(SessionContext sessionContext, String aliSn) throws Exception {
        if (null == sessionContext || sessionContext.getSession().isInvalid()) {
            throw new ServiceException("连接不可用,请先启动模拟桩");
        }
        String sn = sessionContext.getSn();
        State state = sessionContext.getCgEfSessions().get(aliSn);
        if (null == state) {
            throw new ServiceException("请确认枪编号是否正确,id=" + aliSn);
        }
        if (state.getStatus() == 0x03) {
            throw new ServiceException("枪口正在充电,请先停止充电再插枪");
        }
        byte[] h1Start = new byte[]{0x68};
        byte[] h3seq = sessionContext.nextSeq();
        byte[] h4ts = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] h5AES = new byte[]{0x00};
        byte[] h6CMD = new byte[]{(byte) 插枪_充电桩上报vin码};
        byte[] b1 = BCD.strToBcd(StringUtils.leftPad(sn, 14, "0"));//1 桩编号
        byte[] b2 = BCD.strToBcd(aliSn);//枪号
        //随便生成15位VIN
        String vin = "BI" + RandomUtils.nextNumber(15);
        byte[] b3 = new StringBuilder(vin).reverse().toString().getBytes(StandardCharsets.US_ASCII);
        byte[] b4 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] message = ByteUtil.mergeArrays(
                b1,
                b2,
                b3,
                b4
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
        //将vin码保存起来,并马上报状态
        state.setVin(vin);
        realDataSync(sessionContext, aliSn, state);

//        不报A9指令
//        if (writeMessageToCloud(sessionContext, reqData, "插枪请求HEX")) {
//            //将vin码保存起来
//            state.setVin(vin);
//        }


    }

    private SessionContext checkCreateConnect(String cpId) throws Exception {
        /** 桩 **/
        SessionContext sessionContext = sessions.get(cpId);
        if (null == sessionContext || sessionContext.getSession().isInvalid()) {


            try {
                sessionContext = getAioSession(cpId);
            } catch (Exception e) {
                log.error("创建连接失败", e);
                throw new ServiceException("连接失败");
            }
            if (null == sessionContext || sessionContext.getSession() == null || sessionContext.getSession().isInvalid()) {
                log.error("创建连接失败,会话不可用");
                throw new ServiceException("连接失败,会话不可用");
            }
            sessions.put(cpId, sessionContext);
        }
        return sessionContext;

    }

    //头
    byte startHead = 0x68;

    //不加密
    byte N_AES = 0x00;

    //加密
    byte AES = 0x01;

    byte AUTH_CMD = 0x01;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    BlockResponseService blockResponseService;

    public static void main(String[] args) {
        String sn = "1002501000001";
        System.out.println(StringUtils.leftPad(sn, 14, "0"));
    }

    private boolean auth(String sn, SessionContext session) {

        byte[] seq = session.nextSeq();
        byte[] ts = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        String binKey = RandomKeyGenerator.generateRandomKey();
        log.info("1随机密钥ST:{}", binKey);
        try {
            String publicKey = geRASPublicKey();
            String hexSecret = RSAUtils.encryptRSADefault(publicKey, binKey);
            byte[] b1 = hexSecret.getBytes(StandardCharsets.US_ASCII);//1 随机密钥
            byte[] b2 = BCD.strToBcd(StringUtils.leftPad(sn, 14, "0"));//2 桩编号
            byte[] b3 = new byte[]{0x01}; //3桩类型
            byte[] b4 = new byte[]{0x01}; //4充电枪数量
            byte[] b5 = new byte[]{0x01, 0x00, 0x0B}; //5通信协议版本
            byte[] b6 = new byte[]{0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01, 0x01};//6程序版本
            byte[] b7 = new byte[]{0x00};//7 网络链接类型
            byte[] b8 = BCD.strToBcd(StringUtils.leftPad(sn, 20, "0"));//8 "sim卡
            byte[] b9 = new byte[]{0x03};//9 运营商
            byte[] b10 = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//10 Token
            byte[] b11 = new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};//11 手机号码
            byte[] b12 = new byte[]{0x00};//12 支持网络制式
            byte[] b13 = new byte[]{0x00};//13 当前网络制式
            byte[] b14 = new byte[]{0x10, 0x17, (byte) 0xDF, (byte) 0x80};//14 经度
            byte[] b15 = new byte[]{0x02, (byte) 0xAE, (byte) 0xA5, 0x40};//15 纬度
            byte[] message = ByteUtil.mergeArrays(
                    b1, // 1随机密钥
                    b2,         //2桩编码
                    b3, //3桩类型
                    b4, //4充电枪数量
                    b5, //5通信协议版本
                    b6,//6程序版本
                    b7,//7 网络链接类型
                    b8,//8 Sim
                    b9,
                    b10,
                    b11,
                    b12,
                    b13,
                    b14,
                    b15
            );
            byte[] aesF = new byte[]{N_AES};
            byte[] datas = ByteUtil.mergeArrays(seq, ts, aesF, new byte[]{AUTH_CMD}, message);
            int crcInt = CrcChecksumUtil.calculateCrc(datas);
            byte[] crc = ByteUtil.CRCByte2H(crcInt);
            byte[] length = ByteUtil.intToByte2H(11 + message.length);
            byte[] reqData = ByteUtil.mergeArrays(new byte[]{startHead}, length, datas, crc);
            log.info("消息头,开头符：{},数据长度：{},序列号:{},发送时间:{},加密标志:{},帧类型标志:{}",
                    HexUtil.encodeHexStr(new byte[]{0x68}),
                    HexUtil.encodeHexStr(length),
                    HexUtil.encodeHexStr(seq),
                    HexUtil.encodeHexStr(ts),
                    HexUtil.encodeHexStr(aesF),
                    HexUtil.encodeHexStr(new byte[]{AUTH_CMD}));
            log.info("1随机密钥:{}", HexUtil.encodeHexStr(b1));
            log.info("2桩编号:{}", HexUtil.encodeHexStr(b2));
            log.info("3桩类型:{}", HexUtil.encodeHexStr(b3));
            log.info("4充电枪数量:{}", HexUtil.encodeHexStr(b4));
            log.info("5通信协议版本:{}", HexUtil.encodeHexStr(b5));
            log.info("6程序版本:{}", HexUtil.encodeHexStr(b6));
            log.info("7网络链接类型:{}", HexUtil.encodeHexStr(b7));
            log.info("8sim卡:{}", HexUtil.encodeHexStr(b8));
            log.info("9运营商:{}", HexUtil.encodeHexStr(b9));
            log.info("10Token:{}", HexUtil.encodeHexStr(b10));
            log.info("11手机号码:{}", HexUtil.encodeHexStr(b11));
            log.info("12支持网络制式:{}", HexUtil.encodeHexStr(b12));
            log.info("13当前网络制式:{}", HexUtil.encodeHexStr(b13));
            log.info("14经度:{}", HexUtil.encodeHexStr(b14));
            log.info("15纬度：{}", HexUtil.encodeHexStr(b15));
            log.info("CRC {}", HexUtil.encodeHexStr(crc));

            if (writeMessageToCloud(session, reqData, "认证请求HEX")) {
                AioSession aioSession = session.getSession();
                //绑定
                aioSession.setAttachment(Attachment.builder().aesKey(binKey).sn(sn).build());
                session.setAESSecret(binKey);
            }


        } catch (Exception e) {
            log.error("发送认证报文,失败", e);
            return false;
        }
        return true;


    }


    private String geRASPublicKey() throws IOException {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Resource resource = resourceLoader.getResource("classpath:RAS_PUBLIC_KEY");
            is = resource.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);
            StringBuffer stringBuffer = new StringBuffer();
            String data = null;
            while ((data = br.readLine()) != null) {
                stringBuffer.append(data);
            }
            return stringBuffer.toString();
        } finally {
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (is != null) {
                is.close();
            }

        }


    }

    private SessionContext getAioSession(String sn) throws IOException {
        SessionContext sessionContext = new SessionContext(sn);
        AioQuickClient aioQuickClient = new AioQuickClient(serverConfig.getServerIp(), serverConfig.getServerPort(), new CusByteArrayProtocol(handlerMapper), new CusMessageProcessor(sessionContext));
        aioQuickClient.setWriteBuffer(1024 * 1024, 512);
        AioSession aioSession = aioQuickClient.start();
        sessionContext.setSession(aioSession);
        return sessionContext;

    }


    public List<DeviceInfo> getListByParentId(Long deviceId) {
        QueryWrapper<DeviceInfo> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("parent_id", deviceId);
        return deviceMapper.selectList(deviceQueryWrapper);
    }

    public DeviceInfo getDeviceInfoBySn(String sn) {
        QueryWrapper<DeviceInfo> deviceQueryWrapper = new QueryWrapper<>();
        deviceQueryWrapper.eq("sn", sn);
        return deviceMapper.selectOne(deviceQueryWrapper);

    }

    private void refreshSession(SessionContext session) {
        String key = "device_scale:" + session.getSn();
        redisService.setCacheObject(key, JSON.toJSONString(session), 2L, TimeUnit.HOURS);
    }

    private void removeSession(String sn) {
        String key = "device_scale:" + sn;
        redisService.deleteObject(key);
    }
}
