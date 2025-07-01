package com.things.cgomp.devicescale.utils;

import cn.hutool.core.codec.BCD;
import cn.hutool.core.util.HexUtil;

import java.time.LocalDateTime;
import java.util.Base64;

import static com.things.cgomp.devicescale.message.CmdId.运营平台远程控制启机_响应;


/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public class TestUtil {
    public static void main(String[] args) {
        //认证响应
        // authResp();

        //计费模型请求应答
        // M0AResp();

        //运营平台远程控制启机
        //MA8Resp();
        long ss = System.nanoTime();
        System.out.println(ss);

        System.out.println(BCD.strToBcd("0268860038559700").length);



    }

    private static void MA8Resp() {
        //消息头,开头符：68,数据长度：9f00,序列号:0100,发送时间:a6da0b10060319,加密标志:00,帧类型标志:01
        byte[] 消息头 = new byte[]{0x68};
        //seq
        int rSeq = 0;
        int sn = 29911;
        byte[] 序列号 = ByteUtil.intToByte2L(rSeq);
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = new byte[]{0x00};
        byte[] 响应CMD = new byte[]{(byte) 0xA8};

        byte[] 消息内容_交流流水 = BCD.strToBcd(String.format("%0" + 32 + "d", System.currentTimeMillis()));//交易流水
        byte[] 消息内容_桩编号 = BCD.strToBcd(String.format("%0" + 14 + "d", sn));//1 桩编号
        byte[] 消息内容_枪号 = new byte[]{0x01};//1 枪号
        byte[] 消息内容_逻辑卡号 = BCD.strToBcd(String.format("%0" + 16 + "d", 123456));//
        byte[] 消息内容_物理卡号 = BCD.strToBcd(String.format("%0" + 16 + "d", 654321));//
        byte[] 消息内容_账户余额 = ByteUtil.intToByte4L(100001);
        byte[] 消息内容_本次充电当前允许的最大功率 = ByteUtil.intToByte2L(11001);
        byte[] 消息内容_SOC限制 = new byte[]{0x00};
        byte[] 消息内容_充电电量限制 = ByteUtil.intToByte4L(0);


        byte[] 响应消息内容 = ByteUtil.mergeArrays(消息内容_交流流水, 消息内容_桩编号, 消息内容_枪号, 消息内容_逻辑卡号, 消息内容_物理卡号, 消息内容_账户余额,
                消息内容_本次充电当前允许的最大功率, 消息内容_SOC限制, 消息内容_充电电量限制);
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
        System.out.println("消息内容{消息内容_交流流水}:" + HexUtil.encodeHexStr(消息内容_交流流水));
        System.out.println("消息内容{桩SN}:" + HexUtil.encodeHexStr(消息内容_桩编号));
        System.out.println("消息内容{消息内容_枪号}:" + HexUtil.encodeHexStr(消息内容_枪号));
        System.out.println("消息内容{消息内容_逻辑卡号}:" + HexUtil.encodeHexStr(消息内容_逻辑卡号));
        System.out.println("消息内容{消息内容_物理卡号}:" + HexUtil.encodeHexStr(消息内容_物理卡号));
        System.out.println("消息内容{消息内容_账户余额}:" + HexUtil.encodeHexStr(消息内容_账户余额));
        System.out.println("消息内容{消息内容_本次充电当前允许的最大功率}:" + HexUtil.encodeHexStr(消息内容_本次充电当前允许的最大功率));
        System.out.println("消息内容{消息内容_SOC限制}:" + HexUtil.encodeHexStr(消息内容_SOC限制));
        System.out.println("消息内容{消息内容_充电电量限制}:" + HexUtil.encodeHexStr(消息内容_充电电量限制));

        System.out.println("消息内容{全部}:" + HexUtil.encodeHexStr(响应消息内容));
        System.out.println("CRC:" + HexUtil.encodeHexStr(CRC));
        System.out.println("REQ:" + HexUtil.encodeHexStr(req));

    }

    private static void M0AResp() {
        //认证响应消息
        //消息头,开头符：68,数据长度：9f00,序列号:0100,发送时间:a6da0b10060319,加密标志:00,帧类型标志:01
        byte[] 消息头 = new byte[]{0x68};
        //要跟请求对应起来
        int rSeq = 0;
        int sn = 29911;
        byte[] 序列号 = ByteUtil.CRCByte2H(rSeq);
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = new byte[]{0x00};
        byte[] 响应CMD = new byte[]{0x0A};

        byte[] 消息内容_桩编号 = BCD.strToBcd(String.format("%0" + 14 + "d", sn));//1 桩编号
        byte[] 消息内容_计费模型编号 = new byte[]{0x01, 0x00};//

        int int_费率个数 = 5;
        byte[] 消息内容_费率个数 = new byte[]{(byte) int_费率个数};
        //一个占8个字节
        // byte[] 消息内容_费率 = new byte[int_费率个数 * 8];
        byte[][] 电费费率 = new byte[int_费率个数][4];
        byte[][] 服务费率 = new byte[int_费率个数][4];
        int pri = 2;
        for (int i = 0; i < int_费率个数; i++) {
            电费费率[i] = ByteUtil.intToByte4L(pri);
            服务费率[i] = ByteUtil.intToByte4L(pri);
            pri++;
        }

        byte[] 消息内容_电费费率 = ByteUtil.mergeArrays(电费费率);
        byte[] 消息内容_服务费率 = ByteUtil.mergeArrays(服务费率);
        byte[] 消息内容_费率 = ByteUtil.mergeArrays(消息内容_电费费率, 消息内容_服务费率);


        byte[] 消息内容_计损比例 = new byte[]{0x01};//

        //固定48个
        byte[] 时段费率号 = new byte[48];
        for (int i = 0; i < 时段费率号.length; i++) {
            时段费率号[i] = (byte) i;
        }


        byte[] 响应消息内容 = ByteUtil.mergeArrays(消息内容_桩编号, 消息内容_计费模型编号, 消息内容_费率个数, 消息内容_费率, 消息内容_计损比例, 时段费率号);
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
        System.out.println("消息内容{消息内容_计费模型编号}:" + HexUtil.encodeHexStr(消息内容_计费模型编号));
        System.out.println("消息内容{消息内容_费率个数}:" + HexUtil.encodeHexStr(消息内容_费率个数));
        System.out.println("消息内容{消息内容_费率}:" + HexUtil.encodeHexStr(消息内容_费率));
        System.out.println("消息内容{消息内容_计损比例}:" + HexUtil.encodeHexStr(消息内容_计损比例));
        System.out.println("消息内容{时段费率号}:" + HexUtil.encodeHexStr(时段费率号));

        System.out.println("消息内容{全部}:" + HexUtil.encodeHexStr(响应消息内容));
        System.out.println("CRC:" + HexUtil.encodeHexStr(CRC));
        System.out.println("REQ:" + HexUtil.encodeHexStr(req));

    }

    private static void authResp() {
        //认证响应消息
        //消息头,开头符：68,数据长度：9f00,序列号:0100,发送时间:a6da0b10060319,加密标志:00,帧类型标志:01
        byte[] 消息头 = new byte[]{0x68};
        //要跟请求对应起来
        int rSeq = 0;
        int sn = 29911;
        String rasPub = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMsGW6BTvELsA6vAY5GlCadckN0KGGcYuUobURbN/rpCvoQ7GDOBm8OLfjeFzPNLMa46CQD27GiL8TSSiSHCqU0CAwEAAQ==";
        byte[] 序列号 = ByteUtil.intToByte2L(rSeq);
        byte[] 发送时间 = ByteUtil.encodeCP56Time2a(LocalDateTime.now());
        byte[] 加密标志 = new byte[]{0x00};
        byte[] 响应CMD = new byte[]{0x02};


        byte[] 消息内容_桩编号 = BCD.strToBcd(String.format("%0" + 14 + "d", sn));//1 桩编号
        byte[] 消息内容_状态 = new byte[]{0x00};//2 处理状态
        byte[] 消息内容_公钥 = Base64.getDecoder().decode(rasPub);//2 处理状态

        byte[] 响应消息内容 = ByteUtil.mergeArrays(消息内容_桩编号, 消息内容_状态, 消息内容_公钥);
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
        System.out.println("消息内容{处理状态}:" + HexUtil.encodeHexStr(消息内容_状态));
        System.out.println("消息内容{公钥}:" + HexUtil.encodeHexStr(消息内容_公钥));
        System.out.println("CRC:" + HexUtil.encodeHexStr(CRC));
        System.out.println("REQ:" + HexUtil.encodeHexStr(req));
    }
}
