package com.things.cgomp.gateway.device.broker.ykc.codec;

import cn.hutool.core.util.HexUtil;
import com.things.cgomp.gateway.device.broker.ykc.codec.out.YkcMessageOut;
import com.things.cgomp.gateway.device.broker.ykc.constant.YkcFrameConstants;
import com.things.cgomp.gateway.device.broker.ykc.message.YkcAbstractMessageHandler;
import com.things.cgomp.gateway.device.broker.ykc.utils.AES128Decryptor;
import com.things.cgomp.gateway.device.broker.ykc.utils.Cp56Time2aUtils;
import com.things.cgomp.gateway.device.broker.ykc.utils.CrcChecksumUtil;
import com.things.cgomp.gateway.device.broker.ykc.utils.DeviceOptHandlerHelper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

@Slf4j
@Service
public class YkcEncoder  {


    /**
     * 云快充编码
     * @param byteBuf
     * @param ykcMessageOut
     * @param resKey
     * @return
     * @throws Exception
     */
    public Boolean encode(ByteBuf byteBuf, YkcMessageOut ykcMessageOut ,String resKey)  {

        // 帧开始标志
        byteBuf.writeByte(YkcFrameConstants.frame_start);
        // 数据长度
        byteBuf.writeBytes(new byte[]{0x00, 0x00});
        // 帧序列号
        byteBuf.writeShortLE(ykcMessageOut.getFrameSerialNo());
        // 发送时间
        LocalDateTime now = LocalDateTime.now();
        byte[] timeBytes = Cp56Time2aUtils.encodeCP56Time2a(now);
        byteBuf.writeBytes(timeBytes);
        // 加密标志
        byteBuf.writeByte(ykcMessageOut.getCryptoFlag() ? 0x01 : 0x00);
        // 帧类型
        Integer deviceCode = ykcMessageOut.getMessageType();
        byteBuf.writeByte(deviceCode);
        // 数据域
        YkcAbstractMessageHandler<?, YkcMessageOut> handler = DeviceOptHandlerHelper.getHandler(ykcMessageOut.getMessageType());
        if (Objects.isNull(handler)) {
            log.error("message not matched handler: {}", ykcMessageOut);
            return false;
        }

        //未加密的十六进制字符串
        String body = "";

        //加密
        ByteBuf originData = Unpooled.buffer();
        try {

            if (ykcMessageOut.getCryptoFlag()) {

                handler.write(originData, ykcMessageOut);

                byte[] originBytes = new byte[originData.readableBytes()];
                originData.readBytes(originBytes);
                body = HexUtil.encodeHexStr(originBytes);
                //AES加密
                byte[] encryptByte = AES128Decryptor.encrypt(originBytes, resKey);

                byteBuf.writeBytes(encryptByte);

            } else {

                handler.write(originData, ykcMessageOut);
                byte[] originBytes = new byte[originData.readableBytes()];
                originData.readBytes(originBytes);
                body = HexUtil.encodeHexStr(originBytes);
                byteBuf.writeBytes(originBytes);
            }
        } catch (Exception e) {
            log.error("encryptWrite error, MsgOut:{}", ykcMessageOut, e);
            return false;

        } finally {

            originData.release();
        }


        // 计算数据长度，序列号域+发送时间+加密标志+帧类型标志+消息体
        int dataLength = byteBuf.readableBytes() - 3;
        byteBuf.setShort(1, dataLength);
        // 帧校验，从序列号域到数据域的CRC校验
        byteBuf.readerIndex(3);
        byte[] data = new byte[dataLength];
        byteBuf.readBytes(data);
        int cryptoValue = CrcChecksumUtil.calculateCrc(data);
        byteBuf.writeShort(cryptoValue);
        // 十六进制消息
        byteBuf.resetReaderIndex();
        byte[] sendBytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(sendBytes);
        String dataHex = HexUtil.encodeHexStr(sendBytes);
        byteBuf.resetReaderIndex();

        long sendTs = now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        ykcMessageOut.setTs(sendTs);
        ykcMessageOut.setBody(body);
        ykcMessageOut.setOriginHex(dataHex);
        log.info("send deviceNo: {}, frameSeq:{}, frameType: {}, ts:{} hex: {}",  ykcMessageOut.getDeviceNo(), ykcMessageOut.getFrameSerialNo(), String.format("%04x", deviceCode),sendTs, dataHex);

        return true;
    }
}