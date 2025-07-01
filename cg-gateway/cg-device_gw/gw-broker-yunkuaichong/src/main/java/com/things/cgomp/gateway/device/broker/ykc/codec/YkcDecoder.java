package com.things.cgomp.gateway.device.broker.ykc.codec;

import cn.hutool.core.util.HexUtil;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.YkcMessageIn;
import com.things.cgomp.gateway.device.broker.ykc.constant.YkcFrameConstants;
import com.things.cgomp.gateway.device.broker.ykc.session.YkcSessionContext;
import com.things.cgomp.gateway.device.broker.ykc.utils.AES128Decryptor;
import com.things.cgomp.gateway.device.broker.ykc.utils.Cp56Time2aUtils;
import com.things.cgomp.gateway.device.broker.ykc.utils.CrcChecksumUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 解码 云快充V2.0.1加密版协议
 */
@Slf4j
public class YkcDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        //报文总字节数
        int readableLength = byteBuf.readableBytes();
        byte[] dataBytes = new byte[readableLength];
        byteBuf.readBytes(dataBytes);
        //原始报文hex字符串
        String originHex = HexUtil.encodeHexStr(dataBytes);
        byteBuf.resetReaderIndex();

        try {

            // CRC校验,crc大端模式
            checkCrc(byteBuf, originHex, readableLength);

            // 起始标志
            byte frameStartFlag = byteBuf.readByte();
            if (frameStartFlag != YkcFrameConstants.frame_start) {
                log.error("frameStart error data package: {}", originHex);
                throw new RuntimeException("frameStart error");
            }

            // 数据长度
            int dataLength = byteBuf.readUnsignedShort();
            // 帧序列号
            Integer frameSerialNo = byteBuf.readUnsignedShortLE();
            // 发送时间
            byte[] timeBytes = new byte[7];
            byteBuf.readBytes(timeBytes);
            Long eventTs = Cp56Time2aUtils.decodeCP56Time2aToLong(timeBytes);
            // 加密标志
            Boolean cryptoFlag = byteBuf.readByte() == YkcFrameConstants.frame_crypto;
            // 帧类型标志
            Short frameType = byteBuf.readUnsignedByte();
            String frameTypeHex = String.format("%04x", frameType);

            String randomKey = "";
            if(cryptoFlag){
                randomKey = channelHandlerContext.attr(YkcSessionContext.DEVICE_SECRET_KEY).get();
                if (StringUtils.isEmpty(randomKey)) {
                    log.error("randomKey isEmpty, data hex: {}", originHex);
                    throw new RuntimeException("aesKey empty");
                }
            }

            // 消息体
            ByteBuf encryptBody = getEncryptBody(byteBuf, cryptoFlag, randomKey, originHex);
            byte[] encryptBodyByte = new byte[encryptBody.readableBytes()];
            encryptBody.readBytes(encryptBodyByte);
            //数据域 解密后的hex字符串
            String decryptHexBoby = HexUtil.encodeHexStr(encryptBodyByte);
            encryptBody.resetReaderIndex();

            log.info("receive , frameSeq:{}, frameType: {} , ts={}, origin:{}, hex: {}", frameSerialNo, frameTypeHex, eventTs, originHex, decryptHexBoby);

            YkcMessageIn ykcMessageIn = YkcMessageFactory.newMessage(frameType.intValue(), encryptBody);
            if (ykcMessageIn != null) {
                ykcMessageIn.setTs(eventTs);
                ykcMessageIn.setMessageType(frameType.intValue());
                ykcMessageIn.setCryptoFlag(cryptoFlag);
                ykcMessageIn.setOriginHex(originHex);
                ykcMessageIn.setFrameSerialNo(frameSerialNo);
                ykcMessageIn.setBody(decryptHexBoby);
                out.add(ykcMessageIn);
            }

        } catch (Exception e) {
            log.error("decoder error, hex={} ", originHex, e);

            YkcMessageIn ykcMessageIn = new YkcMessageIn();
            ykcMessageIn.setOriginHex(originHex);
            out.add(ykcMessageIn);

        }

    }

    private void checkCrc(ByteBuf byteBuf, String originHex, int readableLength) {
        //截取crc字符
        String subCrcstring = originHex.substring(readableLength * 2 - 2 * 2);
        //读取crc字节
        byte[] calculateBytes = new byte[readableLength - 5];
        ByteBuf calDataSlice = byteBuf.slice(3, readableLength - 5);
        calDataSlice.readBytes(calculateBytes);
        Integer calculateCrc = CrcChecksumUtil.calculateCrc(calculateBytes);
        String crcValStr = StringUtils.leftPad(Integer.toHexString(calculateCrc), 4, "0");
        if (!subCrcstring.equals(crcValStr)) {
            log.error("crc error , 计算crc:{}, 截取crc:{}, data package: {}", crcValStr, subCrcstring, originHex);
            throw new RuntimeException("crc error");
        }
    }

    private ByteBuf getEncryptBody(ByteBuf byteBuf, Boolean cryptoFlag, String randomKey, String originHex) {
        ByteBuf messageBody = byteBuf.copy(byteBuf.readerIndex(), byteBuf.readableBytes() - 2);
        // 解密后消息体
        ByteBuf encryptBody = null;
        try {
            if (cryptoFlag) {
                byte[] bodyData = new byte[messageBody.readableBytes()];
                messageBody.readBytes(bodyData);
                byte[] bytes = AES128Decryptor.decrypt(bodyData, randomKey);

                return Unpooled.buffer().writeBytes(bytes);

            }else {
                return messageBody;
            }

        }catch (Exception e){
            log.error("encrypt, randomKey={}, hex={}", randomKey, originHex, e);
            if(messageBody != null){
                messageBody.release();
            }
            if(encryptBody != null){
                encryptBody.release();
            }

            throw new RuntimeException("解密失败");
        }finally {

            if (encryptBody != null) {
                ReferenceCountUtil.safeRelease(messageBody);
            }
        }

    }

}
