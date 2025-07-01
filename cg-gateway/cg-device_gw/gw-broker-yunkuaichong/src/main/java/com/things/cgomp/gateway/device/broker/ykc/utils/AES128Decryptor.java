package com.things.cgomp.gateway.device.broker.ykc.utils;

import cn.hutool.core.util.HexUtil;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class AES128Decryptor {

    /**
     * 算法的名称
     */
    private static final String AES = "AES";

    /**
     * 默认 AES/CBC/PKCS5Padding
     * <p>
     * 算法：AES
     * 模式：CBC； 其中CBC、CFB模式需要向量；OFB模式不需要向量
     * 填充：PKCS5Padding
     */
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";


    /**
     * 加密
     *
     * @param oldEncrypted 待加密
     * @param key            加密key
     * @param key            向量
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] oldEncrypted, String key) throws Exception {
        byte[] raw = key.getBytes(StandardCharsets.US_ASCII);
        //设置秘钥
        SecretKeySpec keySpec = new SecretKeySpec(raw, AES);
        //设置向量
        IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes());
        //初始化加密方式  Cipher.ENCRYPT_MODE 加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        //加密；   设置为utf-8, 防止中文和英文混合
        byte[] encrypted = cipher.doFinal(oldEncrypted);
        return encrypted;
    }

    /**
     * 解密
     *
     *
     * @param key            秘钥
     * @param
     * @param
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] encrypted, String key) throws Exception {
        byte[] raw = key.getBytes(StandardCharsets.US_ASCII);
        //设置秘钥
        SecretKeySpec keySpec = new SecretKeySpec(raw, AES);
        //设置向量
        IvParameterSpec ivSpec = new IvParameterSpec(key.getBytes());
        //初始化解密方式  Cipher.DECRYPT_MODE 解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        //获取HEX的解码，因为在是加密过程中采用了HEX的编码，所以此步骤需要HEX的解码
        //如果在是加密过程中采用了base64的编码，此步骤就需要base64的解码，
        //HEX和base64 使用一种即可，但需要保持一致
        // 先用base64解密。与上一行HEX两者选其一。
        // byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);

        //解密
        return cipher.doFinal(encrypted);
    }

    public static void main(String[] args) throws Exception {
/*
        String needEncryptStr = "A1B2......................";
        byte[] ss = needEncryptStr.getBytes(StandardCharsets.US_ASCII);
        String key = "aaaaaaaaaaaaaaaa";
        byte[]  old = encrypt(ss, key);
        byte[] newB = decrypt(old,key);
        System.out.println(new String(newB,StandardCharsets.US_ASCII));
*/

        byte[] bytes = HexUtil.decodeHex("8ac3075d4214abdaa61f014a7ef441edfa5a132bf90463fdb1f0829afeadbfbfe4cf2af3093c6e66fad89b595196fa4f5c6213c26ec730cf67b3a3fb97567714a1bc765a01a5db87bd26d75114176c2c4b45465e65e84306a5fcde2156fb7accedca4141dbe53c5eb402f0ba6ebf82306f26121464349feabc91e2b8c788003d7f640da5e0aeb3d3f27b4fde512f2c8f");

        byte[] ccZn7PUFeiDpjb43s = decrypt(bytes, "ccZn7PUFeiDpjb43");


    }


}
