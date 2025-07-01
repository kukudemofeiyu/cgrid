package com.things.cgomp.gateway.device.broker.ykc.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeviceMessageHandlerEnum {

    connect_handler(DeviceOpConstantEnum.CONNECT.getOpCode(), "ykcDeviceLoginHandler"),
    connect__resp_handler(DeviceOpConstantEnum.CONNECT_RESP.getOpCode(), "ykcDeviceLoginHandler"),
    heartbeat_handler(DeviceOpConstantEnum.HEARTBEAT.getOpCode(), "ykcDeviceHeartbeatHandler"),
    heartbeat_resp_handler(DeviceOpConstantEnum.HEARTBEAT_RESP.getOpCode(), "ykcDeviceHeartbeatHandler"),
    startCharge_handler(DeviceOpConstantEnum.START_CHARGE.getOpCode(), "ykcDeviceStartChargeHandler"),
    startCharge_resp_handler(DeviceOpConstantEnum.START_CHARGE_RESP.getOpCode(), "ykcDeviceStartChargeHandler"),
    stopCharge_handler(DeviceOpConstantEnum.STOP_CHARGE.getOpCode(), "ykcDeviceStopChargeHandler"),
    stopCharge_resp_handler(DeviceOpConstantEnum.STOP_CHARGE_RESP.getOpCode(), "ykcDeviceStopChargeHandler"),
    chargeStandard_sync_handler(DeviceOpConstantEnum.CHARGE_STANDARD_SYNC.getOpCode(), "ykcChargeStandardSyncHandler"),
    chargeStandard_sync_resp_handler(DeviceOpConstantEnum.CHARGE_STANDARD_SYNC_RESP.getOpCode(), "ykcChargeStandardSyncHandler"),
    chargeStandard_check_handler(DeviceOpConstantEnum.CHARGE_STANDARD_CHECK.getOpCode(), "ykcChargeStandardCheckHandler"),
    chargeStandard_check__resp_handler(DeviceOpConstantEnum.CHARGE_STANDARD_CHECK_RESP.getOpCode(), "ykcChargeStandardCheckHandler"),
    report_gun_status_handler(DeviceOpConstantEnum.DEVICE_STATUS_REPORT.getOpCode(), "ykcDeviceGunStatusHandler"),
    update_device_qr_handler(DeviceOpConstantEnum.UPDATE_DEVICE_QR.getOpCode(), "ykcDeviceQrcodeHandler"),
    update_device_qr_resp_handler(DeviceOpConstantEnum.UPDATE_DEVICE_QR_RESP.getOpCode(), "ykcDeviceQrcodeHandler"),
    device_vin_report_handler(DeviceOpConstantEnum.DEVICE_VIN_REPORT.getOpCode(), "ykcDeviceVinStatusHandler"),
    device_vin_report_resp_handler(DeviceOpConstantEnum.DEVICE_VIN_REPORT_RESP.getOpCode(), "ykcDeviceVinStatusHandler"),
    device_charge_end_status_handler(DeviceOpConstantEnum.DEVICE_CHARGE_END.getOpCode(), "ykcDeviceChargeEndStatusHandler"),
    charge_record_handler(DeviceOpConstantEnum.CHARGE_RECORD.getOpCode(), "ykcDeviceChargeRecordHandler"),
    charge_record_resp_handler(DeviceOpConstantEnum.CHARGE_RECORD_RESP.getOpCode(), "ykcDeviceChargeRecordHandler"),
    device_time_sync_handler(DeviceOpConstantEnum.DEVICE_TIME_SYNC.getOpCode(), "ykcTimeSyncHandler"),
    device_time_sync_resp_handler(DeviceOpConstantEnum.DEVICE_TIME_SYNC_RESP.getOpCode(), "ykcTimeSyncHandler"),
    charge_handshake_handler(DeviceOpConstantEnum.CHARGE_HANDSHAKE.getOpCode(), "ykcChargeHandShakeStatusHandler"),
    config_setting_handler(DeviceOpConstantEnum.CHARGE_CONFIG_SETTING.getOpCode(), "ykcConfigSettingHandler"),
    charge_error_status_handler(DeviceOpConstantEnum.CHARGE_ERROR_STATUS.getOpCode(), "ykcChargeErrorStatusHandler"),
    get_charge_record(DeviceOpConstantEnum.GET_CHARGE_RECORD.getOpCode(), "ykcDeviceChargeRecordGetHandler"),
    get_charge_record_resp(DeviceOpConstantEnum.GET_CHARGE_RECORD_RESP.getOpCode(), "ykcDeviceChargeRecordGetHandler"),
    set_charge_standard_check(DeviceOpConstantEnum.SET_CHARGE_STANDARD_CHECK.getOpCode(), "ykcChargeStandardSetHandler"),
    set_charge_standard_check_resp(DeviceOpConstantEnum.SET_CHARGE_STANDARD_CHECK_RESP.getOpCode(), "ykcChargeStandardSetHandler"),
    device_alarm_report(DeviceOpConstantEnum.DEVICE_ALARM_REPORT.getOpCode(), "ykcDeviceAlarmStatusHandler"),
    device_alarm_report_resp(DeviceOpConstantEnum.DEVICE_ALARM_REPORT_RESP.getOpCode(), "ykcDeviceAlarmStatusHandler"),
    device_alarm_recover(DeviceOpConstantEnum.DEVICE_ALARM_RECOVER.getOpCode(), "ykcDeviceAlarmRecoverHandler"),
    device_alarm_recover_resp(DeviceOpConstantEnum.DEVICE_ALARM_RECOVER_RESP.getOpCode(), "ykcDeviceAlarmRecoverHandler"),
    charge_output_status(DeviceOpConstantEnum.CHARGE_OUTPUT_STATUS.getOpCode(), "ykcChargeBmsOutputHandler"),


    ;



    private final Integer opCode;

    private final String handlerName;
}
