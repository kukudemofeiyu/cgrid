package com.things.cgomp.devicescale.utils;

import java.security.SecureRandom;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class RandomKeyGenerator {

    public static void main(String[] args) {


    }

    /**
     * 生成 16 位随机密钥（2 个字节）
     */
    public static String generateRandomKey() {
        // 创建一个SecureRandom实例
        SecureRandom secureRandom = new SecureRandom();

        // 生成一个2字节的随机字节数组（因为16位/8=2字节）
        byte[] randomBytes = new byte[2];
        secureRandom.nextBytes(randomBytes);

        // 将字节数组转换为二进制字符串，并确保长度为16位
        StringBuilder binaryString = new StringBuilder();
        for (byte b : randomBytes) {
            binaryString.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0')); // 将每个字节转换为8位二进制并填充至8位
        }

        // 确保总长度为16位，如果不足，则在前面补0（理论上不会发生，因为我们生成的是2字节）
        while (binaryString.length() < 16) {
            binaryString.insert(0, '0');
        }
        return binaryString.toString();
    }


}
