package com.things.cgomp.devicescale.message;

/**
 * describe:
 *
 * @author mofeiyu
 * @date $
 */
public enum DataType {

    //无符号单字节整型（字节，8位）
    BYTE(1),
    //无符号2字节整型（字节，16位）
    UNSIGNED_SHORT_LE(2),
    //  无符号4字节整型（字节，32位
    UNSIGNED_INT_LE(4),
    CP56Time2a(7),
    //BCD码，N字节
    BCD(-1),

    //ASCII 码
    BYTE_ASCII(-1),

    //ASCII BASE 64码
    BYTE_ASCII_BASE64(-1),
    //列表
    LIST(-1);


    public int length;

    DataType(int length) {
        this.length = length;
    }
}
