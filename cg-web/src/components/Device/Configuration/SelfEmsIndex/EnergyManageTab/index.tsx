import React, { useMemo, useCallback } from 'react';
import Detail from '@/components/Detail';
import type { GroupItem } from '@/components/Detail';
import {
  manaulParamsItems,
  backupModeItems,
  peakTimeItems,
  manulSetColumns,
  BackupPowerSetColumns,
  PeakSetColumns,
  peakLoadShiftItems,
} from './config';
import type { DeviceDataType } from '@/services/equipment';
import ConfigModal from '../../../ConfigModal';
import moment from 'moment';
import { parseToArray } from '@/utils';
import { useAuthority } from '@/hooks';
import { formatMessage } from '@/utils';
import { chargeAndDischargeStatus } from '@/utils/dict';
import { powerFormat } from '@/utils/format';
export type ConfigProps = {
  deviceId: string;
  deviceData: DeviceDataType;
  realTimeData?: any;
};
const timeFormat = 'HH:mm';

const timeFields = ['sharpTime', 'peakTime', 'flatTime', 'valleyTime'];

export const EnergyManageTab: React.FC<ConfigProps> = (props) => {
  const { realTimeData, deviceId, deviceData } = props;

  const { authorityMap } = useAuthority([
    'iot:device:config:energyManage:manualModeSetting',
    'iot:device:config:energyManage:peakShaveModeSetting',
    'iot:device:config:energyManage:backupModeSetting',
    'iot:device:config:energyManage:peakValleyTimeSetting',
  ]);

  const peakLoadShiftData = useMemo(() => {
    const result: Record<string, any> = {
      peakShavingAndValleyFillingModeMaximumSOC:
        realTimeData?.peakShavingAndValleyFillingModeMaximumSOC,
      peakShavingAndValleyFillingModeLowestSOC:
        realTimeData?.peakShavingAndValleyFillingModeLowestSOC,
      PeriodOfTime: [],
    };
    parseToArray(realTimeData?.PeriodOfTime).forEach((item, index) => {
      const timeFrame = item?.pcsRunningTimeFrame?.split?.('-') || [];
      item.CorD = item.CorD + '';
      result.PeriodOfTime.push({
        ...item,
        pcsRunningTimeFrame:
          timeFrame.length > 1
            ? [
                moment(moment().format('YYYY-MM-DD') + ' ' + timeFrame[0]),
                moment(moment().format('YYYY-MM-DD') + ' ' + timeFrame[1]),
              ]
            : [],
      });
      result['pcsRunningTimeFrame' + index] = item.pcsRunningTimeFrame;
      result['CorD' + index] = item.CorD;
      result['executionPower' + index] = item.executionPower;
    });
    return result;
  }, [realTimeData]);

  const peakLoadShiftDetailItems = useMemo(() => {
    const result = peakLoadShiftItems.slice(0, 2);
    parseToArray(realTimeData?.PeriodOfTime)?.forEach?.((item, index) => {
      result.push(
        {
          title: formatMessage({ id: 'device.timePeriod', defaultMessage: '时段' }) + (index + 1),
          dataIndex: 'pcsRunningTimeFrame' + index,
        },
        {
          title: formatMessage({ id: 'device.workMode', defaultMessage: '工作模式' }),
          dataIndex: 'CorD' + index,
          format: (value) => chargeAndDischargeStatus[value]?.text,
        },
        {
          title: formatMessage({ id: 'siteMonitor.executionPower', defaultMessage: '执行功率' }),
          dataIndex: 'executionPower' + index,
          format: powerFormat,
        },
      );
    });
    return result;
  }, [realTimeData]);

  const peakValleyData = useMemo(() => {
    const timeData: Record<string, string[]> = {};
    const ElectrovalenceTimeFrame = parseToArray(realTimeData.ElectrovalenceTimeFrame).map(
      (item) => {
        timeData[timeFields[item?.ElectrovalenceType]] =
          timeData[timeFields[item?.ElectrovalenceType]] || [];
        timeData[timeFields[item?.ElectrovalenceType]].push(item?.TimeFrame);
        const timeFrame = item?.TimeFrame?.split?.('-') || [];
        return {
          ...item,
          TimeFrame:
            timeFrame.length > 1
              ? [
                  moment(moment().format('YYYY-MM-DD') + ' ' + timeFrame[0]),
                  moment(moment().format('YYYY-MM-DD') + ' ' + timeFrame[1]),
                ]
              : [],
        };
      },
    );
    const result: Record<string, any> = {
      ElectrovalenceTimeFrame,
    };
    timeFields.forEach((item) => {
      result[item] = timeData[item]?.join?.('，');
    });
    return result;
  }, [realTimeData]);

  const peakLoadShiftBeforeSubmit = useCallback((result) => {
    result?.input?.PeriodOfTime?.forEach?.((item: any) => {
      item.pcsRunningTimeFrame =
        moment('2023-01-01 ' + item.pcsRunningTimeFrame[0]).format(timeFormat) +
        '-' +
        moment('2023-01-01 ' + item.pcsRunningTimeFrame[1]).format(timeFormat);
    });
  }, []);

  const manaulModeSetting = useMemo<GroupItem[]>(() => {
    return [
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.manualModeSetting',
              defaultMessage: '手动模式设置',
            })}
          >
            <ConfigModal
              title={formatMessage({
                id: 'device.manualModeSetting',
                defaultMessage: '手动模式设置',
              })}
              deviceId={deviceId}
              deviceData={deviceData}
              realTimeData={realTimeData}
              columns={manulSetColumns}
              serviceId={'ManualModeSetting'}
              authority="iot:device:config:energyManage:manualModeSetting:distribute"
            />
          </Detail.Label>
        ),
        items: manaulParamsItems,
      },
    ];
  }, [deviceData, deviceId, realTimeData]);

  const backupModeSetting = useMemo<GroupItem[]>(() => {
    const result: GroupItem[] = [];
    if (authorityMap.get('iot:device:config:energyManage:peakShaveModeSetting')) {
      result.push({
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.peakShavingValleyFillingModeSetting',
              defaultMessage: '削峰填谷模式设置',
            })}
          >
            <ConfigModal
              width={'816px'}
              title={formatMessage({
                id: 'device.peakShavingValleyFillingModeSetting',
                defaultMessage: '削峰填谷模式设置',
              })}
              deviceId={deviceId}
              deviceData={deviceData}
              realTimeData={peakLoadShiftData}
              columns={peakLoadShiftItems}
              serviceId={'PeakShavingAndValleyFillingModeSetting'}
              beforeSubmit={peakLoadShiftBeforeSubmit}
              authority="iot:device:config:energyManage:peakShaveModeSetting:distribute"
            />
          </Detail.Label>
        ),
        items: peakLoadShiftDetailItems,
      });
    }
    if (authorityMap.get('iot:device:config:energyManage:backupModeSetting')) {
      result.push({
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.backupPowerModeSetting',
              defaultMessage: '备电模式设置',
            })}
          >
            <ConfigModal
              title={formatMessage({
                id: 'device.backupPowerModeSetting',
                defaultMessage: '备电模式设置',
              })}
              deviceId={deviceId}
              deviceData={deviceData}
              realTimeData={realTimeData}
              columns={BackupPowerSetColumns}
              serviceId={'BackupPowerModeSetting'}
              authority="iot:device:config:energyManage:backupModeSetting:distribute"
            />
          </Detail.Label>
        ),
        items: backupModeItems,
      });
    }
    if (authorityMap.get('iot:device:config:energyManage:peakValleyTimeSetting')) {
      result.push({
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'device.peakAndValleyTimeSetting',
              defaultMessage: '尖峰平谷时段设置',
            })}
          >
            <ConfigModal
              width={'816px'}
              title={formatMessage({
                id: 'device.peakAndValleyTimeSetting',
                defaultMessage: '尖峰平谷时段设置',
              })}
              deviceId={deviceId}
              deviceData={deviceData}
              realTimeData={{
                ...realTimeData,
                ...peakValleyData,
              }}
              columns={PeakSetColumns}
              serviceId={'PeakAndValleyTimeSettings'}
              colProps={{
                span: 24,
              }}
              authority="iot:device:config:energyManage:peakValleyTimeSetting:distribute"
            />
          </Detail.Label>
        ),
        items: peakTimeItems,
      });
    }
    return result;
  }, [
    deviceId,
    deviceData,
    realTimeData,
    authorityMap,
    peakValleyData,
    peakLoadShiftData,
    peakLoadShiftBeforeSubmit,
  ]);

  return (
    <>
      <div className="px24">
        {authorityMap.get('iot:device:config:energyManage:manualModeSetting') ? (
          <Detail.Group
            data={realTimeData}
            items={manaulModeSetting}
            detailProps={{
              colon: false,
              labelStyle: { width: 140 },
            }}
          />
        ) : (
          <></>
        )}
        <Detail.Group
          data={{ ...realTimeData, ...peakValleyData, ...peakLoadShiftData }}
          items={backupModeSetting}
          detailProps={{
            colon: false,
            labelStyle: { width: 140 },
          }}
        />
      </div>
    </>
  );
};
export default EnergyManageTab;
