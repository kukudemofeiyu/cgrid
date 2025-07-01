package com.things.cgomp.devicescale.message;

public interface CmdId {

    int 充电桩登录认证 = 0x01;
    int 充电桩登录认证响应 = 0x02;
    int 心跳响应 = 0x04;
    int 运营平台远程控制启机 = 0xA8;
    int 运营平台远程控制启机_响应 = 0xA7;

    int 交易记录上报 = 0x3D;

    int 计费模型验证请求 = 0x05;
    int 计费模型验证请求_响应 = 0x06;

    int 充电桩计费模型请求 = 0x09;
    int 充电桩计费模型响应 = 0x0A;

    int 插枪_充电桩上报vin码 = 0xA9;
    int 插枪_充电桩上报vin码_响应 = 0xAA;

    int 运营平台远程停机 = 0x36;

    int 运营平台远程停机_响应 = 0x35;

    int 交易记录 = 0x3D;

    int 交易记录_应答 = 0x40;


    int 二维码设置 = 0x5B;
    int 二维码设置应答 = 0x5A;

    int 计费模型设置 = 0x58;
    int 计费模型应答 = 0x57;

    int 默认最大功率下发 = 0x60;
    int 默认最大功率下发_应答 = 0x59;


    int 充电结束 = 0x19;

    int 上传实时监测数据 = 0x13;


    int 读取实时监测数据 = 0x12;//运营平台根据需要主动发起读取实时数据的请求

    int 充电握手 = 0x15;

    int 远程账户余额更新 = 0x42;

    int 远程账户余额更新_答应 = 0x41;

    int 对时设置 = 0x56;
    int 对时设置_响应 = 0x55;

    int 参数设置 = 0x5F;//平台下发参数配置信息到桩，桩根据下发参数修改配置。
    int 参数设置_应答 = 0x5E;//充电桩在修改参数后进行应答


    int 交易记录召唤 = 0x4D;

    int 交易记录召唤_响应 = 0x4C;


}
