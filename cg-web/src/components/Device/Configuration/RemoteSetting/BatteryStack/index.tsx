/*
 * @Description:
 * @Author: YangJianFei
 * @Date: 2024-01-05 16:12:13
 * @LastEditTime: 2024-01-13 13:57:29
 * @LastEditors: YangJianFei
 * @FilePath: \energy-cloud-frontend\src\components\Device\Configuration\RemoteSetting\BatteryStack\index.tsx
 */

import React, { useCallback, useMemo, useState } from 'react';
import { RemoteSettingType } from '../typing';
import { Button, Tabs, TabsProps } from 'antd';
import { useAuthority, useSubscribe } from '@/hooks';
import { formatMessage, getPropsFromTree } from '@/utils';
import Detail from '@/components/Detail';
import {
  batteryPackColumns,
  batteryPackFireColumns,
  batteryPackFireGroupItems,
  batteryPackGroupItems,
  protectFourColumns,
  protectFourGroupItems,
  protectOneColumns,
  protectOneGroupItems,
  protectThreeColumns,
  protectThreeGroupItems,
  protectTwoColumns,
  protectTwoGroupItems,
} from './helper';
import ConfigModal from '@/components/Device/ConfigModal';
import { useBoolean } from 'ahooks';
import { ProFormColumnsType } from '@ant-design/pro-components';
import SchemaForm from '@/components/SchemaForm';
import { OnlineStatusEnum } from '@/utils/dictionary';
import Authority from '@/components/Authority';

export type BatteryStackType = {
  bmuNum?: number;
} & RemoteSettingType;

const protectTabItem = [
  {
    label: '一级保护参数',
    key: 'level1',
    items: protectOneGroupItems,
  },
  {
    label: '二级保护参数',
    key: 'level2',
    items: protectTwoGroupItems,
  },
  {
    label: '三级保护参数',
    key: 'level3',
    items: protectThreeGroupItems,
  },
  {
    label: 'EMS四级保护参数',
    key: 'level4',
    items: protectFourGroupItems,
  },
];

const BatteryStack: React.FC<BatteryStackType> = (props) => {
  const { deviceData, bmuNum = 10 } = props;

  const [openForm, { set, setTrue }] = useBoolean(false);
  const realTimeData = useSubscribe(deviceData?.deviceId, true);
  const [currentFormInfo, setCurrentFormInfo] = useState<{
    title?: string;
    serviceId?: string;
    columns?: ProFormColumnsType[];
  }>({});
  const { authorityMap } = useAuthority([
    'iot:device:config:batterySetting:batterySystemSetting',
    'iot:device:config:batterySetting:batteryProtectSetting',
  ]);

  const Com = useMemo(
    () =>
      ({ onChange, form }: any) =>
        (
          <span>
            <Tabs
              items={protectTabItem.map((item) => ({
                ...item,
                children: (
                  <SchemaForm
                    layoutType="Form"
                    columns={item.items[0].items as any}
                    initialValues={realTimeData}
                    submitter={false}
                    grid={true}
                    colProps={{
                      span: 8,
                    }}
                    onValuesChange={(changeedValues: any) => {
                      const tabData = form?.getFieldValue?.('tabData') || {};
                      onChange?.({ ...tabData, ...changeedValues });
                    }}
                  />
                ),
              }))}
            />
          </span>
        ),
    [realTimeData],
  );

  const protectFormItems = useMemo<ProFormColumnsType>(() => {
    return {
      dataIndex: 'tabData',
      colProps: {
        span: 24,
      },
      renderFormItem: (_, __, form) => {
        return <Com form={form} />;
      },
    };
  }, [realTimeData, Com]);

  const onClick = useCallback(
    (serviceId: string, title: string) => {
      setCurrentFormInfo({
        title,
        serviceId,
        columns:
          serviceId == 'EnableBatterySystemSelfStartFunction'
            ? batteryPackFireColumns
            : [...batteryPackColumns, protectFormItems],
      });
      setTrue();
    },
    [protectFormItems],
  );

  const beforeSubmit = useCallback(
    (formData) => {
      const allFeild = getPropsFromTree(
        [...protectOneColumns, ...protectTwoColumns, ...protectThreeColumns, ...protectFourColumns],
        'dataIndex',
      );
      const allFeildValue = allFeild?.reduce?.(
        (result, item) => ({ ...result, [item]: realTimeData?.[item] }),
        {},
      );
      formData.input = { ...formData?.input, ...allFeildValue, ...formData?.input?.tabData };
      delete formData?.input?.tabData;
    },
    [realTimeData],
  );

  const levelItems = useMemo<TabsProps['items']>(() => {
    return protectTabItem.map((item) => ({
      ...item,
      children: <Detail.Group data={realTimeData} items={item.items} />,
    }));
  }, [realTimeData]);

  return (
    <>
      <Detail.Group
        data={realTimeData}
        items={[
          ...(authorityMap.get('iot:device:config:batterySetting:batterySystemSetting')
            ? [
                {
                  label: (
                    <Detail.Label title="电池组使能设置">
                      <Authority code="iot:device:config:batterySetting:batterySystemSetting:distribute">
                        <Button
                          type="primary"
                          disabled={deviceData?.networkStatus === OnlineStatusEnum.Offline}
                          onClick={() =>
                            onClick('EnableBatterySystemSelfStartFunction', '电池组使能设置')
                          }
                        >
                          {formatMessage({ id: 'common.configParam', defaultMessage: '配置参数' })}
                        </Button>
                      </Authority>
                    </Detail.Label>
                  ),
                },
                ...batteryPackFireGroupItems,
              ]
            : []),
          ...(authorityMap.get('iot:device:config:batterySetting:batteryProtectSetting')
            ? [
                {
                  label: (
                    <Detail.Label title="电池组保护参数设置">
                      <Authority code="iot:device:config:batterySetting:batteryProtectSetting:distribute">
                        <Button
                          type="primary"
                          disabled={deviceData?.networkStatus === OnlineStatusEnum.Offline}
                          onClick={() => onClick('BatteryProtecParam', '电池组保护参数设置')}
                        >
                          {formatMessage({ id: 'common.configParam', defaultMessage: '配置参数' })}
                        </Button>
                      </Authority>
                    </Detail.Label>
                  ),
                },
                ...batteryPackGroupItems,
              ]
            : []),
        ]}
      />
      {authorityMap.get('iot:device:config:batterySetting:batteryProtectSetting') ? (
        <Tabs items={levelItems} />
      ) : (
        <></>
      )}
      <ConfigModal
        width={
          currentFormInfo?.serviceId == 'EnableBatterySystemSelfStartFunction' ? '552px' : '816px'
        }
        open={openForm}
        onOpenChange={set}
        title={currentFormInfo?.title || ''}
        deviceId={deviceData?.deviceId}
        realTimeData={realTimeData}
        serviceId={currentFormInfo?.serviceId || ''}
        columns={currentFormInfo?.columns || []}
        showClickButton={false}
        colProps={
          currentFormInfo?.serviceId == 'EnableBatterySystemSelfStartFunction'
            ? {
                span: 24,
              }
            : {
                span: 8,
              }
        }
        beforeSubmit={beforeSubmit}
      />
    </>
  );
};

export default BatteryStack;
