package com.things.cgomp.gateway.device.broker.ykc.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtils {

    // 填充方式
    public static final String RSA_ALGORITHM_NOPADDING = "RSA";
    public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";

    /**
     * 生成密钥对
     * @return
     */
    public static Map<String, String> createKeyPairs() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM_NOPADDING);
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            return new HashMap<String, String>(){{
                put("publicKey", Base64.encodeBase64String(publicKey.getEncoded()));
                put("privateKey", Base64.encodeBase64String(privateKey.getEncoded()));
            }};
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过公钥对数据进行加密
     * @param publicKeyStr
     * @param data
     * @return
     */
    public static String encryptRSADefault(String publicKeyStr, String data) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NOPADDING);  // 指定算法，返回keyFactory对象
            byte[] publicKeyByte = Base64.decodeBase64(publicKeyStr);  // 或：Base64.decodeBase64(publicKeyStr.getBytes())
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyByte);  // 创建X509编码公钥规范
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);  // 根据X5090编码密钥规范产生公钥对象
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NOPADDING);  // 根据算法名称，生成密码对象
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  // 使用公钥初始化cipher对象（encrypt加密模式）
            byte[] encryptByte = cipher.doFinal(data.getBytes());  // 对数据进行加密
            return Base64.encodeBase64String(encryptByte);  // 将字节数组，经过base64编码后，以US-ASCII编码输出为字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过私钥对数据进行解密
     * @param privateKeyStr
     * @param data
     * @return
     */
    public static String decryptRSADefault(String privateKeyStr, String data) throws Exception{
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM_NOPADDING);
            byte[] privateKeyByte = Base64.decodeBase64(privateKeyStr);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyByte);  //  密钥为pkcs8格式
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);  // 生成私钥对象
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_NOPADDING);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptByte = cipher.doFinal(Base64.decodeBase64(data.getBytes()));
            return new String(decryptByte, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("RSA解密失败");
        }
    }

    public static byte[] getPublicKeyBytes(String publicKey){
        byte[] bytes = Base64.decodeBase64(publicKey);
        return bytes;
    }

    public static void main(String[] args) throws Exception {
/*        Map<String, String> keyPairs = createKeyPairs();
        System.out.println(keyPairs.get("publicKey"));
        System.out.println(keyPairs.get("privateKey"));*/

        String s = encryptRSADefault("MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMsGW6BTvELsA6vAY5GlCadckN0KGGcYuUobURbN/rpCvoQ7GDOBm8OLfjeFzPNLMa46CQD27GiL8TSSiSHCqU0CAwEAAQ==", "hello");
        String s1 = decryptRSADefault("MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAywZboFO8QuwDq8BjkaUJp1yQ3QoYZxi5ShtRFs3+ukK+hDsYM4Gbw4t+N4XM80sxrjoJAPbsaIvxNJKJIcKpTQIDAQABAkAwUg0QtZ1DGEUvZZTNH0iS6rcXxMVWFE5VUvcTxGdyEO+tl4DcmY6iz/InDfk+bIvu4fQXNlBJoGv7tx5ufMcJAiEA+Wl60Z53ytwdD4n6rK6Y6WfalrRAKq+0XSOsDWpljD8CIQDQYzVrcVFzvczSBZwwpC/dJPWJt6ZzeAjSZd2NcrUXcwIhAJZ54TPfG/80VgQuByNwI6mOkv8huSUH5RXck810R9gZAiBPwmkj4o+GeVL33+XUSEN5pTfFqmEvOPZHcW3HQexGOQIhAPYHJGoaVNdD2G+hk87MguYt0EZVOiG/L/LFTR02Ay5b", "Ex6f/KAE1diCqJmLByHnBV0TpTsEv8KoZBN7gsJ8B4uEdLnpSpbbrh6onIs6kwMX7WqHI21bvoyB7iNE/ipPTw==");
        System.out.println(s1);


    }
}
