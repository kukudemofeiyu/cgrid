package com.things.cgomp.devicescale.utils;

import cn.hutool.core.util.HexUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.LocalDateTime;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class ByteUtil {

    public static byte[] mergeArrays(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] array : arrays) {
            totalLength += array.length;
        }
        byte[] merged = new byte[totalLength];
        int index = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, merged, index, array.length);
            index += array.length;
        }
        return merged;
    }


    public static byte[] intToByte2L(int n) {
        byte[] b = new byte[2];
        b[0] = (byte) (n & 0xff);    // 低8位存入第0位（小端模式起始位）
        b[1] = (byte) ((n >> 8) & 0xff); // 高8位右移8位后存入第1位
        return b;
    }

    public static byte[] intToByte4L(int n) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (n & 0xFF);        // 最低位字节
        bytes[1] = (byte) ((n >> 8) & 0xFF);  // 次低位字节
        bytes[2] = (byte) ((n >> 16) & 0xFF); // 次高位字节
        bytes[3] = (byte) ((n >> 24) & 0xFF); // 最高位字节
        return bytes;
    }

    public static byte[] encodeCP56Time2a(LocalDateTime dateTime) {
        ByteBuffer buffer = ByteBuffer.allocate(7).order(ByteOrder.LITTLE_ENDIAN);
        buffer.putShort((short) (dateTime.getSecond() * 1000 + dateTime.getNano() / 1000000));
        buffer.put((byte) (dateTime.getMinute() & 0x3F));
        buffer.put((byte) (dateTime.getHour() & 0x1F));
        buffer.put((byte) (dateTime.getDayOfMonth() & 0x1F));
        buffer.put((byte) (dateTime.getMonthValue() & 0x0F));
        buffer.put((byte) ((dateTime.getYear() % 100) & 0x7F));

        return buffer.array();
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

    public static byte[] intToByte2H(int n) {
        byte[] b = new byte[2];
        b[0] = (byte) ((n >> 8) & 0xff);
        b[1] = (byte) (n & 0xff);
        return b;
    }

    /**
     * 小端
     *
     * @param data
     * @return
     */
    public static int byteLToI2nt(byte[] data) {
        return (data[1] & 0xFF) << 8 | (data[0] & 0xFF);
    }

    public static int byteLToI4nt(byte[] data) {
        return (data[0] & 0xFF)
                | ((data[1] & 0xFF) << 8)
                | ((data[2] & 0xFF) << 16)
                | ((data[3] & 0xFF) << 24);

    }

    public static Integer byteToI1nt(byte[] data) {
        return (int) data[0];
    }

    public static byte[] CRCByte2H(int crcInt) {
        return intToByte2H(crcInt);
    }

    public static int byteHToI2nt(byte[] data) {

        return (data[0] & 0xFF) << 8 | (data[1] & 0xFF);
    }
}
