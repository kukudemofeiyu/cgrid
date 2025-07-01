package com.things.cgomp.device.data.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 设备历史数据路由
 *
 * @author things
 */
@Getter
public enum HistoryDataRouter {

    MONITOR("device_port_data",
            new String[]{
                    "voltage",
                    "current",
                    "power",
                    "gunLineTemperature",
                    "batteryGroupMaxTemp",
                    "pileBodyTemperature",
                    "chargeEnergy"
            }),

    CHARGING("device_charging_data",
            new String[]{
                    "bmsDemandVoltage",
                    "bmsDemandCurrent",
                    "bmsCheckVoltage",
                    "bmsCheckCurrent",
                    "bmsChargeMode",
                    "bmsMaxCellVoltage",
                    "bmsMaxCellVoltageGroupNo",
                    "bmsSoc",
                    "bmsLeftChargeTime",
                    "bmsPileOutputVoltage",
                    "bmsPileOutputCurrent",
                    "bmsTotalChargeTime"
            });

    private final String table;

    private final String[] keys;

    HistoryDataRouter(String table, String[] keys) {
        this.table = table;
        this.keys = keys;
    }

    private static final Map<String, String> tableRouter = new HashMap<>();
    static {
        HistoryDataRouter[] values = HistoryDataRouter.values();
        for (HistoryDataRouter value : values) {
            Arrays.stream(value.getKeys()).forEach(k -> tableRouter.put(k, value.getTable()));
        }
    }

    public static String getTableByKey(String key) {
        return tableRouter.get(key);
    }
}
