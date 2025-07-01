package com.things.cgomp.gateway.device.broker.ykc.utils;

import cn.hutool.core.util.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Cp56Time2a毫秒部分是小端模式
 */
@Slf4j
public class Cp56Time2aUtils {

    // 编码当前时间为 CP56Time2a 格式
    public static byte[] encodeCP56Time2a(LocalDateTime dateTime) {
        ByteBuffer buffer = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN);

        buffer.put((byte) (dateTime.getMinute() & 0x3F));
        buffer.put((byte) (dateTime.getHour() & 0x1F));
        buffer.put((byte) (dateTime.getDayOfMonth() & 0x1F));
        buffer.put((byte) (dateTime.getMonthValue() & 0x0F));
        buffer.put((byte) ((dateTime.getYear() % 100) & 0x7F));

        return buffer.array();
    }

    // 解码 CP56Time2a 格式为 LocalDateTime
    public static LocalDateTime decodeCP56Time2a(byte[] bytes) {
        try {
            int milliseconds1 = bytes[0] < 0 ? 256 + bytes[0] : bytes[0];
            int milliseconds2 = bytes[1] < 0 ? 256 + bytes[1] : bytes[1];
            int milliseconds = milliseconds2 * 256 + milliseconds1;

            int seconds = milliseconds / 1000;
            int nanos = (milliseconds % 1000) * 1000000;

            // 位于 0011 1111
            int minutes = bytes[2] & 0x3F;
            // 位于 0001 1111
            int hours = bytes[3] & 0x1F;
            // 位于 0001 1111
            int days = bytes[4] & 0x1F;
            // 位于 0000 1111
            int months = bytes[5] & 0x0F;
            // 位于 0111 1111
            int years = (bytes[6] & 0x7F) + 2000;

            LocalDateTime localDateTime = LocalDateTime.of(years, months, days, hours, minutes, seconds, nanos);

            return localDateTime;
        } catch (Exception e) {
            log.error("decodeCP56Time2a.error, dataHex:{}", HexUtil.encodeHex(bytes), e);
            return LocalDateTime.now();
        }

    }

    public static Long decodeCP56Time2aToLong(byte[] data) {
        LocalDateTime localDateTime = decodeCP56Time2a(data);
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static void main(String[] args) {
//  byte[] bytes = HexUtil.decodeHex("f8a73310180419");
/*        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        byte[] bytes1 = encodeCP56Time2a(now);
        String s = HexUtil.encodeHexStr(bytes1);
        System.out.println(s);*/

        byte[] bytes = HexUtil.decodeHex("f8a73310180419");
        LocalDateTime localDateTime = decodeCP56Time2a(bytes);
        System.out.println(localDateTime);


    }
}
