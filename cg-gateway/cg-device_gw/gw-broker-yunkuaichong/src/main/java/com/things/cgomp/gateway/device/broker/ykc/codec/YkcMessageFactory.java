package com.things.cgomp.gateway.device.broker.ykc.codec;

import cn.hutool.bloomfilter.bitMap.BitMap;
import cn.hutool.core.codec.BCD;
import cn.hutool.core.util.HexUtil;
import com.things.cgomp.common.core.exception.ServiceException;
import com.things.cgomp.common.core.utils.StringUtils;
import com.things.cgomp.common.gw.device.context.error.ErrorCodeConstants;
import com.things.cgomp.gateway.device.broker.ykc.codec.in.*;
import com.things.cgomp.gateway.device.broker.ykc.constant.*;
import com.things.cgomp.gateway.device.broker.ykc.utils.AES128Decryptor;
import com.things.cgomp.gateway.device.broker.ykc.utils.Cp56Time2aUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.ByteUtils;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class YkcMessageFactory {

    public static YkcMessageIn newMessage(Integer messageType,  ByteBuf messageBody) throws Exception{
        DeviceOpConstantEnum optEnum = DeviceOpConstantEnum.getOptEnum(messageType);
        if (optEnum == null) {
            log.info("no support frameType:{}", String.format("%04x", messageType));
            return null;
        }

        switch (optEnum) {
            case CONNECT:
                return getLoginMessageIn(messageBody);
            case HEARTBEAT:
                return getHeartbeatMessageIn(messageBody);
            case UPDATE_DEVICE_QR_RESP:
                return getQRRespMessageIn(messageBody);
            case START_CHARGE_RESP:
                return getStartChargeMessageIn(messageBody);
            case STOP_CHARGE_RESP:
                return getStopChargeMessageIn(messageBody);
            case CHARGE_STANDARD_SYNC:
                return getChargeStandardSyncMessageIn(messageBody);
            case CHARGE_STANDARD_CHECK:
                return getChargeStandardCheckMessageIn(messageBody);
            case DEVICE_STATUS_REPORT:
                return getDeviceStatusReportMessageIn(messageBody);
            case DEVICE_VIN_REPORT:
                return getDeviceVinReportMessageIn(messageBody);
            case DEVICE_CHARGE_END:
                return getDeviceChargeEndMessageIn(messageBody);
            case CHARGE_RECORD:
                return getChargeRecordMessageIn(messageBody);
            case DEVICE_TIME_SYNC_RESP:
                return getDeviceTimeSyncMessageIn(messageBody);
            case CHARGE_HANDSHAKE:
                return getChargeHandshakeMessageIn(messageBody);
            case CHARGE_CONFIG_SETTING:
                return getConfigSettingMessageIn(messageBody);
            case CHARGE_ERROR_STATUS:
                return getChargeErrorStatusMessageIn(messageBody);
            case CHARGE_BMS_STOP_STATUS:
                return getChargeBmsStopStatusMessageIn(messageBody);
            case GET_CHARGE_RECORD_RESP:
                return getChargeRecordGetMessageIn(messageBody);
            case SET_CHARGE_STANDARD_CHECK_RESP:
                return getSetChargeStandardRespMessageIn(messageBody);
            case DEVICE_ALARM_REPORT:
                return getDeviceAlarmReportMessageIn(messageBody);
            case DEVICE_ALARM_RECOVER:
                return getDeviceAlarmRecoverMessageIn(messageBody);
            case CHARGE_OUTPUT_STATUS:
                return getChargeOutputStatusMessageIn(messageBody);
            default:
                throw new ServiceException(ErrorCodeConstants.NO_SUPPORT_FRAME);
        }
    }

    private static YkcMessageIn getChargeOutputStatusMessageIn(ByteBuf messageBody) {
        String orderNo = readOrderNo(messageBody);
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        Double data4 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        Double data5 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10 - 400;
        int data6 = messageBody.readByte();
        Double data7 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        Double data8 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10 - 400;

        short i = messageBody.readShortLE();
        Double maxVoltage = Double.valueOf(i & 0xfff) /100;
        int maxCurrentGroupNo = i & 0xf000;

        Integer  data10 = (int)messageBody.readByte();

        int data11 = messageBody.readUnsignedShortLE();
        Double data12 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        Double data13 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10 - 400;
        int data14 = messageBody.readUnsignedShortLE();

        return YkcChargeBmsOutputIn.builder()
                .orderNo(orderNo)
                .deviceNo(deviceNo)
                .gunNo(gunNo)
                .bmsVoltageXQ(data4)
                .bmsCurrentXQ(data5)
                .chargeMode(data6)
                .bmsVoltageMeasure(data7)
                .bmsCurrentMeasure(data8)
                .maxVoltage(maxVoltage)
                .maxVoltageGroupNo(maxCurrentGroupNo)
                .soc(data10)
                .bmsChargeTime(data11)
                .voltageOutput(data12)
                .currentOutput(data13)
                .totalChargeTime(data14)
                .build();

    }

    private static YkcMessageIn getDeviceAlarmRecoverMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        Integer alarmType = (int)messageBody.readByte();

        Integer alarmCode = (int)messageBody.readShortLE();

        String reason = DeviceAlarmCodeEnum.getReason(alarmCode);

        Long time = getTs(messageBody);

        return YkcDeviceAlarmRecoverIn.builder()
                .deviceNo(deviceNo)
                .gunNo(gunNo)
                .alarmType(alarmType)
                .alarmCode(String.format("%04x", alarmCode))
                .alarmReason(reason)
                .ts(time)
                .build();
    }

    private static YkcMessageIn getDeviceAlarmReportMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        Integer alarmType = (int)messageBody.readByte();

        Integer alarmCode = (int)messageBody.readShortLE();

        String reason = DeviceAlarmCodeEnum.getReason(alarmCode);

        Long time = getTs(messageBody);

        return YkcDeviceAlarmStatusIn.builder()
                .deviceNo(deviceNo)
                .gunNo(gunNo)
                .alarmType(alarmType)
                .alarmCode(String.format("%04x", alarmCode))
                .alarmReason(reason)
                .ts(time)
                .build();
    }

    private static YkcMessageIn getSetChargeStandardRespMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);

        Boolean controlSuccessful  = messageBody.readByte() == 0x00;

        return YkcChargeStandardSetIn.builder()
                .deviceNo(deviceNo)
                .controlSuccessful(controlSuccessful)
                .build();
    }

    private static YkcMessageIn getChargeRecordGetMessageIn(ByteBuf messageBody) {
        String orderNo = readOrderNo(messageBody);
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);

        Boolean controlSuccessful  = messageBody.readByte() == 0x00;
        String reasonCode = parseGetChargeRecordReason(messageBody.readByte());

        return YkcDeviceGetChargeRecordIn.builder()
                .orderNo(orderNo)
                .deviceNo(deviceNo)
                .gunNo(gunNo)
                .controlSuccessful(controlSuccessful)
                .errorReason(reasonCode)
                .build();
    }

    private static YkcMessageIn getChargeBmsStopStatusMessageIn(ByteBuf messageBody) {
        return null;
    }

    private static YkcMessageIn getChargeErrorStatusMessageIn(ByteBuf messageBody) {
        String data1 = readOrderNo(messageBody);
        String data2 = readDeviceNo(messageBody);
        String dsat3 = readGunNo(messageBody);
        Integer data4 = (int)messageBody.readShort();
        Integer data5 = (int)messageBody.readShort();

        // 预留位
        messageBody.skipBytes(1);

        Integer data7 = (int)messageBody.readShort();
        Integer data8 = (int)messageBody.readShort();

        // 预留位
        messageBody.skipBytes(4);

        Integer data10 = (int)messageBody.readShort();
        Integer data11 = (int)messageBody.readShort();

        // 预留位
        messageBody.skipBytes(4);

        Integer data13 = (int)messageBody.readShort();

        byte[] bmsOtherB = new byte[6];
        messageBody.readBytes(bmsOtherB);
        Long data14 = ByteBuffer.wrap(bmsOtherB).order(ByteOrder.LITTLE_ENDIAN).getLong();

        Integer data15 = (int)messageBody.readShort();

        // 预留位
        messageBody.skipBytes(6);

        Integer data17 = (int)messageBody.readShort();
        Integer data18 = (int)messageBody.readShort();

        // 预留位
        messageBody.skipBytes(4);

        Integer data20 = (int)messageBody.readShort();
        Integer data21 = (int)messageBody.readShort();
        Integer data22 = (int)messageBody.readShort();

        // 预留位
        messageBody.skipBytes(2);

        Integer data24 = (int)messageBody.readShort();

        byte[] cgOtherB = new byte[6];
        messageBody.readBytes(cgOtherB);
        Integer data25 = ByteBuffer.wrap(bmsOtherB).order(ByteOrder.LITTLE_ENDIAN).getInt();

        return YkcChargeErrorStatusIn.builder()
                .orderNo(data1)
                .deviceNo(data2)
                .gunNo(dsat3)
                .errStuCgSpn00(data4)
                .errStuCgSpnAA(data5)
                .errStuCgTimeSync(data7)
                .errStuCgChargeReady(data8)
                .errStuCgChargeStatus(data10)
                .errStuCgChargeStop(data11)
                .errStuCgChargeStatistics(data13)
                .errStuBmsOther(data14)
                .errStuBmsVin(data15)
                .errStuBmsChargeParam(data17)
                .errStuBmsChargeReady(data18)
                .errStuBmsChargeAll(data20)
                .errStuBmsChargeRequest(data21)
                .errStuBmsStopCharge(data22)
                .errStuBmsChargeStatistics(data24)
                .errorCgOther(data25)
                .build();
    }

    private static YkcMessageIn getConfigSettingMessageIn(ByteBuf messageBody) {
        String data1 = readOrderNo(messageBody);
        String data2 = readDeviceNo(messageBody);
        String data3 = readGunNo(messageBody);

        double data4 = Double.valueOf(messageBody.readUnsignedShortLE()) / 100;
        double data5 = Double.valueOf(messageBody.readShortLE()) / 10;
        double data6 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        double data7 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        Integer data8 = (int)messageBody.readByte();
        double data9 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        Integer data10 = messageBody.readUnsignedShortLE();

        double data11 = Double.valueOf(messageBody.readShortLE()) / 10;
        double data12 = Double.valueOf(messageBody.readShortLE()) / 10;
        double data13 = Double.valueOf(messageBody.readShortLE()) / 10;
        double data14 = Double.valueOf(messageBody.readShortLE()) / 10;

        return YkcChargeConfigSettingIn.builder()
                .orderNo(data1)
                .deviceNo(data2)
                .gunNo(data3)
                .maxAllowCur(data4)
                .maxAllowEle(data5)
                .standElecQ(data6)
                .maxAllowTolCur(data7)
                .maxAllowTemp(data8)
                .soc(data9)
                .currentCur(data10)
                .maxOutCurrent(data11)
                .minOutCurrent(data12)
                .maxOutElec(data13)
                .minOutElec(data14)
                .build();
    }

    private static YkcMessageIn getChargeHandshakeMessageIn(ByteBuf messageBody) {
        String data1 = readOrderNo(messageBody);
        String data2 = readDeviceNo(messageBody);
        String data3 = readGunNo(messageBody);

        String data4 = readVersion(messageBody);

        Integer data5 = (int)messageBody.readByte();

        Double data6 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        Double data7 = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;

        byte[] bmsFactoryNameB  = new byte[4];
        messageBody.readBytes(bmsFactoryNameB);
        String data8 = new String(bmsFactoryNameB, StandardCharsets.US_ASCII);

        String data9 = String.valueOf(messageBody.readIntLE());

        Integer bmsBirthYear = (int)messageBody.readUnsignedByte() + 1985;
        Integer bmsBirthMonth = (int)messageBody.readUnsignedByte();
        Integer bmsBirthDay = (int)messageBody.readUnsignedByte() ;
        LocalDate data10_12 = LocalDate.of(bmsBirthYear, bmsBirthMonth, bmsBirthDay);

        byte[] bmsChargeTimeB  = new byte[3];
        messageBody.readBytes(bmsChargeTimeB);
        Long data13 = bytesToLong(bmsChargeTimeB);

        Integer data14 = (int)messageBody.readByte();

        // 预留位
        messageBody.skipBytes(1);

        String data16 = parseVin(messageBody);

        String data17 = readVersion(messageBody) + "_" + messageBody.readByte() + messageBody.readByte() + messageBody.readByte() +  messageBody.readByte();

        return YkcChargeHandShakeStatusIn.builder()
                .orderNo(data1)
                .deviceNo(data2)
                .gunNo(data3)
                .bmsProtocolVersion(data4)
                .bmsType(data5)
                .bmsSystemRatedCapacity(data6)
                .bmsSystemRatedCurrent(data7)
                .bmsFactoryName(data8)
                .bmsGroupSn(data9)
                .bmsBirthDay(data10_12)
                .bmsChargeTime(data13.intValue())
                .bmsId(data14)
                .vin(data16)
                .bmsSoftwareVer(data17)
                .build();

    }

    private static YkcMessageIn getDeviceTimeSyncMessageIn(ByteBuf messageBody) {
        // 设备编号
        String deviceNo = readDeviceNo(messageBody);
        // 时间
        LocalDateTime time = getTime(messageBody);
        YkcDeviceTimeSyncIn timeSyncIn = new YkcDeviceTimeSyncIn(deviceNo, time);
        return timeSyncIn;
    }

    private static YkcMessageIn getChargeRecordMessageIn(ByteBuf messageBody) {
        // 订单编号
        String orderNo = readOrderNo(messageBody);
        // 设备编号
        String deviceNo = readDeviceNo(messageBody);
        // 枪号
        String gunNo = readGunNo(messageBody);
        // 开始时间
        LocalDateTime startTime = getTime(messageBody);
        // 结束时间
        LocalDateTime endTime = getTime(messageBody);

        //忽略电表加密信息
        messageBody.skipBytes(6+34+2+1+5+5);

        // 总电量
        BigDecimal chargeElectricity = new BigDecimal(Double.valueOf(messageBody.readUnsignedIntLE()) / 10000);
        // 计损总电量
        BigDecimal lossElectricity = new BigDecimal(Double.valueOf(messageBody.readUnsignedIntLE()) / 10000);
        // 充电金额4位小数
        BigDecimal chargeFee = new BigDecimal(Double.valueOf(messageBody.readUnsignedIntLE()) /10000);
        // vin码
        String vin = parseVin(messageBody);


        // 交易标识
        byte chargeSignB = messageBody.readByte();
        Integer orderChargeType = (int)chargeSignB;
        // 交易时间
        LocalDateTime orderTime = getTime(messageBody);
        // 停止原因
        Integer stopReasonEnum =  (int)messageBody.readByte();
        // 物理卡号
        Long cardNo = messageBody.readLongLE();
        String cardNoStr = StringUtils.leftPad(String.valueOf(cardNo), 8,"0");

        // 费率时段数量
        Integer feeTimeRangeSize = (int)messageBody.readByte();

        List<YkcDeviceChargeRecordIn.ChargeRecordFee> chargeRecordFees = parseFee(messageBody, feeTimeRangeSize);

        return YkcDeviceChargeRecordIn.builder()
                .orderNo(orderNo)
                .deviceNo(deviceNo)
                .gunNo(gunNo)
                .startTime(startTime)
                .endTime(endTime)
                .chargeElectricity(chargeElectricity)
                .lossElectricity(lossElectricity)
                .chargeFee(chargeFee)
                .vin(vin)
                .orderChargeType(orderChargeType)
                .stopReason(stopReasonEnum)
                .cardNo(cardNoStr)
                .chargeRecordFees(chargeRecordFees)
                .orderTime(orderTime)
                .build();
    }

    private static List<YkcDeviceChargeRecordIn.ChargeRecordFee> parseFee(ByteBuf messageBody, Integer feeTimeRangeSize) {
        List<YkcDeviceChargeRecordIn.ChargeRecordFee> chargeRecordFees = new ArrayList<>(feeTimeRangeSize);
        YkcDeviceChargeRecordIn.ChargeRecordFee chargeRecordFee = null;
        for (int i = 0; i < feeTimeRangeSize; i++) {

            chargeRecordFee=  new YkcDeviceChargeRecordIn.ChargeRecordFee();
            BigDecimal singleFee = new BigDecimal(Double.valueOf(messageBody.readIntLE()) / 100000);
            BigDecimal electricity = new BigDecimal(Double.valueOf(messageBody.readIntLE()) / 10000);
            BigDecimal lossElectricity = new BigDecimal(Double.valueOf(messageBody.readIntLE()) / 10000);
            BigDecimal totalFee = new BigDecimal(Double.valueOf(messageBody.readIntLE()) / 10000);
            chargeRecordFee.setSingleFee(singleFee);
            chargeRecordFee.setElectricity(electricity);
            chargeRecordFee.setLossElectricity(lossElectricity);
            chargeRecordFee.setTotalFee(totalFee);
            chargeRecordFees.add(chargeRecordFee);
        }

        return chargeRecordFees;

    }


    private static YkcMessageIn getDeviceChargeEndMessageIn(ByteBuf messageBody) {
        String orderNo = readOrderNo(messageBody);
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        Integer bmsEndSoc = ((Byte) messageBody.readByte()).intValue();
        Double bmsMinCurrent = Double.valueOf(messageBody.readShortLE()) /100;
        Double bmsMaxCurrent = Double.valueOf(messageBody.readShortLE()) /100;
        Integer bmsLowTemp = ((Byte) messageBody.readByte()).intValue();
        Integer bmsHighTemp = ((Byte) messageBody.readByte()).intValue();
        Integer totalChargeTime = ((Short) messageBody.readShortLE()).intValue();
        Double deviceElectricity = Double.valueOf( messageBody.readShortLE()) / 10;
        Integer chargeDeviceNo = messageBody.readIntLE();

        return YkcDeviceChargeEndStatusIn.of(orderNo,deviceNo,gunNo,bmsEndSoc, bmsMinCurrent,bmsMaxCurrent,bmsLowTemp,bmsHighTemp,
                totalChargeTime,deviceElectricity,chargeDeviceNo);
    }

    private static YkcMessageIn getDeviceVinReportMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);

        String vin = parseVin(messageBody);

        LocalDateTime ts = getTime(messageBody);
        return YkcDeviceVinStatusIn.of(deviceNo, gunNo, vin, ts);
    }

    private static YkcMessageIn getDeviceStatusReportMessageIn(ByteBuf messageBody) {
        // 订单编号
        String orderNo = readOrderNo(messageBody);
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        // 充电枪信息
        YkcDeviceGunStatusEnum gunStatus = parseGunStatus(messageBody.readByte());
        Integer gunHomeStatus = (int)messageBody.readByte();
        Integer gunInserted = (int)messageBody.readByte();
        // 输出电压
        Double voltage = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        // 输出电流
        Double current = Double.valueOf(messageBody.readUnsignedShortLE()) / 10;
        // 枪线温度 偏移量-50
        Integer gunLineTemp = ((Byte)messageBody.readByte()).intValue();
        // 枪线编码
        Long gunLineNo = messageBody.readLongLE();
        //电池信息
        // SOC
        Integer soc = (int) messageBody.readUnsignedByte();
        // 电池组最高温度
        Integer batteryGroupMaxTemp = (int) messageBody.readByte()  - 50;
        // 计量信息
        // 累计充电时间
        Integer chargeTime = messageBody.readUnsignedShortLE();
        // 剩余时间
        Integer remainingTime = messageBody.readUnsignedShortLE();
        // 充电度数
        Double chargeElectricity = Double.valueOf(messageBody.readUnsignedIntLE()) /1000;
        // 计损充电度数
        Integer lossChargeElectricity = Long.valueOf(messageBody.readUnsignedIntLE()).intValue();
        // 已充金额
        Double chargeAmount =  Double.valueOf(messageBody.readUnsignedIntLE())/1000;

        messageBody.skipBytes(2);

        Integer deviceTemp = messageBody.readByte() -50;

        Integer smokeDetectorStatus = (int) messageBody.readByte();

        Double  meterValue = 0d;
        int i = messageBody.readableBytes();
        if(i > 0 ){
            byte[] meterValueB = new byte[5];
            messageBody.readBytes(meterValueB);
            meterValue = Double.valueOf(bytesToLong(meterValueB)) /10000;
        }


        YkcDeviceStatusInfoIn ykcDeviceStatusInfoIn = YkcDeviceStatusInfoIn.of(orderNo, deviceNo, gunNo, gunStatus,
                gunHomeStatus, gunInserted, voltage, current, gunLineTemp,
                gunLineNo, soc, batteryGroupMaxTemp, chargeTime, remainingTime,
                chargeElectricity, lossChargeElectricity, chargeAmount, deviceTemp, smokeDetectorStatus, meterValue);
        return ykcDeviceStatusInfoIn;
    }


    private static YkcDeviceGunStatusEnum parseGunStatus(Byte gunStatusByte) {
        YkcDeviceGunStatusEnum gunStatus  ;
        switch (gunStatusByte) {
            case 0x00:  gunStatus = YkcDeviceGunStatusEnum.OFFLINE; break;
            case 0x01:  gunStatus =   YkcDeviceGunStatusEnum.ERROR; break;
            case 0x02:  gunStatus =  YkcDeviceGunStatusEnum.IDLE; break;
            case 0x03:   gunStatus = YkcDeviceGunStatusEnum.CHARGING; break;
            default : gunStatus =  YkcDeviceGunStatusEnum.IDLE;
        };
        return gunStatus;
    }


    private static YkcMessageIn getChargeStandardCheckMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        YkcChargeStandardCheckIn chargeStandardCheckIn = new YkcChargeStandardCheckIn(deviceNo,
                (int) messageBody.readShort());
        return chargeStandardCheckIn;
    }

    private static LocalDateTime getTime(ByteBuf byteBuf){
        // 发送时间
        byte[] timeBytes = new byte[7];
        byteBuf.readBytes(timeBytes);
        LocalDateTime eventTs = Cp56Time2aUtils.decodeCP56Time2a(timeBytes);
        return eventTs;
    }

    private static Long getTs(ByteBuf byteBuf){
        // 发送时间
        byte[] timeBytes = new byte[7];
        byteBuf.readBytes(timeBytes);
        Long eventTs = Cp56Time2aUtils.decodeCP56Time2aToLong(timeBytes);
        return eventTs;
    }


    private static YkcMessageIn getChargeStandardSyncMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        YkcChargeStandardIn ykcChargeStandardIn = new YkcChargeStandardIn();
        ykcChargeStandardIn.setDeviceNo(deviceNo);
        return ykcChargeStandardIn;
    }

    /**
     * 0x01
     */
    private static YkcMessageIn getLoginMessageIn(ByteBuf messageBody) {
        //随机密钥
        byte[] randomKeyByte = new byte[88];
        messageBody.readBytes(randomKeyByte);
        String randomKey = new String(randomKeyByte, StandardCharsets.US_ASCII);
        // 设备编号
        String deviceNo = readDeviceNo(messageBody);
        // 桩类型，0 表示直流桩，1 表示交流桩
        Boolean acOrDirectType = messageBody.readByte() == 0x01;
        // 充电枪数量
        Integer gunCount = readByteToInt(messageBody);
        // 通信协议版本, BIN码 如果协议版本号为 1.0.11，则为0x01，0x00,0x0B
        String protocolVersion = "";
        messageBody.readBytes(3);
        // 程序版本
        byte[]  programVersionBytes = new byte[8];
        messageBody.readBytes(programVersionBytes);
        String programVersion = new String(programVersionBytes, StandardCharsets.US_ASCII);
        // 网络链接类型
        byte networkTypeByte = messageBody.readByte();
        // sim卡
        byte[] simBytes = new byte[10];
        messageBody.readBytes(simBytes);
        String simCardNo = BCD.bcdToStr(simBytes);
        // 运营商
        byte operatorByte = messageBody.readByte();
        YkcDeviceLoginIn ykcDeviceLoginIn = YkcDeviceLoginIn.of(randomKey , deviceNo, acOrDirectType, gunCount, protocolVersion,
                programVersion, parseNetworkType(networkTypeByte), simCardNo, parseOperatorType(operatorByte));
        return ykcDeviceLoginIn;

    }

    /**
     * 0x02
            */
    private static YkcMessageIn getHeartbeatMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        // 0x00：正常 0x01：故障
        Boolean gunNormal = messageBody.readByte() == 0x00;
        YkcDeviceHeartbeatIn ykcDeviceHeartbeatIn = new YkcDeviceHeartbeatIn(deviceNo, gunNo, gunNormal);
        return ykcDeviceHeartbeatIn;
    }


    /**
     * 0xA8
     */
    private static YkcMessageIn getStartChargeMessageIn(ByteBuf messageBody) {
        // 订单编号
        String orderNo = readOrderNo(messageBody);
        // 设备编号
        String deviceNo = readDeviceNo(messageBody);
        // 枪号
        String gunNo = readGunNo(messageBody);
        // 控制结果
        Boolean controlResult = messageBody.readByte() == 0x01;
        // 失败原因
        String errorReason = parseErrorReason(messageBody.readByte());
        YkcDeviceStartChargeIn ykcDeviceStartChargeIn = YkcDeviceStartChargeIn.of(
                orderNo, deviceNo, gunNo, controlResult, errorReason);
        return ykcDeviceStartChargeIn;
    }

    /*
     * 0x36
     */
    private static YkcMessageIn getStopChargeMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        Boolean controlSuccessful = messageBody.readByte() == 0x01;
        String reason = parseChargeErrorReason(messageBody.readByte());
        YkcDeviceStopChargeIn stopChargeIn = YkcDeviceStopChargeIn.of(deviceNo, gunNo, controlSuccessful, reason);
        return stopChargeIn;
    }

    /**
     * 0x5A
     */
    private static YkcMessageIn getQRRespMessageIn(ByteBuf messageBody) {
        String deviceNo = readDeviceNo(messageBody);
        String gunNo = readGunNo(messageBody);
        Boolean result = messageBody.readByte() == 0x01;
        YkcDeviceQrcodeIn ykcDeviceQrcodeIn = new YkcDeviceQrcodeIn(deviceNo,gunNo, result);
        return ykcDeviceQrcodeIn;
    }

    private static String parseChargeErrorReason(Byte errorReasonByte) {
        String reason = YkcStopChargeErrorReasonEnum.OTHER.getReasonDesc();
        switch (errorReasonByte) {
            case 0x00 :reason = YkcStopChargeErrorReasonEnum.NONE.getReasonDesc(); break;
            case 0x01 :reason = YkcStopChargeErrorReasonEnum.DEVICE_NO_NOT_MATCH.getReasonDesc();break;
            case 0x02 :reason = YkcStopChargeErrorReasonEnum.NOT_CHARGING.getReasonDesc();break;
            default :reason = YkcStopChargeErrorReasonEnum.OTHER.getReasonDesc();
        };
        return reason;
    }

    private static String parseErrorReason(Byte errorReasonByte) {
        switch (errorReasonByte) {
            case 0x00:
                return YkcStartChargeErrorReason.NONE;
            case 0x01:
                return YkcStartChargeErrorReason.DEVICE_NO_NOT_MATCH;
            case 0x02:
                return YkcStartChargeErrorReason.ALREADY_CHARGING;
            case 0x03:
                return YkcStartChargeErrorReason.DEVICE_FAILURE;
            case 0x04:
                return YkcStartChargeErrorReason.GUN_DEVICE_OFF_LINE;
            case 0x05:
                return YkcStartChargeErrorReason.NOT_INSERTED;
            default:
                return YkcStartChargeErrorReason.UNKNOWN;
        }
    }


    /**
     * 读取gunNo
     * @param byteBuf byteBuf
     * @return {@link Long}
     */
    protected static String readGunNo(ByteBuf byteBuf) {
        int i = byteBuf.readByte();
        return org.apache.commons.lang3.StringUtils.leftPad( HexUtil.toHex(i)+"", 2, "0");
    }

    private static String parseNetworkType(byte networkByteType) {
         String networkType = "";
          switch (networkByteType) {
            case 0x00 : networkType =  DeviceNetworkTypeConstants.SIM_CARD; break;
            case 0x01 : networkType =  DeviceNetworkTypeConstants.LAN; break;
            case 0x02 : networkType =  DeviceNetworkTypeConstants.WAN; break;
            default : networkType =  DeviceNetworkTypeConstants.OTHER; break;
        };
          return networkType;
    }
    private static String parseOperatorType(byte operatorByte) {
        String operatorType = "";
        switch (operatorByte) {
            case 0x00 : operatorType =  DeviceOperatorTypeConstants.CMCC; break;
            case 0x02 : operatorType = DeviceOperatorTypeConstants.CTCC; break;
            case 0x03 : operatorType =  DeviceOperatorTypeConstants.CUCC; break;
            default : operatorType =  DeviceOperatorTypeConstants.OTHER;
        };
        return operatorType;
    }

    private static String parseGetChargeRecordReason(byte val) {
        String ret = "";
        switch (val) {
            case 0x00 : ret = "无"; break;
            case 0x02 : ret = "交易记录不存在"; break;
            case 0x03 : ret =  "召唤交易正在充电中"; break;
            default : ret =  "无";;
        };
        return ret;
    }

    /**
     * 读取deviceNo
     * @param byteBuf byteBuf
     * @return {@link Long}
     */
    public static String readDeviceNo(ByteBuf byteBuf) {
        // 设备编号
        byte[] deviceNoBytes = new byte[7];
        byteBuf.readBytes(deviceNoBytes);
        return BCD.bcdToStr(deviceNoBytes);
    }

    /**
     * 读取byte转int
     * @param byteBuf byteBuf
     * @return {@link Integer}
     */
    public static Integer readByteToInt(ByteBuf byteBuf) {
        return byteBuf.readByte() & 0xFF;
    }

    /**
     * 读取orderNo
     * @param byteBuf byteBuf
     * @return {@link String}
     */
    private static String readOrderNo(ByteBuf byteBuf) {
        byte[] orderNoBytes = new byte[16];
        byteBuf.readBytes(orderNoBytes);
        return HexUtil.encodeHexStr(orderNoBytes);
    }

    /**
     * 读取vin码
     * @param byteBuf
     * @return {@link String}
     */
    private static String parseVin(ByteBuf byteBuf){
        byte[] vinBytes = new byte[17];
        byteBuf.readBytes(vinBytes);
        String vin  = new StringBuffer( new String(vinBytes, StandardCharsets.US_ASCII)).reverse().toString();
        return vin;
    }

    private static String readVersion(ByteBuf byteBuf){
        String vPart1 = String.format("%02x", byteBuf.readUnsignedByte()).replace("0", "");
        String vPart2 = String.format("%02x", byteBuf.readUnsignedByte()).replace("0", "");
        String vPart3 = String.format("%02x", byteBuf.readUnsignedByte()).replace("0", "");
        String bmsProtocolVersion = "V"+ vPart1 +"." + vPart2 +"."+ vPart3;
        return bmsProtocolVersion;
    }


    public static long bytesToLong(byte[] bytes) {
        // 创建 8 字节的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(bytes);
        // 将 5 字节填充到 8 字节缓冲区（高位补 0）
        if (bytes.length < 8) {
            buffer.put(new byte[8 - bytes.length]); // 补 0
        }

        // 重置位置到起始位置并读取 long
        buffer.flip();

        return buffer.getLong();
    }

    public static void main(String[] args) {

/*        ByteBuf byteBuf = Unpooled.copiedBuffer(HexUtil.decodeHex("79456950bb798475be83f1e0f112fa2d782728210825f80d392a3d072829f0334b7cc867e064cf8c72dc755406c14b917e9d730d56010e7305c040b91dbcfa2403c5f104babf5096a6b538771051e1cb65"));


        YkcMessageIn chargeHandshakeMessageIn = getChargeHandshakeMessageIn(byteBuf);

        System.out.println(chargeHandshakeMessageIn);*/
        String s = new String(HexUtil.decodeHex("7d22e108870f7fce9c385501268098c908"), StandardCharsets.UTF_8);
        System.out.println(s);

    }



}
