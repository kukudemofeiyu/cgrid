package com.things.cgomp.devicescale.encode;

import cn.hutool.core.util.HexUtil;
import com.things.cgomp.devicescale.annotation.Property;
import com.things.cgomp.devicescale.mapping.Handler;
import com.things.cgomp.devicescale.mapping.HandlerMapper;
import com.things.cgomp.devicescale.message.AbstractBody;
import com.things.cgomp.devicescale.message.Message;
import com.things.cgomp.devicescale.session.Attachment;
import com.things.cgomp.devicescale.utils.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.smartboot.socket.Protocol;
import org.smartboot.socket.transport.AioSession;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.things.cgomp.devicescale.message.DataType;

import static com.things.cgomp.devicescale.message.DataType.*;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
@Slf4j
public class CusByteArrayProtocol implements Protocol<Message> {

    private static byte H = 0x68;

    private HandlerMapper handlerMapper;

    public CusByteArrayProtocol(HandlerMapper handlerMapper) {
        this.handlerMapper = handlerMapper;
    }

    private int findStartFlag(ByteBuffer buffer) {
        for (int i = buffer.position(); i < buffer.limit(); i++) {
            if (buffer.get(i) == (byte) 0x68) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Message decode(ByteBuffer readBuffer, AioSession aioSession) {
        try {
            int remaining = readBuffer.remaining();
            if (remaining < Integer.BYTES) {
                return null;
            }
            int startIndex = findStartFlag(readBuffer);
            if (startIndex == -1) {
                //清除
                if (readBuffer.remaining() > 0) {
                    readBuffer.clear();
                }
                return null;
            }
            readBuffer.position(startIndex);
            byte startH = readBuffer.get();
            //log.info("message：{}", HexUtil.encodeHexStr(readBuffer.array()));
            log.info("头起始符={}", HexUtil.encodeHexStr(new byte[]{startH}));
            if (startH != 0x68) {
                return null;
            }
            //返回包最小不会少了,读取到数据长度位置
            if (readBuffer.remaining() < 2) {
                return null;
            }
            log.info("message：{}", HexUtil.encodeHexStr(readBuffer.array()));
            readBuffer.mark();
            int length = readBuffer.getShort() & 0xFFFF;
            //加上2个字节的CRC
            int pLength = 2 + length;
            log.info("头数据长度={}", HexUtil.encodeHexStr(ByteUtil.intToByte2H(length)));
            if (readBuffer.remaining() < pLength) {
                //重置到mark位置
                readBuffer.reset();
                return null;
            }
            byte[] bytes = new byte[pLength];
            readBuffer.get(bytes);
            // log.info("message：{}", HexUtil.encodeHexStr(bytes));

            byte[] seq = new byte[2];
            System.arraycopy(bytes, 0, seq, 0, seq.length);
            byte[] ts = new byte[7];
            System.arraycopy(bytes, 2, ts, 0, ts.length);
            //下标9
            byte encrypB = bytes[9];
            //下标10
            byte cmd = bytes[10];
            //2 CRC ,11==SEQ,TS,encrypB,upB
            byte[] body = new byte[pLength - 2 - 11];
            System.arraycopy(bytes, 11, body, 0, body.length);

            log.info("头序列号={}", HexUtil.encodeHexStr(seq));
            log.info("头ts={}", HexUtil.encodeHexStr(ts));
            log.info("头加密标志={}", HexUtil.encodeHexStr(new byte[]{encrypB}));
            log.info("头命令字={}", HexUtil.encodeHexStr(new byte[]{cmd}));
            log.info("消息内容={}", HexUtil.encodeHexStr(body));
//        System.out.println("消息内容{处理状态}:" + HexUtil.encodeHexStr(消息内容_状态));
//        System.out.println("消息内容{公钥}:" + HexUtil.encodeHexStr(消息内容_公钥));
//        System.out.println("CRC:" + HexUtil.encodeHexStr(CRC));
//        System.out.println("REQ:" + HexUtil.encodeHexStr(req));


            //CRC 校验
            byte[] crcB = new byte[2];
            System.arraycopy(bytes, bytes.length - 2, crcB, 0, crcB.length);
            int oldCRC = ByteUtil.byteHToI2nt(crcB);
            log.info("数据包中的DEC—CRC={},HEX-CRC={}", oldCRC, HexUtil.encodeHexStr(crcB));
            //减去2个CRC码的数据
            byte[] crcVAilData = new byte[bytes.length - 2];
            System.arraycopy(bytes, 0, crcVAilData, 0, crcVAilData.length);
            int culCrcB = CrcChecksumUtil.calculateCrc(crcVAilData);
            log.info("计算出的DEC—CRC={},HEX-CRC={}", culCrcB, HexUtil.encodeHexStr(ByteUtil.CRCByte2H(culCrcB)));
            if (culCrcB != oldCRC) {
                log.error("CRC校验失败,计算出的CRC={},数据包中的CRC={}", HexUtil.encodeHexStr(ByteUtil.CRCByte2H(culCrcB)), HexUtil.encodeHexStr(crcB));
                //先不校验
                if (readBuffer.remaining() > 0) {
                    readBuffer.clear();
                }
                return null;
            }

            System.arraycopy(bytes, 11, body, 0, body.length);
            //有加密
            if (0x01 == encrypB) {
                Attachment attachment = aioSession.getAttachment();
                String aesKey = attachment.getAesKey();
                body = AES128Decryptor.decrypt(body, aesKey);
            }
            int cmdU = cmd & 0xFF;
            Handler handler = handlerMapper.getHandler(cmdU);
            if (handler == null) {
                if (readBuffer.remaining() > 0) {
                    readBuffer.clear();
                }
                log.info("没有获取到对应的cmd:{}", cmdU);
                return null;
            }
            Type[] types = handler.getTargetParameterTypes();
            ParameterizedTypeImpl clazz = (ParameterizedTypeImpl) types[0];
            //message
            //Class<? extends AbstractMessage> messageClass = (Class<? extends AbstractMessage>) clazz.getRawType();
            //body
            Class<? extends AbstractBody> bodyClass = (Class<? extends AbstractBody>) clazz.getActualTypeArguments()[0];
            //   ProtocolMessage message = ProtocolMessage.builder().header(Header.builder().seq(ByteUtil.byteLToI2nt(seq))
            //          .time(ByteUtil.toDateString(ts)).encryp(encrypB == 0x01).
            //          cmd(cmd).build()).build().buildBody(aesBody == null ? body : aesBody);

            ByteBuf in = Unpooled.wrappedBuffer(body);
            log.info("body,hex={}", ByteBufUtil.hexDump(in));
            AbstractBody bodyObj = decode(in, bodyClass);
            Message message = new Message();
            message.setCmd(cmdU);
            message.setSeq(ByteUtil.byteLToI2nt(seq));
            message.setTs(ByteUtil.toDateString(ts));
            message.setEncryptionType(0x01 == encrypB ? true : false);
            message.setHandler(handler);
            message.setBody(bodyObj);
            return message;
            // return message;

        } catch (Exception e) {
            if (readBuffer.remaining() > 0) {
                readBuffer.clear();
            }
            return null;
        }


//            int length = readBuffer.getInt();
//            if (readBuffer.remaining() < length) {
//                readBuffer.reset();
//                return null;
//            } else {
//                byte[] bytes = new byte[length];
//                readBuffer.get(bytes);
//                return this.decode(bytes, session);
//            }


    }


//    public <T extends AbstractBody> AbstractMessage<T> decode(ByteBuf buf, Class<? extends AbstractMessage> clazz, Class<T> bodyClass) {
//        AbstractMessage message = decode(buf, clazz);
//        if (bodyClass != null) {
//
//            Integer headerLength = message.getHeaderLength();
//            int bodyLength = message.getBodyLength();
//            if (message.getEncryptionType()) {
//                //解密
//                buf = dataDecrypt.decrypt(message, buf, headerLength, bodyLength);
//
//            } else {
//                //  logger.info("receive_g_hex="+ByteBufUtil.hexDump(buf));
//                buf.setIndex(headerLength, headerLength + bodyLength);
//            }
//            log.info("消息体=" + ByteBufUtil.hexDump(buf));
//            T body = decode(buf, bodyClass);
//            message.setBody(body);
//        }
//        return message;
//    }

    public static <T> T decode(ByteBuf buf, Class<T> targetClass) {
        T result = BeanUtils.newInstance(targetClass);
        PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptor(targetClass);
        for (PropertyDescriptor pd : pds) {

            Method readMethod = pd.getReadMethod();
            Property prop = readMethod.getDeclaredAnnotation(Property.class);
            int length = getLength(result, prop);
            //     PropertyUtils.getLength(result, prop);
            if (!buf.isReadable(length)) {
                buf.clear();
                //把数据读完,以防配置错误,造成死循环
                break;
            }

            if (length == -1)
                length = buf.readableBytes();
            Object value = null;
            try {
                value = read(buf, prop, length, pd);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BeanUtils.setValue(result, pd.getWriteMethod(), value);
        }
        return result;
    }

    public static int getLength(Object obj, Property prop) {
        int length = prop.length();
        if (length == -1) {
            if ("".equals(prop.lengthName()))
                length = prop.type().length;
            else
                length = (int) BeanUtils.getValue(obj, prop.lengthName(), 0);
        }

        return length;
    }

    public static Object read(ByteBuf buf, Property prop, int length, PropertyDescriptor pd) {
        DataType type = prop.type();
        if (type == BYTE) {
            return (int) buf.readUnsignedByte();
        }
        if (type == UNSIGNED_SHORT_LE) {
            return buf.readUnsignedShortLE();
        } else if (type == CP56Time2a) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return toDateString(bytes);
        } else if (type == BCD) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return cn.hutool.core.codec.BCD.bcdToStr(bytes);
        } else if (type == BYTE_ASCII) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return new String(bytes, StandardCharsets.US_ASCII);
        } else if (type == BYTE_ASCII_BASE64) {
            byte[] bytes = new byte[length];
            buf.readBytes(bytes);
            return Base64.getEncoder().encodeToString(bytes);
        } else if (type == UNSIGNED_INT_LE) {
            return buf.readUnsignedIntLE();
        } else if (type == LIST) {
            List list = new ArrayList();
            Type clazz = ((ParameterizedType) pd.getReadMethod().getGenericReturnType()).getActualTypeArguments()[0];
            ByteBuf slice = buf.readSlice(length);
            log.info("list,hex={}", ByteBufUtil.hexDump(slice));
            while (slice.isReadable()) {
                list.add(decode(slice, (Class) clazz));
            }

            return list;
        }
        return null;
    }

    /**
     * Cp56Time2a转时间字符串
     *
     * @param bytes 字符数组
     * @return 时间字符串
     */
    public static String toDateString(byte[] bytes) {
        int milliseconds1 = bytes[0] < 0 ? 256 + bytes[0] : bytes[0];
        int milliseconds2 = bytes[1] < 0 ? 256 + bytes[1] : bytes[1];
        int milliseconds = milliseconds2 * 256 + milliseconds1;
        // 位于 0011 1111
        int minutes = bytes[2] & 0x3F;
        // 位于 0001 1111
        int hours = bytes[3] & 0x1F;
        // 位于 0001 1111
        int days = bytes[4] & 0x1F;
        // 位于 0000 1111
        int months = bytes[5] & 0x0F;
        // 位于 0111 1111
        int years = bytes[6] & 0x7F;
        return "20" + String.format("%02d", years) + "-" + String.format("%02d", months) + "-" + String.format("%02d", days) +
                " " + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":" +
                String.format("%02d", milliseconds / 1000);
    }

}
