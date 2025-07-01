/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2023-07-13 23:36:42
 * @LastEditTime: 2024-12-23 11:38:07
 * @LastEditors: YangJianFei
 * @FilePath: /energy-cloud-frontend/src/components/DeviceRealTime/BatterryStack/Stack/index.tsx
 */
import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { useRequest } from 'umi';
import Detail, { DetailItem, GroupItem } from '@/components/Detail';
import YTProTable from '@/components/ProTable';
import { ProField } from '@ant-design/pro-components';
import { ProColumns } from '@ant-design/pro-components';
import {
  getControlItems,
  controlItemsTow,
  controlItemsMain,
  controlItemsMainYT,
  statusItemsOne,
  statusItemsH2,
  statusItemsTow,
  historyItems,
  tempItems,
  abilityItems,
  maxUnitColumns,
  statusItemsWaterMine,
  controlItemsMainLiquid,
} from './config';
import ElectricLine from '@/assets/image/device/electric-line.png';
import styles from './index.less';
import { MaxUnitType } from './type';
import { formatMessage, getPlaceholder, isEmpty } from '@/utils';
import { getClusterByStack, DeviceDataType, ClusterType } from '@/services/equipment';
import { DeviceTypeEnum } from '@/utils/dictionary';
import { onlineStatus } from '@/utils/dict';
import { clusterFormat, deviceAlarmStatusFormat } from '@/utils/format';
import Button from '@/components/CollectionModal/Button';
import useDeviceModel from '../../useDeviceModel';
import { MessageEventType } from '@/utils/connection';
import { useSubscribe } from '@/hooks';

export type StackProps = {
  deviceData?: DeviceDataType;
  realTimeData?: Record<string, any>;
};

const Stack: React.FC<StackProps> = (props) => {
  const { deviceData, realTimeData } = props;

  const { modelMap } = useDeviceModel({ productId: deviceData?.productId });
  const [collectionInfo, setCollectionInfo] = useState({
    title: '',
    collection: '',
  });
  const {
    data: clusterData,
    loading,
    run,
  } = useRequest(getClusterByStack, {
    manual: true,
  });

  const clusterDeviceIds = useMemo(() => {
    return clusterData?.map?.((item) => item?.deviceId || '');
  }, [clusterData]);

  const clusterNetWorkData = useSubscribe(clusterDeviceIds, true, MessageEventType.NETWORKSTSTUS);

  const onClick = useCallback((item: DetailItem) => {
    if (item.field) {
      setCollectionInfo({
        title: item.label as any,
        collection: item.field,
      });
    }
  }, []);

  useEffect(() => {
    if (deviceData?.deviceId) {
      run({ deviceId: deviceData?.deviceId });
    }
  }, [deviceData]);

  const maxUnitData = useMemo<MaxUnitType[]>(() => {
    const result: MaxUnitType[] = [
      {
        maxVoltage: `${formatMessage({
          id: 'siteMonitor.cellNumber',
          defaultMessage: '电芯编号',
        })}：${getPlaceholder(realTimeData?.MaxNOIV)}`,
        minVoltage: `${formatMessage({
          id: 'siteMonitor.cellNumber',
          defaultMessage: '电芯编号',
        })}：${getPlaceholder(realTimeData?.MNOIV)}`,
        maxTemp: `${formatMessage({
          id: 'siteMonitor.temperaturePoint',
          defaultMessage: '温度点',
        })}：${getPlaceholder(realTimeData?.MITN)}`,
        minTemp: `${formatMessage({
          id: 'siteMonitor.temperaturePoint',
          defaultMessage: '温度点',
        })}：${getPlaceholder(realTimeData?.MNOIT)}`,
      },
      {
        maxVoltage: `${formatMessage({
          id: 'siteMonitor.voltage',
          defaultMessage: '电压',
        })}：${getPlaceholder(realTimeData?.MVVOASU)}`,
        minVoltage: `${formatMessage({
          id: 'siteMonitor.voltage',
          defaultMessage: '电压',
        })}：${getPlaceholder(realTimeData?.MVVOSU)}`,
        maxTemp: `${formatMessage({
          id: 'siteMonitor.temperature',
          defaultMessage: '温度',
        })}：${getPlaceholder(realTimeData?.MaximumIndividualTemperature)}`,
        minTemp: `${formatMessage({
          id: 'siteMonitor.temperature',
          defaultMessage: '温度',
        })}：${getPlaceholder(realTimeData?.LVOMT)}`,
      },
    ];
    return result;
  }, [realTimeData]);

  const columns = useMemo<ProColumns<ClusterType>[]>(() => {
    return [
      {
        title: formatMessage({ id: 'common.deviceName', defaultMessage: '设备名称' }),
        dataIndex: 'deviceName',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
      },
      {
        title: formatMessage({ id: 'siteMonitor.runningState', defaultMessage: '运行状态' }),
        dataIndex: 'runState',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
        render: (_, { runState }) => {
          return clusterFormat(runState);
        },
      },
      {
        title: formatMessage({ id: 'siteMonitor.communicationStatus', defaultMessage: '通信状态' }),
        dataIndex: 'connectStatus',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
        render: (_, { deviceId, connectStatus }) => {
          return (
            <ProField
              text={clusterNetWorkData?.[deviceId ?? '']?.status ?? connectStatus}
              mode="read"
              valueEnum={onlineStatus}
            />
          );
        },
      },
      {
        title: formatMessage({ id: 'siteMonitor.alarmStatus', defaultMessage: '告警状态' }),
        dataIndex: 'alarmStatus',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
        render: (_, { alarmStatus }) => {
          return deviceAlarmStatusFormat(alarmStatus as any);
        },
      },
      {
        title: formatMessage({ id: 'siteMonitor.equipmentCapacity', defaultMessage: '设备容量' }),
        dataIndex: 'ratedCapacity',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
      },
      {
        title: formatMessage({ id: 'common.current', defaultMessage: '当前' }) + 'SOC(%)',
        dataIndex: 'soc',
        width: 150,
        ellipsis: true,
        hideInSearch: true,
      },
    ];
  }, [clusterNetWorkData]);

  const extral = (
    <Button
      title={collectionInfo.title}
      deviceId={deviceData?.deviceId}
      collection={collectionInfo.collection}
      model={modelMap?.[collectionInfo.collection]}
      onClick={onClick}
    />
  );

  const detailGroup = useMemo<GroupItem[]>(() => {
    return [
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.controlInformation',
              defaultMessage: '控制信息',
            })}
          />
        ),
        items: [
          ...getControlItems(deviceData),
          ...((deviceData?.productId as any) == DeviceTypeEnum.YTEnergyBatteryStack ||
            (deviceData?.productId as any) == DeviceTypeEnum.LiquidEnergyBatteryStack
            ? deviceData?.productId == DeviceTypeEnum.LiquidEnergyBatteryStack
              ? controlItemsMainLiquid
              : controlItemsMainYT
            : controlItemsMain),
          ...controlItemsTow,
        ],
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.statusInformation',
              defaultMessage: '状态信息',
            })}
          />
        ),
        items: [
          ...statusItemsOne,
          ...((deviceData?.productId as any) == DeviceTypeEnum.YTEnergyBatteryStack ||
            (deviceData?.productId as any) == DeviceTypeEnum.LiquidEnergyBatteryStack
            ? []
            : statusItemsH2),
          ...statusItemsTow,
          ...((deviceData?.productId as any) == DeviceTypeEnum.YTEnergyBatteryStack ||
            (deviceData?.productId as any) == DeviceTypeEnum.LiquidEnergyBatteryStack
            ? statusItemsWaterMine
            : []),
        ],
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.historicalInformation',
              defaultMessage: '历史信息',
            })}
          />
        ),
        items: historyItems,
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.temperatureInformation',
              defaultMessage: '温度信息',
            })}
          />
        ),
        items: tempItems,
      },
      {
        label: (
          <Detail.Label
            title={formatMessage({
              id: 'siteMonitor.capabilityInformation',
              defaultMessage: '能力信息',
            })}
          />
        ),
        items: abilityItems,
      },
    ];
  }, [deviceData]);

  return (
    <>
      <Detail.Group
        data={realTimeData}
        items={detailGroup}
        detailProps={{
          extral,
          colon: false,
          labelStyle: { width: 140 },
        }}
      />
      <Detail.Label
        title={formatMessage({
          id: 'siteMonitor.monomerInformation',
          defaultMessage: '单体极值信息',
        })}
        className="mt16"
      />
      <YTProTable
        search={false}
        options={false}
        columns={maxUnitColumns}
        toolBarRender={false}
        dataSource={maxUnitData}
        scroll={{ y: 'auto' }}
        pagination={false}
      />
      <Detail.Label
        title={formatMessage({
          id: 'siteMonitor.batteryClusterInformation',
          defaultMessage: '电池簇信息',
        })}
        className="mt32"
      />
      <YTProTable
        loading={loading}
        search={false}
        options={false}
        columns={columns}
        toolBarRender={false}
        dataSource={clusterData}
        scroll={{ y: 'auto' }}
        pagination={false}
      />
      <Detail.Label
        title={formatMessage({
          id: 'siteMonitor.electricalPrimaryDiagram',
          defaultMessage: '电气一次图',
        })}
        className="mt32"
      />
      <div className={styles.elctric}>
        <img src={ElectricLine} />
      </div>
    </>
  );
};

export default Stack;
